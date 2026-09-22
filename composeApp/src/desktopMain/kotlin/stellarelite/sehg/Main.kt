package stellarelite.sehg

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "星域控股集团") {
        App()
    }
}
