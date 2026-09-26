package stellarelite.sehg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * 液态玻璃（Liquid Glass）· 深色清透配色
 * 参考 tomagranate/liquid-glass 参数映射到 Compose：
 *   blur(清透→低雾度) / rimLight(边缘高光) / specular(镜面) / tint(着色) / shadow(投影)
 */
internal object GlassColors {
    val WallpaperTop = Color(0xFF111A22)
    val WallpaperBottom = Color(0xFF06090D)

    val GlassFillBright = Color(0xFF2A3540)   // 玻璃受光面
    val GlassFill = Color(0xFF171F27)         // 玻璃主体
    val GlassEdge = Color(0x1AFFFFFF)         // 弱描边
    val RimTop = Color(0x73FFFFFF)            // 顶部高光
    val RimTopStrong = Color(0x99FFFFFF)      // 顶部高光（强）
    val Specular = Color(0x1FFFFFFF)          // 镜面

    val TextPrimary = Color(0xFFF2F6FA)
    val TextSecondary = Color(0xFF98A6B2)
    val Accent = Color(0xFF0A84FF)            // iOS 蓝
    val AccentGlass = Color(0x260A84FF)       // 蓝色玻璃着色
    val Danger = Color(0xFFFF453A)
}

/** 玻璃面板：受光渐变底 + 顶部高光描边（Liquid Glass 标志性边缘高光） */
internal fun Modifier.glassPanel(shape: Shape): Modifier = this
    .clip(shape)
    .background(
        Brush.verticalGradient(
            0f to GlassColors.GlassFillBright,
            1f to GlassColors.GlassFill
        )
    )
    .border(
        width = 1.dp,
        brush = Brush.verticalGradient(
            0f to GlassColors.RimTopStrong,
            0.4f to GlassColors.GlassEdge,
            1f to GlassColors.GlassEdge
        ),
        shape = shape
    )

/** 圆形玻璃按钮（所有按钮都是圆的） */
@Composable
internal fun GlassCircleButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    iconSize: Dp = 20.dp,
    tint: Color = GlassColors.TextPrimary,
    selected: Boolean = false
) {
    Box(
        modifier = modifier
            .size(size)
            .glassPanel(CircleShape)
            .clickable(onClick = onClick),
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
