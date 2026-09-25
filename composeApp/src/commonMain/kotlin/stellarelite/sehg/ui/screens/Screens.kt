package stellarelite.sehg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stellarelite.sehg.model.SubsidiaryWhatsApp
import stellarelite.sehg.model.subsidiaryWhatsAppList
import stellarelite.sehg.ui.theme.HoldingsColors

@Composable
fun HomeScreen(onOpenWhatsApp: () -> Unit) {
    val uriHandler = LocalUriHandler.current

    PageScaffold(
        title = "首页",
        subtitle = "星域控股集团 · 董事长驾驶舱",
        icon = Icons.Filled.Home
    ) {
        Text("欢迎回来，董事长", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = HoldingsColors.TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text("这里是集团全域概览，后续接入各板块核心数据。", fontSize = 14.sp, color = HoldingsColors.TextSecondary)

        Spacer(Modifier.height(24.dp))
        Text("子公司 WhatsApp", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = HoldingsColors.TextPrimary)
        Spacer(Modifier.height(12.dp))

        subsidiaryWhatsAppList.forEach { sub ->
            SubsidiaryWhatsAppCard(
                sub = sub,
                onClick = {
                    if (sub.nameZh == "炙巷食铺") {
                        onOpenWhatsApp()
                    } else if (sub.phone.isNotBlank()) {
                        uriHandler.openUri("https://wa.me/${sub.phone}")
                    }
                }
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun SubsidiaryWhatsAppCard(
    sub: SubsidiaryWhatsApp,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(HoldingsColors.Surface)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF25D366)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.AutoMirrored.Filled.Chat,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(
                sub.nameZh,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = HoldingsColors.TextPrimary
            )
            Spacer(Modifier.height(2.dp))
            Text(
                if (sub.phone.isNotBlank()) "WhatsApp 联系" else "号码待配置",
                fontSize = 12.sp,
                color = HoldingsColors.TextSecondary
            )
        }
        Icon(
            Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = HoldingsColors.TextMuted
        )
    }
}

@Composable
fun FinanceScreen() {
    PageScaffold("财务", "资金与账务", Icons.Filled.AccountBalanceWallet) {
        Text("财务板块", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = HoldingsColors.TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text("资金流、账务核算、预算与报表等内容将在此呈现。", fontSize = 14.sp, color = HoldingsColors.TextSecondary)
    }
}

@Composable
fun LegalScreen() {
    PageScaffold("法务", "合规与风控", Icons.Filled.Gavel) {
        Text("法务板块", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = HoldingsColors.TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text("合同审查、合规管理、风险防控等内容将在此呈现。", fontSize = 14.sp, color = HoldingsColors.TextSecondary)
    }
}

@Composable
fun AdminScreen() {
    PageScaffold("行政", "行政与后勤", Icons.Filled.Business) {
        Text("行政板块", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = HoldingsColors.TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text("办公资产、印章证照、后勤保障等内容将在此呈现。", fontSize = 14.sp, color = HoldingsColors.TextSecondary)
    }
}

@Composable
fun AuditScreen() {
    PageScaffold("内审", "审计与监督", Icons.Filled.VerifiedUser) {
        Text("内审板块", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = HoldingsColors.TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text("内部审计、流程监督、风险预警等内容将在此呈现。", fontSize = 14.sp, color = HoldingsColors.TextSecondary)
    }
}

@Composable
fun ProfileScreen() {
    PageScaffold("我", "董事长", Icons.Filled.Person) {
        Text("我", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = HoldingsColors.TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text("个人中心、账号与权限设置等内容将在此呈现。", fontSize = 14.sp, color = HoldingsColors.TextSecondary)
    }
}

@Composable
internal fun PageScaffold(
    title: String,
    subtitle: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HoldingsColors.Background)
    ) {
        // 顶部栏
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(HoldingsColors.Primary)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = HoldingsColors.Accent, modifier = Modifier.size(28.dp))
                Spacer(Modifier.width(10.dp))
                Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = HoldingsColors.Surface)
            }
            Spacer(Modifier.height(4.dp))
            Text(subtitle, fontSize = 13.sp, color = HoldingsColors.Surface.copy(alpha = 0.7f))
        }

        // 内容区
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            content = content
        )
    }
}
