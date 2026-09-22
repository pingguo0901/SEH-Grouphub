package stellarelite.sehg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stellarelite.sehg.model.CompanyHrStats
import stellarelite.sehg.ui.theme.HoldingsColors

// 示例数据：待接入 Supabase 真实员工数据后替换
private val sampleCompanies = listOf(
    CompanyHrStats(
        companyNameZh = "星域控股集团",
        companyNameEn = "STELLAR ELITE HOLDINGS GROUP",
        note = "（包括旗下子公司）",
        totalEmployees = 128, active = 96, pending = 4,
        resigned = 12, resigningSoon = 2, onLeave = 8, onDuty = 6
    ),
    CompanyHrStats(
        companyNameZh = "星域臻旅",
        companyNameEn = "STELLAR ELITE ENTERPRISE",
        totalEmployees = 45, active = 36, pending = 2,
        resigned = 4, resigningSoon = 1, onLeave = 2, onDuty = 0
    ),
    CompanyHrStats(
        companyNameZh = "炙巷食铺",
        companyNameEn = "ZHI XIANG FOOD ENTERPRISE",
        totalEmployees = 32, active = 26, pending = 1,
        resigned = 3, resigningSoon = 0, onLeave = 1, onDuty = 1
    ),
    CompanyHrStats(
        companyNameZh = "星域科技",
        companyNameEn = "STELLAR TECH STUDIO",
        totalEmployees = 18, active = 15, pending = 1,
        resigned = 1, resigningSoon = 0, onLeave = 1, onDuty = 0
    )
)

private data class HrModule(val label: String, val icon: ImageVector)

private val hrModules = listOf(
    HrModule("员工名册", Icons.Filled.Badge),
    HrModule("员工档案", Icons.Filled.Folder),
    HrModule("考勤管理", Icons.Filled.PunchClock),
    HrModule("排班管理", Icons.Filled.CalendarMonth),
    HrModule("薪资管理", Icons.Filled.Payments),
    HrModule("申报报表", Icons.Filled.Assessment),
    HrModule("人事公告", Icons.Filled.Campaign),
    HrModule("人事审计记录", Icons.Filled.History)
)

@Composable
fun HrScreen() {
    PageScaffold("人事", "组织与人才", Icons.Filled.People) {
        // 4 张员工统计卡片
        sampleCompanies.forEach { company ->
            CompanyStatsCard(company)
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(8.dp))

        // 2 排 8 个人事功能入口
        hrModules.chunked(4).forEach { rowModules ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowModules.forEach { module ->
                    ModuleButton(module.label, module.icon, Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun CompanyStatsCard(stats: CompanyHrStats) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(HoldingsColors.Surface)
            .padding(16.dp)
    ) {
        // 公司名称
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                stats.companyNameZh,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = HoldingsColors.TextPrimary
            )
            if (stats.note.isNotEmpty()) {
                Spacer(Modifier.width(6.dp))
                Text(
                    stats.note,
                    fontSize = 12.sp,
                    color = HoldingsColors.TextMuted
                )
            }
        }
        Spacer(Modifier.height(2.dp))
        Text(
            stats.companyNameEn,
            fontSize = 10.sp,
            letterSpacing = 0.5.sp,
            color = HoldingsColors.TextMuted
        )

        Spacer(Modifier.height(14.dp))

        // 总员工人数
        Text("总员工人数", fontSize = 12.sp, color = HoldingsColors.TextSecondary)
        Spacer(Modifier.height(2.dp))
        Text(
            stats.totalEmployees.toString(),
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = HoldingsColors.Primary
        )

        Spacer(Modifier.height(14.dp))
        HorizontalDivider(color = HoldingsColors.SurfaceVariant)
        Spacer(Modifier.height(14.dp))

        // 6 项状态
        Row(Modifier.fillMaxWidth()) {
            StatCell("在职", stats.active, HoldingsColors.Success, Modifier.weight(1f))
            StatCell("待入职", stats.pending, HoldingsColors.Info, Modifier.weight(1f))
            StatCell("辞职", stats.resigned, HoldingsColors.Error, Modifier.weight(1f))
        }
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth()) {
            StatCell("即将辞职", stats.resigningSoon, HoldingsColors.Warning, Modifier.weight(1f))
            StatCell("放假", stats.onLeave, HoldingsColors.TextSecondary, Modifier.weight(1f))
            StatCell("值班", stats.onDuty, HoldingsColors.Accent, Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCell(label: String, value: Int, color: Color, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(label, fontSize = 11.sp, color = HoldingsColors.TextMuted)
        Spacer(Modifier.height(2.dp))
        Text(value.toString(), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
private fun ModuleButton(label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(HoldingsColors.Surface)
            .padding(vertical = 14.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = HoldingsColors.Accent,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.height(6.dp))
        Text(
            label,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            textAlign = TextAlign.Center,
            color = HoldingsColors.TextPrimary
        )
    }
}
