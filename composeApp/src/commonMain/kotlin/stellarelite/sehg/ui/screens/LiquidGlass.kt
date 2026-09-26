package stellarelite.sehg.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

// 液态玻璃配色（深色清透）
internal object GlassColors {
    val WallpaperTop = Color(0xFF111A22)
    val WallpaperMid = Color(0xFF14202B)
    val WallpaperBottom = Color(0xFF06090D)

    val TextPrimary = Color(0xFFF2F6FA)
    val TextSecondary = Color(0xFF98A6B2)
    val Accent = Color(0xFF0A84FF)
    val Danger = Color(0xFFFF453A)

    // 非玻璃面的实体填充色（输入框、接收气泡等）
    val GlassFill = Color(0xFF2C2C2E)
}

/**
 * 液态玻璃样式规格（对照 iOS 26.7 Liquid Glass 参数）
 * blurRadius: 背景模糊半径（backdrop blur，非 box blur）
 * maskColor/maskColorPressed: 玻璃蒙版色（默认/按压不同色值）
 * maskAlpha/maskAlphaPressed: 蒙版透明度
 * saturation/brightness/contrast: 色彩校正（iOS vibrancy）
 * edgeHighlight: 曲面边缘高光 opacity（极微弱）
 * grain: 细微胶片颗粒强度（很低）
 * 注：透镜折射 Haze 1.5.3 无对应 API，未实现（保持轻微、不强行模拟）。
 */
internal data class GlassSpec(
    val blurRadius: Dp,
    val maskColor: Color,
    val maskColorPressed: Color,
    val maskAlpha: Float,
    val maskAlphaPressed: Float,
    val saturation: Float,
    val saturationPressed: Float,
    val brightness: Float,
    val brightnessPressed: Float,
    val contrast: Float,
    val contrastPressed: Float,
    val edgeHighlight: Float,
    val edgeHighlightPressed: Float,
    val grain: Float
)

internal object GlassSpecs {
    // 左上角三点圆形按钮 48dp 正圆
    val circleButton = GlassSpec(
        blurRadius = 22.dp,
        maskColor = Color(0xFF2A2A2C),
        maskColorPressed = Color(0xFF1E1E20),
        maskAlpha = 0.36f,
        maskAlphaPressed = 0.58f,
        saturation = 1.32f,
        saturationPressed = 1.20f,
        brightness = 0.94f,
        brightnessPressed = 0.87f,
        contrast = 1.03f,
        contrastPressed = 1.07f,
        edgeHighlight = 0.07f,
        edgeHighlightPressed = 0.03f,
        grain = 0.025f
    )
    // 聊天会话列表卡片 圆角20dp 高度104dp
    val card = GlassSpec(
        blurRadius = 18.dp,
        maskColor = Color(0xFF2C2C2E),
        maskColorPressed = Color(0xFF222224),
        maskAlpha = 0.32f,
        maskAlphaPressed = 0.52f,
        saturation = 1.28f,
        saturationPressed = 1.15f,
        brightness = 0.95f,
        brightnessPressed = 0.88f,
        contrast = 1.02f,
        contrastPressed = 1.06f,
        edgeHighlight = 0.06f,
        edgeHighlightPressed = 0.02f,
        grain = 0.02f
    )
    // 顶部栏 / 底部栏
    val bar = GlassSpec(
        blurRadius = 22.dp,
        maskColor = Color(0xFF2A2A2C),
        maskColorPressed = Color(0xFF1E1E20),
        maskAlpha = 0.30f,
        maskAlphaPressed = 0.50f,
        saturation = 1.30f,
        saturationPressed = 1.18f,
        brightness = 0.95f,
        brightnessPressed = 0.88f,
        contrast = 1.02f,
        contrastPressed = 1.06f,
        edgeHighlight = 0.06f,
        edgeHighlightPressed = 0.02f,
        grain = 0.02f
    )
}

// 统一动效曲线 cubic-bezier(0.20, 0.90, 0.30, 1.00)
private val LiquidEasing = CubicBezierEasing(0.20f, 0.90f, 0.30f, 1.00f)
private const val PRESS_MS = 80
private const val RELEASE_MS = 120

/** 玻璃壁纸：玻璃面板背后的底层画面（带细微色调变化，供 backdrop blur 透出） */
@Composable
internal fun GlassWallpaper(modifier: Modifier = Modifier) {
    Box(
        modifier.background(
            Brush.verticalGradient(
                listOf(GlassColors.WallpaperTop, GlassColors.WallpaperMid, GlassColors.WallpaperBottom)
            )
        )
    )
}

/**
 * 构造饱和/亮度/对比度组合 ColorMatrix（iOS vibrancy 色彩校正）。
 * 顺序：先亮度+对比度，再饱和度。
 */
