/* 星域集团 PWA · iOS Liquid Glass 折射引擎（渐进增强）
 * 参数由董事长校准（参考 Apple HIG + 真实玻璃折射率 IOR 1.52）
 * 基于 archisvaze/liquid-glass 的 SVG feDisplacementMap 方案。
 * 注意：feDisplacementMap 的 backdrop-filter 仅 Blink（桌面 Chrome/Edge、安卓 Chrome）支持，
 * iPhone 上所有浏览器（含 Chrome）都强制用 WebKit，不支持 → 自动回退 CSS 毛玻璃。
 */
(function () {
  'use strict';

  var PARAMS = {
    thickness: 12,     // 玻璃厚度（薄）
    bezel: 14,         // bezel 宽度（只有边缘一点点折射）
    ior: 1.52,         // 真实玻璃折射率
    blur: 2.2,         // 背景模糊 stdDeviation
    specular: 0.22,    // 高光强度（弱）
    specSaturation: 4, // 高光饱和度
    radius: 21         // 圆角
  };

  var SELECTOR = '.card, .sub-card, .stats-card, .emp-card, .archive-section';

  var SURFACE_FNS = {
    convex_squircle: function (x) {
      return Math.pow(1 - Math.pow(1 - x, 4), 0.25);
    }
  };

  function isBlink() {
    if (navigator.userAgentData && navigator.userAgentData.brands) {
      for (var i = 0; i < navigator.userAgentData.brands.length; i++) {
        if (navigator.userAgentData.brands[i].brand === 'Chromium') return true;
      }
    }
    var ua = navigator.userAgent || '';
    return /Chrom(e|ium)/.test(ua) && !/CriOS|FxiOS|EdgiOS|OPiOS/.test(ua);
  }

  function calculateRefractionProfile(glassThickness, bezelWidth, heightFn, ior, samples) {
    samples = samples || 128;
    var eta = 1 / ior;
    function refract(nx, ny) {
      var dot = ny;
      var k = 1 - eta * eta * (1 - dot * dot);
      if (k < 0) return null;
      var sq = Math.sqrt(k);
      return [-(eta * dot + sq) * nx, eta - (eta * dot + sq) * ny];
    }
    var profile = new Float64Array(samples);
    for (var i = 0; i < samples; i++) {
      var x = i / samples;
      var y = heightFn(x);
      var dx = x < 1 ? 0.0001 : -0.0001;
      var y2 = heightFn(x + dx);
      var deriv = (y2 - y) / dx;
      var mag = Math.sqrt(deriv * deriv + 1);
      var ref = refract(-deriv / mag, -1 / mag);
      if (!ref) { profile[i] = 0; continue; }
      profile[i] = ref[0] * ((y * bezelWidth + glassThickness) / ref[1]);
    }
    return profile;
  }

  function generateDisplacementMap(w, h, radius, bezelWidth, profile, maxDisp) {
    var c = document.createElement('canvas');
    c.width = w; c.height = h;
    var ctx = c.getContext('2d');
    var img = ctx.createImageData(w, h);
    var d = img.data;
    var i;
    for (i = 0; i < d.length; i += 4) { d[i] = 128; d[i + 1] = 128; d[i + 2] = 0; d[i + 3] = 255; }
    var r = radius, rSq = r * r, r1Sq = (r + 1) * (r + 1);
    var rBSq = Math.max(r - bezelWidth, 0); rBSq = rBSq * rBSq;
    var wB = w - r * 2, hB = h - r * 2, S = profile.length;
    for (var y1 = 0; y1 < h; y1++) {
      for (var x1 = 0; x1 < w; x1++) {
        var x = x1 < r ? x1 - r : (x1 >= w - r ? x1 - r - wB : 0);
        var y = y1 < r ? y1 - r : (y1 >= h - r ? y1 - r - hB : 0);
        var dSq = x * x + y * y;
        if (dSq > r1Sq || dSq < rBSq) continue;
        var dist = Math.sqrt(dSq);
        var fromSide = r - dist;
        var op = dSq < rSq ? 1 : 1 - (dist - Math.sqrt(rSq)) / (Math.sqrt(r1Sq) - Math.sqrt(rSq));
        if (op <= 0 || dist === 0) continue;
        var cos = x / dist, sin = y / dist;
        var bi = Math.min(((fromSide / bezelWidth) * S) | 0, S - 1);
        var disp = profile[bi] || 0;
        var dX = (-cos * disp) / maxDisp, dY = (-sin * disp) / maxDisp;
        var idx = (y1 * w + x1) * 4;
        d[idx] = (128 + dX * 127 * op + 0.5) | 0;
        d[idx + 1] = (128 + dY * 127 * op + 0.5) | 0;
      }
    }
    ctx.putImageData(img, 0, 0);
    return c.toDataURL();
  }

  function generateSpecularMap(w, h, radius, bezelWidth, angle) {
    angle = angle != null ? angle : Math.PI / 3;
    var c = document.createElement('canvas');
    c.width = w; c.height = h;
    var ctx = c.getContext('2d');
    var img = ctx.createImageData(w, h);
    var d = img.data;
    d.fill(0);
    var r = radius, rSq = r * r, r1Sq = (r + 1) * (r + 1);
    var rBSq = Math.max(r - bezelWidth, 0); rBSq = rBSq * rBSq;
    var wB = w - r * 2, hB = h - r * 2;
    var sv = [Math.cos(angle), Math.sin(angle)];
    for (var y1 = 0; y1 < h; y1++) {
      for (var x1 = 0; x1 < w; x1++) {
        var x = x1 < r ? x1 - r : (x1 >= w - r ? x1 - r - wB : 0);
        var y = y1 < r ? y1 - r : (y1 >= h - r ? y1 - r - hB : 0);
        var dSq = x * x + y * y;
        if (dSq > r1Sq || dSq < rBSq) continue;
        var dist = Math.sqrt(dSq);
        var fromSide = r - dist;
        var op = dSq < rSq ? 1 : 1 - (dist - Math.sqrt(rSq)) / (Math.sqrt(r1Sq) - Math.sqrt(rSq));
        if (op <= 0 || dist === 0) continue;
        var cos = x / dist, sin = -y / dist;
        var dot = Math.abs(cos * sv[0] + sin * sv[1]);
        var edge = Math.sqrt(Math.max(0, 1 - (1 - fromSide) * (1 - fromSide)));
        var coeff = dot * edge;
        var col = (255 * coeff) | 0;
        var alpha = (col * coeff * op) | 0;
        var idx = (y1 * w + x1) * 4;
        d[idx] = col; d[idx + 1] = col; d[idx + 2] = col; d[idx + 3] = alpha;
      }
    }
    ctx.putImageData(img, 0, 0);
    return c.toDataURL();
  }

  var defsEl = null;
  var seq = 0;
  var rt = null;

  function buildFilterFor(el) {
    var w = el.offsetWidth, h = el.offsetHeight;
    if (w < 4 || h < 4) return;
    var radius = Math.min(PARAMS.radius, w / 2 - 1, h / 2 - 1);
    if (radius < 2) return;
    var bezel = Math.min(PARAMS.bezel, radius - 1, Math.min(w, h) / 2 - 1);
    if (bezel < 1) return;

    var heightFn = SURFACE_FNS.convex_squircle;
    var profile = calculateRefractionProfile(PARAMS.thickness, bezel, heightFn, PARAMS.ior, 128);
    var arr = Array.prototype.slice.call(profile);
    var maxDisp = Math.max.apply(null, arr.map(function (v) { return Math.abs(v); })) || 1;
    var dispUrl = generateDisplacementMap(w, h, radius, bezel, profile, maxDisp);
    var specUrl = generateSpecularMap(w, h, radius, bezel * 2.5);
    var scale = maxDisp;

    var id = 'lg-f' + (seq++);
    var filter = '<filter id="' + id + '" x="0%" y="0%" width="100%" height="100%">'
      + '<feGaussianBlur in="SourceGraphic" stdDeviation="' + PARAMS.blur + '" result="blurred_source" />'
      + '<feImage href="' + dispUrl + '" x="0" y="0" width="' + w + '" height="' + h + '" result="disp_map" />'
      + '<feDisplacementMap in="blurred_source" in2="disp_map" scale="' + scale + '" xChannelSelector="R" yChannelSelector="G" result="displaced" />'
      + '<feColorMatrix in="displaced" type="saturate" values="' + PARAMS.specSaturation + '" result="displaced_sat" />'
      + '<feImage href="' + specUrl + '" x="0" y="0" width="' + w + '" height="' + h + '" result="spec_layer" />'
      + '<feComposite in="displaced_sat" in2="spec_layer" operator="in" result="spec_masked" />'
      + '<feComponentTransfer in="spec_layer" result="spec_faded"><feFuncA type="linear" slope="' + PARAMS.specular + '" /></feComponentTransfer>'
      + '<feBlend in="spec_masked" in2="displaced" mode="normal" result="with_sat" />'
      + '<feBlend in="spec_faded" in2="with_sat" mode="normal" />'
      + '</filter>';
    defsEl.insertAdjacentHTML('beforeend', filter);

    el.style.backdropFilter = 'url(#' + id + ')';
    el.style.webkitBackdropFilter = 'url(#' + id + ')';
  }

  function refresh() {
    if (!defsEl) {
      defsEl = document.getElementById('lg-defs');
      if (!defsEl) return;
    }
    var els = document.querySelectorAll(SELECTOR);
    var i;
    for (i = 0; i < els.length; i++) {
      if (els[i].offsetWidth > 0) {
        els[i].style.backdropFilter = '';
        els[i].style.webkitBackdropFilter = '';
      }
    }
    defsEl.innerHTML = '';
    seq = 0;
    for (i = 0; i < els.length; i++) {
      if (els[i].offsetWidth > 0) buildFilterFor(els[i]);
    }
  }

  function scheduleRefresh(delay) {
    clearTimeout(rt);
    rt = setTimeout(refresh, delay || 40);
  }

  function start() {
    if (!isBlink()) return; // WebKit/Safari/Firefox 不支持 feDisplacementMap backdrop-filter，保持 CSS 毛玻璃
    defsEl = document.getElementById('lg-defs');
    if (!defsEl) return;

    if (window.MutationObserver) {
      var obs = new MutationObserver(function () { scheduleRefresh(40); });
      ['content', 'overlay'].forEach(function (id) {
        var el = document.getElementById(id);
        if (el) obs.observe(el, { childList: true, subtree: true });
      });
    }
    window.addEventListener('resize', function () { scheduleRefresh(250); });
    window.addEventListener('orientationchange', function () { scheduleRefresh(250); });
    scheduleRefresh(60);
  }

  window.LiquidGlass = { start: start, refresh: refresh, params: PARAMS, isBlink: isBlink() };

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', start);
  } else {
    start();
  }
})();

