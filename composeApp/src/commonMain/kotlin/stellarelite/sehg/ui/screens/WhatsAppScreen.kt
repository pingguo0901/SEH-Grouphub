package stellarelite.sehg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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

// 列表筛选
enum class ChatFilter(val label: String) {
    All("全部"),
    Unread("未读"),
    Favorite("特别关注"),
    Group("群组")
}

// 联系人会话
data class WaContact(
    val name: String,
    val phone: String,
    val lastMessage: String,
    val time: String,
    val unread: Int = 0,
    val favorite: Boolean = false,
    val isGroup: Boolean = false
) {
    // 选择/去重用唯一键（群组无号码时退化为名称）
    val key: String get() = phone.ifBlank { name }
}

// 示例联系人列表（后续接 Supabase / WhatsApp webhook 数据源替换）
private val sampleContacts = listOf(
    WaContact("阿康（店长）", "+601162329701", "好的陈先生，今晚 7 点见！🍢", "14:35"),
    WaContact("张小姐", "+60123456789", "你好，想问下今天有什么优惠？", "14:31", unread = 2, favorite = true),
    WaContact("李先生", "+60198765432", "帮我订一份烤串套餐，谢谢", "13:05", unread = 1),
    WaContact("王先生", "+60155512345", "收到，明天中午见", "昨天"),
    WaContact("陈小姐", "+60155567890", "请问营业时间到几点？", "昨天", unread = 3, favorite = true),
    WaContact("刘先生", "+60133344455", "好的，谢谢！", "星期二"),
    WaContact("林女士", "+60122233344", "有包间吗？8 人", "星期一"),
    WaContact("赵先生", "+60111122233", "已付款，请查收", "星期日"),
    WaContact("炙巷食铺工作群", "", "阿康：今日营业至 22:00", "14:20", isGroup = true)
)

@Composable
fun WhatsAppScreen(onBack: () -> Unit) {
    var currentTab by remember { mutableStateOf(WaTab.Chats) }
    var openContact by remember { mutableStateOf<WaContact?>(null) }
    var showContactInfo by remember { mutableStateOf(false) }
    var chatSelecting by remember { mutableStateOf(false) }

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

    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(GlassColors.WallpaperTop, GlassColors.WallpaperBottom)
                )
            )
    ) {
        Box(Modifier.weight(1f)) {
            when (currentTab) {
                WaTab.Chats -> ChatsTab(
                    onBack = onBack,
                    onOpenContact = { openContact = it },
                    onSelectingChange = { chatSelecting = it }
                )
                else -> PlaceholderTab(currentTab.label)
            }
        }

        if (!chatSelecting) {
            WaBottomBar(currentTab = currentTab, onSelect = { currentTab = it })
        }
    }
}

@Composable
private fun ChatsTab(
    onBack: () -> Unit,
    onOpenContact: (WaContact) -> Unit,
    onSelectingChange: (Boolean) -> Unit
) {
    val openCamera = rememberCameraLauncher()
    var selecting by remember { mutableStateOf(false) }
    var selectedKeys by remember { mutableStateOf(setOf<String>()) }
    var contacts by remember { mutableStateOf(sampleContacts) }
    var filter by remember { mutableStateOf(ChatFilter.All) }

    fun startSelecting() {
        selecting = true
        selectedKeys = emptySet()
        onSelectingChange(true)
    }

    fun endSelecting() {
        selecting = false
        selectedKeys = emptySet()
        onSelectingChange(false)
    }

    val filteredContacts = when (filter) {
        ChatFilter.All -> contacts
        ChatFilter.Unread -> contacts.filter { it.unread > 0 }
        ChatFilter.Favorite -> contacts.filter { it.favorite }
        ChatFilter.Group -> contacts.filter { it.isGroup }
    }

    Column(Modifier.fillMaxSize()) {
        // 顶部玻璃栏：左上返回 + 三点（选择模式变完成）；右上相机 + 加号
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .glassPanel(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlassCircleButton(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "返回",
                onClick = { if (selecting) endSelecting() else onBack() }
            )
            Spacer(Modifier.width(10.dp))

            if (selecting) {
                // 选择模式：三点切换为完成（打勾）圆形按钮
                GlassCircleButton(
                    Icons.Filled.Check,
                    contentDescription = "完成",
                    onClick = { endSelecting() },
                    selected = true
                )
            } else {
                // 三点菜单
                var menuExpanded by remember { mutableStateOf(false) }
                Box {
                    GlassCircleButton(
                        Icons.Filled.MoreVert,
                        contentDescription = "更多",
                        onClick = { menuExpanded = true }
                    )
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        containerColor = GlassColors.GlassFill
                    ) {
                        DropdownMenuItem(
                            text = { Text("选择对话", color = GlassColors.TextPrimary) },
                            onClick = {
                                menuExpanded = false
                                startSelecting()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("全部已读", color = GlassColors.TextPrimary) },
                            onClick = {
                                menuExpanded = false
                                contacts = contacts.map { it.copy(unread = 0) }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("列表", color = GlassColors.TextPrimary) },
                            onClick = { menuExpanded = false }
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            if (!selecting) {
                GlassCircleButton(
                    Icons.Filled.CameraAlt,
                    contentDescription = "相机",
                    onClick = openCamera
                )
                Spacer(Modifier.width(10.dp))
                GlassCircleButton(
                    Icons.Filled.Add,
                    contentDescription = "添加",
                    onClick = { }
                )
            }
        }

        // 页面标题（选择模式切换为已选数量）
        Text(
            if (selecting) "已选择 " + selectedKeys.size + " 个" else "聊天",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = GlassColors.TextPrimary,
            modifier = Modifier.padding(start = 16.dp, top = 14.dp, bottom = 4.dp)
        )

        // 搜索框（玻璃胶囊）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .glassPanel(RoundedCornerShape(22.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
                tint = GlassColors.TextSecondary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text("搜索", fontSize = 14.sp, color = GlassColors.TextSecondary)
        }

        // 列表快捷栏（筛选）：chips 横向滚动，+ 圆形按钮固定在右侧
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChatFilter.entries.forEach { f ->
                    FilterChip(label = f.label, selected = filter == f, onClick = { filter = f })
                    Spacer(Modifier.width(8.dp))
                }
            }
            Spacer(Modifier.width(8.dp))
            GlassCircleButton(
                Icons.Filled.Add,
                contentDescription = "新建",
                onClick = { },
                size = 34.dp,
                iconSize = 18.dp
            )
        }

        // 联系人聊天列表
        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
        ) {
            items(filteredContacts, key = { it.key }) { contact ->
                ContactRow(
                    contact = contact,
                    selecting = selecting,
                    selected = contact.key in selectedKeys,
                    onClick = {
                        if (selecting) {
                            selectedKeys =
                                if (contact.key in selectedKeys) selectedKeys - contact.key
                                else selectedKeys + contact.key
                        } else {
                            onOpenContact(contact)
                        }
                    }
                )
                Spacer(Modifier.height(8.dp))
            }
        }

        // 选择模式底部操作栏
        if (selecting) {
            SelectionActionBar(
                selectedCount = selectedKeys.size,
                onAllRead = { contacts = contacts.map { it.copy(unread = 0) } },
                onList = { /* 列表功能待接入 */ },
                onRead = {
                    contacts = contacts.map {
                        if (it.key in selectedKeys) it.copy(unread = 0) else it
                    }
                    selectedKeys = emptySet()
                },
                onClear = {
                    contacts = contacts.filterNot { it.key in selectedKeys }
                    selectedKeys = emptySet()
                }
            )
        }
    }
}

