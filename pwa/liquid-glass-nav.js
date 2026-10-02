/* 星域集团 PWA · 底部快捷栏 Liquid Glass（Three.js WebGL2 真折射引擎）
 * 严格按董事长 7 条提示词实现：
 *   CH01 三层拆分（Background → Glass → Content）
 *   CH02 SDF 轮廓（宽高比校正 + 圆角矩形 SDF + fwidth 抗锯齿）
 *   CH03 高度场与法线（quintic 高度场 + 有限差分法线）
 *   CH04 双界面折射（前表面/后表面 + IOR 1.46 + 光谱色散）
 *   CH05 三路光学采样（Face / Inner / Outer）
 *   CH06 GGX BRDF 光照（四点掠射柔光）
 *   CH07 融合动画（背景光斑漂移 + 灯光缓慢旋转）
 * 渐进增强：WebGL2 不可用时回退 CSS 毛玻璃（保持底部导航可用）。
 */
(function () {
  'use strict';

  if (typeof THREE === 'undefined') return;

  var nav = document.getElementById('bottom-nav');
  if (!nav) return;

  var canvas = document.createElement('canvas');
  canvas.id = 'lg-nav-canvas';
  canvas.style.cssText = 'position:fixed;pointer-events:none;z-index:998;will-change:transform;';
  document.body.appendChild(canvas);

  var renderer;
  try {
    renderer = new THREE.WebGLRenderer({ canvas: canvas, alpha: true, antialias: true, powerPreference: 'high-performance' });
  } catch (e) {
    return;
  }
  if (!renderer.capabilities.isWebGL2) {
    renderer.dispose();
    canvas.parentNode && canvas.parentNode.removeChild(canvas);
    return;
  }

  var PR = Math.min(window.devicePixelRatio || 1, 2);
  renderer.setPixelRatio(PR);
  renderer.setClearColor(0x000000, 0); // 透明清除：胶囊外不遮挡页面内容

  var camera = new THREE.OrthographicCamera(-1, 1, 1, -1, 0, 1);

  // ===== 共享顶点着色器 =====
  var VERT = [
    'varying vec2 vUv;',
    'void main() { vUv = uv; gl_Position = vec4(position, 1.0); }'
  ].join('\n');

  // ===== CH01 背景层（paintComposeRT）=====
  var BG_FRAG = [
    'precision highp float;',
    'varying vec2 vUv;',
    'uniform float uTime;',
    'void main() {',
    '  vec2 uv = vUv;',
    '  vec3 col = vec3(0.027, 0.027, 0.035);', // #070709 底座
    '  vec2 c1 = vec2(0.22 + 0.06 * sin(uTime * 0.21), 0.30 + 0.05 * cos(uTime * 0.17));',
    '  float d1 = length(uv - c1);',
    '  col += vec3(0.55, 0.60, 0.75) * 0.15 * exp(-d1 * d1 * 18.0);',
    '  vec2 c2 = vec2(0.78 + 0.05 * cos(uTime * 0.13), 0.55 + 0.06 * sin(uTime * 0.23));',
    '  float d2 = length(uv - c2);',
    '  col += vec3(0.60, 0.55, 0.70) * 0.13 * exp(-d2 * d2 * 20.0);',
    '  vec2 c3 = vec2(0.5 + 0.04 * sin(uTime * 0.19), 0.5 + 0.04 * cos(uTime * 0.15));',
    '  float d3 = length(uv - c3);',
    '  col += vec3(1.0) * 0.06 * exp(-d3 * d3 * 25.0);',
    '  gl_FragColor = vec4(col, 1.0);',
    '}'
  ].join('\n');

  // ===== CH02-CH06 玻璃层 =====
  var GLASS_FRAG = [
    'precision highp float;',
    'varying vec2 vUv;',
    'uniform sampler2D uBackground;',
    'uniform vec2 uResolution;',
    'uniform float uAspect;',
    'uniform float uTime;',
    'uniform vec2 uCapsuleCenterP;',
    'uniform vec2 uCapsuleHalfP;',
    'uniform float uCapsuleRadiusP;',
    'uniform vec2 uCapsuleCenterUv;',
    'uniform float uGlassThickness;',
    'uniform float uNormalTransition;',
    'uniform float uBackgroundDistance;',
    'uniform float uIOR;',
    'uniform float uDispersion;',
    'uniform float uRoughness;',

    'float quintic(float t){ return t * t * t * (t * (t * 6.0 - 15.0) + 10.0); }',

    // CH02 圆角矩形 SDF
    'float sdRoundRect(vec2 p, vec2 halfSize, float r){',
    '  vec2 q = abs(p) - halfSize + r;',
    '  return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;',
    '}',

    'vec2 toP(vec2 uv){ return (uv - 0.5) * vec2(uAspect, 1.0); }',
    'vec2 toUv(vec2 p){ return p / vec2(uAspect, 1.0) + 0.5; }',

    'float capsuleSdf(vec2 p){',
    '  return sdRoundRect(p - uCapsuleCenterP, uCapsuleHalfP, uCapsuleRadiusP);',
    '}',

    // CH03 高度场
    'float heightAt(vec2 p){',
    '  float sdf = capsuleSdf(p);',
    '  float interior = max(-sdf, 0.0);',
    '  float bevel = uNormalTransition * 0.82;',
    '  float t = clamp(interior / bevel, 0.0, 1.0);',
    '  return uGlassThickness * quintic(t);',
    '}',

    // CH03 法线（有限差分，X 含 aspect 校正）
    'vec3 surfaceNormal(vec2 p){',
    '  float step = clamp(uNormalTransition * 0.13, 0.003, 0.012);',
    '  float hL = heightAt(p - vec2(step, 0.0));',
    '  float hR = heightAt(p + vec2(step, 0.0));',
    '  float hU = heightAt(p + vec2(0.0, step));',
    '  float hD = heightAt(p - vec2(0.0, step));',
    '  vec2 grad = vec2(hR - hL, hU - hD) / (2.0 * step);',
    '  return normalize(vec3(grad, 1.0));',
    '}',

    // CH04 双界面折射：返回折射后在背景平面的采样 UV
    'vec2 traceUV(vec2 p, float ior){',
    '  float height = heightAt(p);',
    '  vec3 frontNormal = surfaceNormal(p);',
    '  vec3 incident = vec3(0.0, 0.0, -1.0);',
    '  vec3 inside = refract(incident, frontNormal, 1.0 / ior);',
    '  if (dot(inside, inside) < 1e-4) inside = refract(incident, vec3(0.0, 0.0, 1.0), 1.0 / ior);',
    '  inside = normalize(inside);',
    '  float glassPath = height / max(-inside.z, 0.025);',
    '  vec3 backPoint = vec3(p, height) + inside * glassPath;',
    '  vec3 backNormal = vec3(0.0, 0.0, 1.0);',
    '  vec3 exitRay = refract(inside, backNormal, ior);',
    '  if (dot(exitRay, exitRay) < 1e-4) exitRay = inside;',
    '  exitRay = normalize(exitRay);',
    '  float tBG = (-uBackgroundDistance - backPoint.z) / max(exitRay.z, 1e-4);',
    '  vec3 hit = backPoint + exitRay * tBG;',
    '  return toUv(hit.xy);',
    '}',

    'void main(){',
    '  vec2 p = toP(vUv);',
    '  float sdf = capsuleSdf(p);',
    '  float aa = fwidth(sdf);',
    '  float mask = 1.0 - smoothstep(-aa, aa, sdf);',
    '  if (mask <= 0.001) discard;',

    '  float interior = max(-sdf, 0.0);',
    '  float t = clamp(interior / (uNormalTransition * 0.82), 0.0, 1.0);',
    '  vec3 N = surfaceNormal(p);',

    // CH04 光谱色散（R/G/B 三路折射）
    '  vec2 greenUV = traceUV(p, uIOR);',
    '  vec2 rUV = traceUV(p, uIOR - uDispersion);',
    '  vec2 bUV = traceUV(p, uIOR + uDispersion);',

    '  vec2 dirToRim = normalize(p - uCapsuleCenterP + vec2(1e-6));',
    '  float rimDisp = uNormalTransition * 0.9;',

    // CH05 三路采样
    '  vec2 faceUV = uCapsuleCenterUv + (vUv - uCapsuleCenterUv) * 0.975;',
    '  vec3 faceCol = texture2D(uBackground, faceUV).rgb;',

    '  vec3 innerCol = vec3(',
    '    texture2D(uBackground, clamp(rUV - dirToRim * rimDisp, 0.0, 1.0)).r,',
    '    texture2D(uBackground, clamp(greenUV - dirToRim * rimDisp, 0.0, 1.0)).g,',
    '    texture2D(uBackground, clamp(bUV - dirToRim * rimDisp, 0.0, 1.0)).b',
    '  );',

    '  vec2 outerBase = clamp(vUv + dirToRim * rimDisp * 1.28, 0.0, 1.0);',
    '  vec3 outerCol = mix(',
    '    vec3(',
    '      texture2D(uBackground, outerBase).r,',
    '      texture2D(uBackground, outerBase).g,',
    '      texture2D(uBackground, outerBase).b',
    '    ),',
    '    vec3(',
    '      texture2D(uBackground, clamp(rUV + dirToRim * rimDisp * 1.28, 0.0, 1.0)).r,',
    '      texture2D(uBackground, clamp(greenUV + dirToRim * rimDisp * 1.28, 0.0, 1.0)).g,',
    '      texture2D(uBackground, clamp(bUV + dirToRim * rimDisp * 1.28, 0.0, 1.0)).b',
    '    ), 0.6);',

    // CH05 权重归一化合成
    '  float outerW = 1.0 - smoothstep(0.0, 0.55, t);',
    '  float faceW = smoothstep(0.5, 1.0, t);',
    '  float innerW = 1.0 - outerW - faceW;',
    '  float wsum = faceW + innerW + outerW + 1e-6;',
    '  faceW /= wsum; innerW /= wsum; outerW /= wsum;',
    '  vec3 refracted = faceCol * faceW + innerCol * innerW + outerCol * outerW;',

    // CH06 顺序：折射背景 → 白色染色 → 镜面反射
    '  refracted = mix(refracted, refracted + vec3(0.03), 0.4);',

    '  float F0 = ((uIOR - 1.0) / (uIOR + 1.0));',
    '  F0 = F0 * F0;',
    '  float alpha = uRoughness * uRoughness;',
    '  vec3 V = vec3(0.0, 0.0, 1.0);',

    '  vec3 spec = vec3(0.0);',
    '  for (int i = 0; i < 4; i++) {',
    '    float fi = float(i);',
    '    float ang = uTime * 0.12 + fi * 1.5707963;',
    '    float rxy = 0.90 + 0.015 * fi;',
    '    float rz = 0.28 + 0.045 * fi;',
    '    vec3 L = normalize(vec3(cos(ang) * rxy, sin(ang) * rxy, rz));',
    '    vec3 H = normalize(V + L);',
    '    float NdotL = max(dot(N, L), 0.0);',
    '    float NdotV = max(dot(N, V), 0.0);',
    '    float NdotH = max(dot(N, H), 0.0);',
    '    float VdotH = max(dot(V, H), 0.0);',
    '    float a2 = alpha * alpha;',
    '    float dden = NdotH * NdotH * (a2 - 1.0) + 1.0;',
    '    float D = a2 / (3.14159265 * dden * dden);',
    '    float k = alpha * 0.5;',
    '    float vis = (NdotV / (NdotV * (1.0 - k) + k)) * (NdotL / (NdotL * (1.0 - k) + k));',
    '    vec3 F = vec3(F0) + (1.0 - vec3(F0)) * pow(1.0 - VdotH, 5.0);',
    '    spec += D * vis * F * NdotL;',
    '  }',

    // CH06 高光仅限倒角外半径（避免中心平面镜凸起）
    '  float edgeW = 1.0 - smoothstep(0.0, 0.65, t);',
    '  spec *= edgeW * 1.6;',

    '  vec3 col = refracted + spec;',
    '  gl_FragColor = vec4(col, mask * 0.85);',
    '}'
  ].join('\n');

  var bgRT = new THREE.WebGLRenderTarget(2, 2, {
    minFilter: THREE.LinearFilter,
    magFilter: THREE.LinearFilter,
    format: THREE.RGBAFormat
  });

  var bgMat = new THREE.ShaderMaterial({
    uniforms: { uTime: { value: 0 } },
    vertexShader: VERT,
    fragmentShader: BG_FRAG,
    depthWrite: false,
    depthTest: false
  });
  var bgQuad = new THREE.Mesh(new THREE.PlaneGeometry(2, 2), bgMat);
  bgQuad.frustumCulled = false;
  var bgScene = new THREE.Scene();
  bgScene.add(bgQuad);

  var glassUniforms = {
    uBackground: { value: bgRT.texture },
    uResolution: { value: new THREE.Vector2(1, 1) },
    uAspect: { value: 1.0 },
    uTime: { value: 0 },
    uCapsuleCenterP: { value: new THREE.Vector2(0, 0) },
    uCapsuleHalfP: { value: new THREE.Vector2(0.4, 0.15) },
    uCapsuleRadiusP: { value: 0.15 },
    uCapsuleCenterUv: { value: new THREE.Vector2(0.5, 0.5) },
    uGlassThickness: { value: 0.12 },
    uNormalTransition: { value: 0.10 },
    uBackgroundDistance: { value: 1.2 },
    uIOR: { value: 1.46 },
    uDispersion: { value: 0.015 },
    uRoughness: { value: 0.32 }
  };

  var glassMat = new THREE.ShaderMaterial({
    uniforms: glassUniforms,
    vertexShader: VERT,
    fragmentShader: GLASS_FRAG,
    transparent: true,
    depthWrite: false,
    depthTest: false
  });
  var glassQuad = new THREE.Mesh(new THREE.PlaneGeometry(2, 2), glassMat);
  glassQuad.frustumCulled = false;
  var glassScene = new THREE.Scene();
  glassScene.add(glassQuad);

  var BLEED = 28; // 折射读取玻璃边界外背景所需的出血边距(px)

  function layout() {
    var r = nav.getBoundingClientRect();
    if (r.width < 4 || r.height < 4) return;

    var W = Math.max(2, r.width + BLEED * 2);
    var H = Math.max(2, r.height + BLEED * 2);

    canvas.style.left = (r.left - BLEED) + 'px';
    canvas.style.top = (r.top - BLEED) + 'px';
    canvas.style.width = W + 'px';
    canvas.style.height = H + 'px';

    renderer.setSize(W, H, false);
    var bw = Math.max(2, Math.floor(W * PR));
    var bh = Math.max(2, Math.floor(H * PR));
    bgRT.setSize(bw, bh);

    var aspect = W / H;
    glassUniforms.uAspect.value = aspect;
    glassUniforms.uResolution.value.set(W, H);

    // 胶囊在 p 空间中的几何（p 单位 = 1 / H）
    var cx = (BLEED + r.width / 2) / W;          // = 0.5
    var cy = 1.0 - (BLEED + r.height / 2) / H;   // = 0.5（WebGL uv y 向上）
    glassUniforms.uCapsuleCenterP.value.set((cx - 0.5) * aspect, cy - 0.5);
    glassUniforms.uCapsuleHalfP.value.set((r.width / 2 / W) * aspect, r.height / 2 / H);
    glassUniforms.uCapsuleRadiusP.value = r.height / 2 / H;
    glassUniforms.uCapsuleCenterUv.value.set(cx, cy);
  }

  var clock = new THREE.Clock();
  var running = true;

  function animate() {
    if (!running) return;
    requestAnimationFrame(animate);
    var t = clock.getElapsedTime();
    bgMat.uniforms.uTime.value = t;
    glassUniforms.uTime.value = t;
    renderer.setRenderTarget(bgRT);
    renderer.render(bgScene, camera);
    renderer.setRenderTarget(null);
    renderer.render(glassScene, camera);
  }

  function onVisibility() {
    if (document.hidden) {
      running = false;
    } else {
      running = true;
      clock.getDelta();
      animate();
    }
  }

  layout();
  nav.classList.add('lg-glass');
  animate();

  window.addEventListener('resize', layout);
  window.addEventListener('orientationchange', function () { setTimeout(layout, 250); });
  document.addEventListener('visibilitychange', onVisibility);
})();
