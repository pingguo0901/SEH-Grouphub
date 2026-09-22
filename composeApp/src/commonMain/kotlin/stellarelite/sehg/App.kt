package stellarelite.sehg

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stellarelite.sehg.ui.screens.*
import stellarelite.sehg.ui.theme.HoldingsColors

enum class Page(val title: String) {
    Home("首页"),
    HR("人事"),
    Finance("财务"),
    Legal("法务"),
    Admin("行政"),
    Audit("内审"),
    Profile("我")
}

private val pageIcon: (Page) -> ImageVector = { page ->
    when (page) {
        Page.Home -> Icons.Filled.Home
        Page.HR -> Icons.Filled.People
        Page.Finance -> Icons.Filled.AccountBalanceWallet
        Page.Legal -> Icons.Filled.Gavel
        Page.Admin -> Icons.Filled.Business
        Page.Audit -> Icons.Filled.VerifiedUser
        Page.Profile -> Icons.Filled.Person
    }
}

@Composable
fun App() {
    var currentPage by remember { mutableStateOf(Page.Home) }

    Box(Modifier.fillMaxSize().background(HoldingsColors.Background)) {
        when (currentPage) {
            Page.Home -> HomeScreen()
            Page.HR -> HrScreen()
            Page.Finance -> FinanceScreen()
            Page.Legal -> LegalScreen()
            Page.Admin -> AdminScreen()
            Page.Audit -> AuditScreen()
            Page.Profile -> ProfileScreen()
        }

        HomeDock(
            currentPage = currentPage,
            onNavigate = { currentPage = it },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun HomeDock(
    currentPage: Page,
    onNavigate: (Page) -> Unit,
    modifier: Modifier = Modifier
) {
    val menuExpanded = remember { mutableStateOf(false) }
    val pressing = remember { mutableStateOf(false) }
    val highlighted = remember { mutableStateOf<Page?>(null) }
    val dockBounds = remember { mutableStateOf(Rect.Zero) }
    val itemBounds = remember { mutableStateListOf<Rect>().apply { repeat(6) { add(Rect.Zero) } } }

    val menuItems = listOf(Page.HR, Page.Finance, Page.Legal, Page.Admin, Page.Audit, Page.Profile)

    fun hitTest(windowPos: Offset): Page? {
        if (!menuExpanded.value) return null
        menuItems.forEachIndexed { i, page ->
            if (itemBounds[i].contains(windowPos)) return page
        }
        return null
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(410.dp)
            .onGloballyPositioned { dockBounds.value = it.boundsInWindow() }
            .pointerInput(Unit) {
                awaitEachGesture {
                    try {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        var longPressed = false
                        pressing.value = true
                        var windowPos = Offset(
                            dockBounds.value.left + down.position.x,
                            dockBounds.value.top + down.position.y
                        )
                        var ended = false
                        while (!ended) {
                            val event = awaitPointerEvent()
                            val change = event.changes.firstOrNull()
                            if (change == null) {
                                ended = true
                                break
                            }
                            windowPos = Offset(
                                dockBounds.value.left + change.position.x,
                                dockBounds.value.top + change.position.y
                            )
                            if (!longPressed && (change.uptimeMillis - down.uptimeMillis) >= 3000L) {
                                longPressed = true
                                menuExpanded.value = true
                            }
                            if (menuExpanded.value) {
                                highlighted.value = hitTest(windowPos)
                            }
                            if (!change.pressed) {
                                ended = true
                            }
                        }

                        val selected = hitTest(windowPos)
                        if (menuExpanded.value && selected != null) {
                            onNavigate(selected)
                        } else if (!longPressed) {
                            onNavigate(Page.Home)
                        }
                    } finally {
                        menuExpanded.value = false
                        highlighted.value = null
                        pressing.value = false
                    }
                }
            }
    ) {
        // 弹出菜单
        if (menuExpanded.value) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                menuItems.forEachIndexed { i, page ->
                    MenuItemButton(
                        page = page,
                        highlighted = highlighted.value == page,
                        modifier = Modifier.onGloballyPositioned { itemBounds[i] = it.boundsInWindow() }
                    )
                }
            }
        }

        // 首页按钮
        val scale by animateFloatAsState(if (pressing.value || menuExpanded.value) 1.08f else 1f)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .scale(scale)
                .size(64.dp)
                .clip(CircleShape)
                .background(if (menuExpanded.value) HoldingsColors.Accent else HoldingsColors.Primary),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text("首页", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
private fun MenuItemButton(
    page: Page,
    highlighted: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .width(140.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(if (highlighted) HoldingsColors.Accent else HoldingsColors.Primary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            pageIcon(page),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(page.title, fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Medium)
    }
}
