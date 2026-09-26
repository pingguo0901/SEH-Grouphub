package stellarelite.sehg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stellarelite.sehg.rememberCameraLauncher

// 底部快捷栏
enum class WaTab(val label: String, val icon: ImageVector) {
    Updates("更新", Icons.Filled.DonutLarge),
    Calls("通话", Icons.Filled.Call),
    Tools("工具", Icons.Filled.Build),
    Chats("聊天", Icons.AutoMirrored.Filled.Chat),
    Settings("设置", Icons.Filled.Settings)
}

// 联系人会话
data class WaContact(
    val name: String,
    val phone: String,
    val lastMessage: String,
    val time: String,
    val unread: Int = 0
)

// 示例联系人列表（后续接 Supabase / WhatsApp webhook 数据源替换）
private val sampleContacts = listOf(
    WaContact("阿康（店长）", "+601162329701", "好的陈先生，今晚 7 点见！🍢", "14:35"),
    WaContact("张小姐", "+60123456789", "你好，想问下今天有什么优惠？", "14:31", unread = 2),
    WaContact("李先生", "+60198765432", "帮我订一份烤串套餐，谢谢", "13:05", unread = 1),
    WaContact("王先生", "+60155512345", "收到，明天中午见", "昨天"),
    WaContact("陈小姐", "+60155567890", "请问营业时间到几点？", "昨天", unread = 3),
    WaContact("刘先生", "+60133344455", "好的，谢谢！", "星期二"),
    WaContact("林女士", "+60122233344", "有包间吗？8 人", "星期一"),
    WaContact("赵先生", "+60111122233", "已付款，请查收", "星期日")
)

@Composable
fun WhatsAppScreen(onBack: () -> Unit) {
    var currentTab by remember { mutableStateOf(WaTab.Chats) }
    var openContact by remember { mutableStateOf<WaContact?>(null) }
    var showContactInfo by remember { mutableStateOf(false) }

    val contact = openContact
    if (contact != null) {
        if (showContactInfo) {
            ContactInfoScreen(contact = contact, onBack = { showContactInfo = false })
        } else {
            WhatsAppChatScreen(
                contactName = contact.name,
                onBack = { openContact = null },
                onOpenContactInfo = { showContactInfo = true }
            )
        }
        return
    }

    Column(Modifier.fillMaxSize().background(WaColors.Wallpaper)) {
        Box(Modifier.weight(1f)) {
            when (currentTab) {
                WaTab.Chats -> ChatsTab(onBack = onBack, onOpenContact = { openContact = it })
                else -> PlaceholderTab(currentTab.label)
            }
        }

        WaBottomBar(currentTab = currentTab, onSelect = { currentTab = it })
    }
}

@Composable
private fun ChatsTab(onBack: () -> Unit, onOpenContact: (WaContact) -> Unit) {
    val openCamera = rememberCameraLauncher()
    Column(Modifier.fillMaxSize().background(WaColors.Wallpaper)) {
        // 顶部栏：左上返回 + 三点；右上相机 + 加号
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(WaColors.Header)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "返回",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onBack)
            )
            Spacer(Modifier.width(8.dp))

            // 三点菜单
            var menuExpanded by remember { mutableStateOf(false) }
            Box {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "更多",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { menuExpanded = true }
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    containerColor = WaColors.ReceivedBubble
                ) {
                    DropdownMenuItem(
                        text = { Text("选择对话", color = WaColors.TextPrimary) },
                        onClick = { menuExpanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("全部已读", color = WaColors.TextPrimary) },
                        onClick = { menuExpanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("列表", color = WaColors.TextPrimary) },
                        onClick = { menuExpanded = false }
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Icon(
                Icons.Filled.CameraAlt,
                contentDescription = "相机",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = openCamera)
            )
            Spacer(Modifier.width(20.dp))
            Icon(
                Icons.Filled.Add,
                contentDescription = "添加",
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }

        // 页面标题
        Text(
            "聊天",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = WaColors.TextPrimary,
            modifier = Modifier.padding(start = 14.dp, top = 10.dp, bottom = 2.dp)
        )

        // 搜索框
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(WaColors.InputField)
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = null,
                    tint = WaColors.IconGrey,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("搜索", fontSize = 14.sp, color = WaColors.IconGrey)
            }
        }

        // 联系人聊天列表
        LazyColumn(Modifier.fillMaxSize()) {
            items(sampleContacts) { contact ->
                ContactRow(contact = contact, onClick = { onOpenContact(contact) })
            }
        }
    }
}

@Composable
private fun ContactRow(contact: WaContact, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(WaColors.MicGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(contact.name.take(1), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(contact.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = WaColors.TextPrimary)
            Spacer(Modifier.height(2.dp))
            Text(contact.lastMessage, fontSize = 13.sp, color = WaColors.IconGrey, maxLines = 1)
        }
        Spacer(Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(contact.time, fontSize = 11.sp, color = WaColors.IconGrey)
            if (contact.unread > 0) {
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(WaColors.MicGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Text(contact.unread.toString(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun PlaceholderTab(label: String) {
    Box(
        modifier = Modifier.fillMaxSize().background(WaColors.Wallpaper),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = WaColors.TextPrimary)
            Spacer(Modifier.height(6.dp))
            Text("功能待接入", fontSize = 13.sp, color = WaColors.IconGrey)
        }
    }
}

@Composable
private fun WaBottomBar(currentTab: WaTab, onSelect: (WaTab) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(WaColors.Header)
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
            .padding(vertical = 6.dp)
    ) {
        WaTab.entries.forEach { tab ->
            val selected = currentTab == tab
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelect(tab) }
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    tab.icon,
                    contentDescription = tab.label,
                    tint = if (selected) WaColors.MicGreen else WaColors.IconGrey,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    tab.label,
                    fontSize = 10.sp,
                    color = if (selected) WaColors.MicGreen else WaColors.IconGrey
                )
            }
        }
    }
}
