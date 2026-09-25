package stellarelite.sehg

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
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
fun App(
    onCheckUpdate: (suspend () -> VersionInfo?)? = null,
    onApplyUpdate: (suspend (VersionInfo, (Long, Long) -> Unit) -> String?)? = null
) {
    var currentPage by remember { mutableStateOf(Page.Home) }
    var showWhatsApp by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    var updateInfo by remember { mutableStateOf<VersionInfo?>(null) }
    var updating by remember { mutableStateOf(false) }
    var updateProgress by remember { mutableStateOf(0f) }
    var updateError by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        onCheckUpdate?.let { checkFn ->
            try {
                val info = checkFn()
                if (info != null) {
                    updateInfo = info
                    showUpdateDialog = true
                }
            } catch (_: Exception) { }
        }
    }

    if (showWhatsApp) {
        WhatsAppScreen(onBack = { showWhatsApp = false })
    } else {
        Column(Modifier.fillMaxSize().background(HoldingsColors.Background)) {
            Box(Modifier.weight(1f)) {
                when (currentPage) {
                    Page.Home -> HomeScreen(onOpenWhatsApp = { showWhatsApp = true })
                    Page.HR -> HrScreen()
                    Page.Finance -> FinanceScreen()
                    Page.Legal -> LegalScreen()
                    Page.Admin -> AdminScreen()
                    Page.Audit -> AuditScreen()
                    Page.Profile -> ProfileScreen()
                }
            }

            BottomNavBar(
                currentPage = currentPage,
                onNavigate = { currentPage = it }
            )
        }
    }

    // 更新弹窗
    if (showUpdateDialog && updateInfo != null) {
        if (updating) {
            AlertDialog(
                onDismissRequest = {},
                containerColor = HoldingsColors.Surface,
                title = {
                    Text(
                        "正在更新 v${updateInfo!!.versionName}",
                        color = HoldingsColors.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("正在下载新版本，请稍候…", color = HoldingsColors.TextSecondary, fontSize = 14.sp)
                        LinearProgressIndicator(
                            progress = { updateProgress },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text("${(updateProgress * 100).toInt()}%", color = HoldingsColors.Accent, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                },
                confirmButton = {}
            )
        } else {
            AlertDialog(
                onDismissRequest = { showUpdateDialog = false },
                containerColor = HoldingsColors.Surface,
                title = {
                    Text(
                        "发现新版本 v${updateInfo!!.versionName}",
                        color = HoldingsColors.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                text = {
                    Column {
                        Text(
                            updateInfo!!.changelog.replace("- ", "• "),
                            color = HoldingsColors.TextSecondary,
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        )
                        if (updateError != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("⚠️ $updateError", color = HoldingsColors.Error, fontSize = 13.sp)
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            updateError = null
                            scope.launch {
                                val fn = onApplyUpdate
                                if (fn == null) {
                                    updateError = "更新功能不可用"
                                } else {
                                    updating = true
                                    updateProgress = 0f
                                    val err = fn.invoke(updateInfo!!) { done, total ->
                                        if (total > 0) updateProgress = done.toFloat() / total.toFloat()
                                    }
                                    if (err != null) {
                                        updating = false
                                        updateError = err
                                    } else {
                                        showUpdateDialog = false
                                        updating = false
                                    }
                                }
                            }
                        }
                    ) {
                        Text("立即更新", color = HoldingsColors.Accent, fontWeight = FontWeight.SemiBold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showUpdateDialog = false }) {
                        Text("稍后", color = HoldingsColors.TextMuted)
                    }
                }
            )
        }
    }
}

@Composable
private fun BottomNavBar(
    currentPage: Page,
    onNavigate: (Page) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(HoldingsColors.NavBar)
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
            .padding(horizontal = 4.dp, vertical = 6.dp)
    ) {
        Page.entries.forEach { page ->
            val selected = currentPage == page
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNavigate(page) }
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    pageIcon(page),
                    contentDescription = page.title,
                    tint = if (selected) HoldingsColors.Accent else HoldingsColors.TextMuted,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    page.title,
                    fontSize = 10.sp,
                    color = if (selected) HoldingsColors.Accent else HoldingsColors.TextMuted,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}
