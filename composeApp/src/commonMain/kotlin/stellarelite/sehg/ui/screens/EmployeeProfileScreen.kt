package stellarelite.sehg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stellarelite.sehg.model.EmployeeProfile
import stellarelite.sehg.ui.theme.HoldingsColors

// 示例数据：待接入 Supabase 真实员工数据后替换
private val sampleEmployees = listOf(
    EmployeeProfile("101", "陈晓明", "CHEN XIAOMING", "chenxm_dev", "星域科技", "工程师", "139-0000-0101"),
    EmployeeProfile("201", "郭富城", "GUO FUCHENG", "gfc_chef", "炙巷食铺", "厨师", "139-0000-0201"),
    EmployeeProfile("001", "黄志强", "HUANG ZHIQIANG", "hzq_driver", "星域臻旅", "司机", "138-0011-0001"),
    EmployeeProfile("002", "李娜", "LI NA", "lina_cs", "星域臻旅", "客服", "138-0011-0002"),
    EmployeeProfile("102", "林俊杰", "LIN JUNJIE", "linjj_design", "星域科技", "设计师", "139-0000-0102"),
    EmployeeProfile("202", "刘德华", "LIU DEHUA", "ldh_manager", "炙巷食铺", "店长", "139-0000-0202"),
    EmployeeProfile("301", "王芳", "WANG FANG", "wangfang_acct", "星域控股集团", "会计", "137-0000-0301"),
    EmployeeProfile("003", "吴彦祖", "WU YANZU", "wyz_driver", "星域臻旅", "司机", "138-0011-0003"),
    EmployeeProfile("302", "杨幂", "YANG MI", "yangmi_sec", "星域控股集团", "秘书", "137-0000-0302"),
    EmployeeProfile("004", "张伟", "ZHANG WEI", "zhangwei_driver", "星域臻旅", "司机", "138-0011-0004"),
    EmployeeProfile("203", "赵丽颖", "ZHAO LIYING", "zly_waiter", "炙巷食铺", "服务员", "139-0000-0203"),
    EmployeeProfile("103", "周杰伦", "ZHOU JIELUN", "zjl_director", "星域科技", "技术总监", "139-0000-0103"),
    EmployeeProfile("007", "阿七", "007", "aqi_driver", "星域臻旅", "司机", "138-0011-0007")
)

@Composable
fun EmployeeProfileScreen(onBack: () -> Unit) {
    var query by remember { mutableStateOf("") }

    val filtered = remember(query) {
        if (query.isBlank()) sampleEmployees
        else sampleEmployees.filter { it.id.contains(query.trim(), ignoreCase = true) }
    }

    val grouped = remember(filtered) {
        filtered
            .sortedBy { it.nameEn.uppercase() }
            .groupBy { emp ->
                val c = emp.nameEn.firstOrNull()?.uppercaseChar()
                if (c != null && c in 'A'..'Z') c.toString() else "#"
            }
            .entries
            .sortedBy { (k, _) -> if (k == "#") "ZZZZ" else k }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HoldingsColors.Background)
    ) {
        // 顶部栏
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(HoldingsColors.Primary)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回", tint = HoldingsColors.Surface)
            }
            Spacer(Modifier.width(4.dp))
            Text("员工档案", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = HoldingsColors.Surface)
        }

        // 搜索框
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(HoldingsColors.Surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
                tint = HoldingsColors.TextMuted,
                modifier = Modifier.padding(start = 14.dp).size(20.dp)
            )
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                singleLine = true,
                textStyle = TextStyle(fontSize = 15.sp, color = HoldingsColors.TextPrimary),
                cursorBrush = SolidColor(HoldingsColors.Accent),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp, vertical = 12.dp),
                decorationBox = { inner ->
                    if (query.isEmpty()) {
                        Text("搜索ID", fontSize = 15.sp, color = HoldingsColors.TextMuted)
                    }
                    inner()
                }
            )
            if (query.isNotEmpty()) {
                IconButton(onClick = { query = "" }) {
                    Icon(Icons.Filled.Clear, contentDescription = "清除", tint = HoldingsColors.TextMuted)
                }
            }
        }

        // 列表
        if (filtered.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("未找到相关员工", fontSize = 14.sp, color = HoldingsColors.TextMuted)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                grouped.forEach { (letter, emps) ->
                    item(key = "header-$letter") {
                        SectionHeader(letter)
                    }
                    items(emps, key = { it.id }) { emp ->
                        EmployeeProfileCard(emp)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(letter: String) {
    Text(
        letter,
        modifier = Modifier
            .fillMaxWidth()
            .background(HoldingsColors.SurfaceVariant)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = HoldingsColors.Accent
    )
}

@Composable
private fun EmployeeProfileCard(emp: EmployeeProfile) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(HoldingsColors.Surface)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 头像
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(avatarColor(emp.id)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                emp.nameZh.firstOrNull()?.toString() ?: "?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(Modifier.width(12.dp))

        // 信息
        Column(Modifier.weight(1f)) {
            // 行1：姓名中文 英文 ｜ ID
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${emp.nameZh}  ${emp.nameEn}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = HoldingsColors.TextPrimary,
                    modifier = Modifier.weight(1f)
                )
                Text("ID ${emp.id}", fontSize = 12.sp, color = HoldingsColors.TextMuted)
            }

            Spacer(Modifier.height(5.dp))

            // 行2：微信号 ｜ 子公司名称（职位）
            InfoLine("微信号", emp.wechat, "${emp.subsidiary}（${emp.position}）")
            Spacer(Modifier.height(2.dp))
            // 行3：手机号码 ｜ 子公司名称（职位）
            InfoLine("手机号码", emp.phone, "${emp.subsidiary}（${emp.position}）")
        }
    }
}

@Composable
private fun InfoLine(label: String, value: String, subsidiary: String) {
    Row {
        Text(
            "$label $value",
            fontSize = 12.sp,
            color = HoldingsColors.TextSecondary,
            modifier = Modifier.weight(1f)
        )
        Text(subsidiary, fontSize = 12.sp, color = HoldingsColors.TextMuted)
    }
}

private fun avatarColor(id: String): Color {
    val palette = listOf(
        Color(0xFF1E4A7A),
        Color(0xFF2E7D6B),
        Color(0xFF8A5A2B),
        Color(0xFF7A3E7A),
        Color(0xFF2E90FA),
        Color(0xFFB4442C)
    )
    val idx = (id.hashCode() and Int.MAX_VALUE) % palette.size
    return palette[idx]
}