private fun buildGlassColorMatrix(saturation: Float, brightness: Float, contrast: Float): ColorMatrix {
    val sat = ColorMatrix()
    sat.setToSaturation(saturation)

    val scale = contrast * brightness
    val offset = 127.5f * (1f - contrast)
    val bc = ColorMatrix(
        floatArrayOf(
            scale, 0f, 0f, 0f, offset,
            0f, scale, 0f, 0f, offset,
            0f, 0f, scale, 0f, offset,
            0f, 0f, 0f, 1f, 0f
        )
    )

    val result = ColorMatrix(sat.values)
    result.timesAssign(bc)
    return result
}

/** 对内容应用色彩校正（ColorMatrix）。 */
private fun Modifier.colorMatrix(matrix: ColorMatrix): Modifier = drawWithContent {
    val paint = Paint().apply { colorFilter = ColorFilter.colorMatrix(matrix) }
    drawIntoCanvas { canvas ->
        canvas.saveLayer(Rect(Offset.Zero, size), paint)
        drawContent()
        canvas.restore()
    }
}

/**
 * 玻璃面板：真正的 backdrop blur（模糊背后内容），
 * 叠加蒙版色 + 色彩校正 + 细微颗粒 + 曲面边缘高光。无硬描边。
 */
@Composable
internal fun GlassSurface(
    hazeState: HazeState,
    spec: GlassSpec,
    shape: Shape,
    modifier: Modifier = Modifier,
    pressed: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    val animSpec = tween<Float>(if (pressed) PRESS_MS else RELEASE_MS, easing = LiquidEasing)

    val maskAlpha by animateFloatAsState(
        targetValue = if (pressed) spec.maskAlphaPressed else spec.maskAlpha,
        animationSpec = animSpec,
        label = "maskAlpha"
    )
    val maskColor by animateColorAsState(
        targetValue = if (pressed) spec.maskColorPressed else spec.maskColor,
        animationSpec = tween(if (pressed) PRESS_MS else RELEASE_MS, easing = LiquidEasing),
        label = "maskColor"
    )
    val edge by animateFloatAsState(
        targetValue = if (pressed) spec.edgeHighlightPressed else spec.edgeHighlight,
        animationSpec = animSpec,
        label = "edgeHighlight"
    )
    val saturation by animateFloatAsState(
        targetValue = if (pressed) spec.saturationPressed else spec.saturation,
        animationSpec = animSpec,
        label = "saturation"
    )
    val brightness by animateFloatAsState(
        targetValue = if (pressed) spec.brightnessPressed else spec.brightness,
        animationSpec = animSpec,
        label = "brightness"
    )
    val contrast by animateFloatAsState(
        targetValue = if (pressed) spec.contrastPressed else spec.contrast,
        animationSpec = animSpec,
        label = "contrast"
    )

    val style = HazeStyle(
        backgroundColor = Color.Transparent,
        tint = HazeTint(maskColor.copy(alpha = maskAlpha)),
        blurRadius = spec.blurRadius,
        noiseFactor = spec.grain,
        fallbackTint = HazeTint(spec.maskColor.copy(alpha = 0.5f))
    )

    val matrix = buildGlassColorMatrix(saturation, brightness, contrast)

    Box(
        modifier = modifier
            .clip(shape)
            .hazeEffect(state = hazeState, style = style)
            .colorMatrix(matrix)
            .then(
                Modifier.border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        0.0f to Color.White.copy(alpha = edge),
                        0.45f to Color.White.copy(alpha = edge * 0.25f),
                        1.0f to Color.Transparent
                    ),
                    shape = shape
                )
            ),
        content = content
    )
}

/** 圆形玻璃按钮：透明背景 + 半透明边框（所有按钮都是圆的） */
@Composable
internal fun GlassCircleButton(
    hazeState: HazeState,
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    iconSize: Dp = 22.dp,
    tint: Color = GlassColors.TextPrimary,
    selected: Boolean = false
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    // 半透明边框：默认轻微，按压时略微加深
    val borderColor by animateColorAsState(
        targetValue = if (pressed) Color.White.copy(alpha = 0.45f) else Color.White.copy(alpha = 0.25f),
        animationSpec = tween(if (pressed) PRESS_MS else RELEASE_MS, easing = LiquidEasing),
        label = "circleBorder"
    )

    // 透明背景：只保留 backdrop blur 透出底层，不叠加蒙版色
    val style = HazeStyle(
        backgroundColor = Color.Transparent,
        tint = HazeTint(Color.Transparent),
        blurRadius = 22.dp,
        noiseFactor = 0f,
        fallbackTint = HazeTint(Color.Transparent)
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .hazeEffect(state = hazeState, style = style)
            .border(1.dp, borderColor, CircleShape)
            .clickable(interactionSource = interaction, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = if (selected) GlassColors.Accent else tint,
            modifier = Modifier.size(iconSize)
        )
    }
}