@Composable
private fun FilterChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(
                if (selected) {
                    Brush.linearGradient(listOf(GlassColors.Accent, Color(0xFF5AA7FF)))
                } else {
                    Brush.verticalGradient(listOf(GlassColors.GlassFillBright, GlassColors.GlassFill))
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 15.dp, vertical = 7.dp)
    ) {
        Text(
            label,
            fontSize = 13.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (selected) Color.White else GlassColors.TextSecondary
        )
    }
}

@Composable
private fun ContactRow(
    contact: WaContact,
    selecting: Boolean,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .glassPanel(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selecting) {
            Icon(
                if (selected) Icons.Filled.CheckCircle else Icons.Filled.RadioButtonUnchecked,
                contentDescription = if (selected) "已选择" else "未选择",
                tint = if (selected) GlassColors.Accent else GlassColors.TextSecondary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.width(12.dp))
        }

        // 圆形头像
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(listOf(GlassColors.Accent, Color(0xFF5AA7FF)))
                ),
            contentAlignment = Alignment.Center
        ) {
            if (contact.isGroup) {
                Icon(
                    Icons.Filled.Group,
                    contentDescription = "群组",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(contact.name.take(1), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(contact.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = GlassColors.TextPrimary)
                if (contact.favorite) {
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "特别关注",
                        tint = Color(0xFFF7C948),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Spacer(Modifier.height(2.dp))
            Text(contact.lastMessage, fontSize = 13.sp, color = GlassColors.TextSecondary, maxLines = 1)
        }
        Spacer(Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.End) {
            Text(contact.time, fontSize = 11.sp, color = GlassColors.TextSecondary)
            if (contact.unread > 0) {
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(GlassColors.Accent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(contact.unread.toString(), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun SelectionActionBar(
    selectedCount: Int,
    onAllRead: () -> Unit,
    onList: () -> Unit,
    onRead: () -> Unit,
    onClear: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .glassPanel(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (selectedCount == 0) {
            SelectionBarButton(Icons.Filled.DoneAll, "全部已读", onClick = onAllRead, modifier = Modifier.weight(1f))
        } else {
            SelectionBarButton(Icons.AutoMirrored.Filled.List, "列表", onClick = onList, modifier = Modifier.weight(1f))
            SelectionBarButton(Icons.Filled.DoneAll, "已读", onClick = onRead, modifier = Modifier.weight(1f))
            SelectionBarButton(Icons.Filled.Delete, "清除", onClick = onClear, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun SelectionBarButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = label, tint = GlassColors.Accent, modifier = Modifier.size(22.dp))
        Spacer(Modifier.height(2.dp))
        Text(label, fontSize = 11.sp, color = GlassColors.Accent)
    }
}

@Composable
private fun PlaceholderTab(label: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = GlassColors.TextPrimary)
            Spacer(Modifier.height(6.dp))
            Text("功能待接入", fontSize = 13.sp, color = GlassColors.TextSecondary)
        }
    }
}

@Composable
private fun WaBottomBar(currentTab: WaTab, onSelect: (WaTab) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .glassPanel(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
            .padding(vertical = 8.dp)
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
                    tint = if (selected) GlassColors.Accent else GlassColors.TextSecondary,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    tab.label,
                    fontSize = 10.sp,
                    color = if (selected) GlassColors.Accent else GlassColors.TextSecondary
                )
            }
        }
    }
}
