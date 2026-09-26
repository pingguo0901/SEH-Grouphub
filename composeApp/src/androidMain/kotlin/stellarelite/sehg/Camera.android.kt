package stellarelite.sehg

import android.content.Intent
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberCameraLauncher(): () -> Unit {
    val context = LocalContext.current
    return {
        try {
            val photoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val videoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            val chooser = Intent.createChooser(photoIntent, "拍照 / 录视频").apply {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(videoIntent))
            }
            context.startActivity(chooser)
        } catch (_: Exception) {
            // 无可用相机应用时静默忽略
        }
    }
}
