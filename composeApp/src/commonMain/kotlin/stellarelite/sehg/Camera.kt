package stellarelite.sehg

import androidx.compose.runtime.Composable

/**
 * 返回一个打开手机相机的回调（可拍照 / 录视频）。
 * Android 通过 Intent 拉起系统相机；iOS/desktop 暂为空实现。
 */
@Composable
expect fun rememberCameraLauncher(): () -> Unit
