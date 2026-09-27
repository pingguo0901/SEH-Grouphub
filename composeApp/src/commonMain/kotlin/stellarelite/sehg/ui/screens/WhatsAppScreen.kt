package stellarelite.sehg.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
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
    val key: String get() = phone.ifBlank { name }
}

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
    var cardContact by remember { mutableStateOf<WaContact?>(null) }
    var showAvatarCard by remember { mutableStateOf(false) }
    var directInfoContact by remember { mutableStateOf<WaContact?>(null) }
    val hazeState = remember { HazeState() }

    // 头像卡片「详情」直接进入联系人信息页面
    val directInfo = directInfoContact
    if (directInfo != null) {
        ContactInfoScreen(contact = directInfo, onBack = { directInfoContact = null })
        return
    }

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

    Box(Modifier.fillMaxSize()) {
        // 底层壁纸：backdrop blur 的 source（被玻璃模糊的内容）
        GlassWallpaper(
            Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
        )

        Column(Modifier.fillMaxSize()) {
            Box(Modifier.weight(1f)) {
                when (currentTab) {
                    WaTab.Chats -> ChatsTab(
                        hazeState = hazeState,
                        onBack = onBack,
                        onOpenContact = { openContact = it },
                        onOpenAvatarCard = { cardContact = it; showAvatarCard = true },
                        onSelectingChange = { chatSelecting = it }
                    )
                    else -> PlaceholderTab(currentTab.label)
                }
            }
        }

        // 底部快捷栏：透明浮层，叠在聊天列表上方，背后透出聊天记录
        if (!chatSelecting) {
            WaBottomBar(
                hazeState = hazeState,
                currentTab = currentTab,
                onSelect = { currentTab = it },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        // 头像详情卡片：点头像从原位放大居中，关闭缩回
        AnimatedVisibility(
            visible = showAvatarCard,
            enter = fadeIn(tween(180)) + scaleIn(initialScale = 0.6f, animationSpec = tween(240)),
            exit = fadeOut(tween(160)) + scaleOut(targetScale = 0.6f, animationSpec = tween(180))
        ) {
            cardContact?.let { c ->
                AvatarCardPopup(
                    contact = c,
                    onInfo = { showAvatarCard = false },
                    onVoiceCall = { showAvatarCard = false },
                    onDetail = {
                        showAvatarCard = false
                        directInfoContact = c
                    },
                    onDismiss = { showAvatarCard = false }
                )
            }
        }
    }
}

@Composable
private fun ChatsTab(
    hazeState: HazeState,
    onBack: () -> Unit,
    onOpenContact: (WaContact) -> Unit,
    onOpenAvatarCard: (WaContact) -> Unit,
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

    // 上滑折叠进度：0 = 完全展开，1 = 标题已收进顶部栏
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val collapseRangePx = with(density) { 150.dp.toPx() }
    val collapseProgress by remember {
        derivedStateOf {
            if (listState.firstVisibleItemIndex > 0) 1f
            else (listState.firstVisibleItemScrollOffset / collapseRangePx).coerceIn(0f, 1f)
        }
    }

    Column(Modifier.fillMaxSize()) {
        // 顶部透明栏：无背景板块，聊天记录从背后透出
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassCircleButton(
                    hazeState = hazeState,
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "返回",
                    onClick = { if (selecting) endSelecting() else onBack() },
                    borderOnly = true
                )
                Spacer(Modifier.width(10.dp))

                if (selecting) {
                    GlassCircleButton(
                        hazeState = hazeState,
                        Icons.Filled.Check,
                        contentDescription = "完成",
                        onClick = { endSelecting() },
                        selected = true,
                        borderOnly = true
                    )
                } else {
                    var menuExpanded by remember { mutableStateOf(false) }
                    Box {
                        GlassCircleButton(
                            hazeState = hazeState,
                            Icons.Filled.MoreVert,
                            contentDescription = "更多",
                            onClick = { menuExpanded = true },
                            borderOnly = true
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

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (!selecting) {
                        Text(
                            "聊天",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GlassColors.TextPrimary,
                            modifier = Modifier.graphicsLayer { alpha = collapseProgress }
                        )
                    }
                }

                if (!selecting) {
                    GlassCircleButton(
                        hazeState = hazeState,
                        Icons.Filled.CameraAlt,
                        contentDescription = "相机",
                        onClick = openCamera,
                        borderOnly = true
                    )
                    Spacer(Modifier.width(10.dp))
                    GlassCircleButton(
                        hazeState = hazeState,
                        Icons.Filled.Add,
                        contentDescription = "添加",
                        onClick = { },
                        borderOnly = true
                    )
                }
            }
        }

        // 联系人聊天列表（头部：标题 + 搜索框 + 筛选栏 随列表上滑逐渐消失；底部留白让最后一条滚到悬浮快捷栏上方）
        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = listState,
            contentPadding = PaddingValues(top = 6.dp, bottom = 88.dp)
        ) {
            item(key = "header") {
                Column(Modifier.fillMaxWidth()) {
                    // 页面标题
                    Text(
                        if (selecting) "已选择 " + selectedKeys.size + " 个" else "聊天",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = GlassColors.TextPrimary,
                        modifier = Modifier.padding(start = 16.dp, top = 14.dp, bottom = 4.dp)
                    )

                    // 搜索框（按钮风格：透明背景 + 半透明边框）
                    GlassButtonSurface(
                        hazeState = hazeState,
                        shape = RoundedCornerShape(22.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
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
                    }

                    // 列表快捷栏（筛选）
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
                                FilterChip(hazeState = hazeState, label = f.label, selected = filter == f, onClick = { filter = f })
                                Spacer(Modifier.width(8.dp))
                            }
                        }
                        Spacer(Modifier.width(8.dp))
                        GlassCircleButton(
                            hazeState = hazeState,
                            Icons.Filled.Add,
                            contentDescription = "新建",
                            onClick = { },
                            size = 34.dp,
                            iconSize = 18.dp
                        )
                    }
                }
            }

            items(filteredContacts, key = { it.key }) { contact ->
                Box(Modifier.padding(horizontal = 10.dp)) {
                    ContactRow(
                        hazeState = hazeState,
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
                        },
                        onAvatarClick = { onOpenAvatarCard(contact) }
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        // 选择模式底部操作栏
        if (selecting) {
            SelectionActionBar(
                hazeState = hazeState,
                selectedCount = selectedKeys.size,
                onAllRead = { contacts = contacts.map { it.copy(unread = 0) } },
                onList = { },
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
private fun FilterChip(hazeState: HazeState, label: String, selected: Boolean, onClick: () -> Unit) {
    GlassButtonSurface(
        hazeState = hazeState,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (selected) {
                        Brush.linearGradient(listOf(GlassColors.Accent, Color(0xFF5AA7FF)))
                    } else {
                        Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
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
}

@Composable
private fun ContactRow(
    hazeState: HazeState,
    contact: WaContact,
    selecting: Boolean,
    selected: Boolean,
    onClick: () -> Unit,
    onAvatarClick: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()

    GlassSurface(
        hazeState = hazeState,
        spec = GlassSpecs.card,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp)
            .clickable(interactionSource = interaction, indication = null, onClick = onClick),
        pressed = pressed
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 16.dp),
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

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(GlassColors.Accent, Color(0xFF5AA7FF)))
                    )
                    .then(if (!selecting) Modifier.clickable(onClick = onAvatarClick) else Modifier),
                contentAlignment = Alignment.Center
            ) {
                if (contact.isGroup) {
                    Icon(
                        Icons.Filled.Group,
                        contentDescription = "群组",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Text(contact.name.take(1), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(contact.name, fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = GlassColors.TextPrimary)
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
                Spacer(Modifier.height(4.dp))
                Text(contact.lastMessage, fontSize = 14.sp, color = GlassColors.TextSecondary, maxLines = 1)
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
}

@Composable
private fun SelectionActionBar(
    hazeState: HazeState,
    selectedCount: Int,
    onAllRead: () -> Unit,
    onList: () -> Unit,
    onRead: () -> Unit,
    onClear: () -> Unit
) {
    GlassSurface(
        hazeState = hazeState,
        spec = GlassSpecs.bar,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
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

/** 按钮风格表面：透明背景 + 10dp 磨砂 + 半透明边框（搜索框、筛选按钮、底部快捷栏统一风格） */
@Composable
private fun GlassButtonSurface(
    hazeState: HazeState,
    shape: Shape,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val style = HazeStyle(
        backgroundColor = Color.Transparent,
        tint = HazeTint(Color.Transparent),
        blurRadius = 10.dp,
        noiseFactor = 0f,
        fallbackTint = HazeTint(Color.Transparent)
    )
    Box(
        modifier = modifier
            .clip(shape)
            .hazeEffect(state = hazeState, style = style)
            .border(1.dp, Color.White.copy(alpha = 0.25f), shape),
        content = content
    )
}

@Composable
private fun WaBottomBar(
    hazeState: HazeState,
    currentTab: WaTab,
    onSelect: (WaTab) -> Unit,
    modifier: Modifier = Modifier
) {
    // 只要边框、不要背景板块：透过背部内容做半透明磨砂（blur + 轻微半透明白色）
    val shape = RoundedCornerShape(26.dp)
    val style = HazeStyle(
        backgroundColor = Color.Transparent,
        tint = HazeTint(Color.White.copy(alpha = 0.08f)),
        blurRadius = 12.dp,
        noiseFactor = 0f,
        fallbackTint = HazeTint(Color.White.copy(alpha = 0.10f))
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clip(shape)
            .hazeEffect(state = hazeState, style = style)
            .border(1.dp, Color.White.copy(alpha = 0.25f), shape)
    ) {
        Row(Modifier.fillMaxWidth()) {
            WaTab.entries.forEach { tab ->
                val selected = currentTab == tab
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(tab) }
                        .padding(vertical = 8.dp),
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
}

/** 聊天主页面点头像弹出的详情卡片：四方形头像 + 下方三个图标按钮（信息 / 语音通话 / 详情） */
@Composable
private fun AvatarCardPopup(
    contact: WaContact,
    onInfo: () -> Unit,
    onVoiceCall: () -> Unit,
    onDetail: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        // 半透明遮罩，点击关闭
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = onDismiss)
        )

        // 居中卡片
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF2A2A2E), Color(0xFF1B1B1F))
                    )
                )
                .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(28.dp))
                .padding(horizontal = 28.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 四方形头像
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.linearGradient(listOf(GlassColors.Accent, Color(0xFF5AA7FF)))),
                contentAlignment = Alignment.Center
            ) {
                if (contact.isGroup) {
                    Icon(
                        Icons.Filled.Group,
                        contentDescription = "群组",
                        tint = Color.White,
                        modifier = Modifier.size(52.dp)
                    )
                } else {
                    Text(
                        contact.name.take(1),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                CardIconButton(Icons.Filled.Info, "信息", onInfo)
                CardIconButton(Icons.Filled.Call, "语音通话", onVoiceCall)
                CardIconButton(Icons.Filled.Person, "详情", onDetail)
            }
        }
    }
}

@Composable
private fun CardIconButton(icon: ImageVector, contentDescription: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.1f))
            .border(1.dp, Color.White.copy(alpha = 0.25f), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = GlassColors.Accent,
            modifier = Modifier.size(26.dp)
        )
    }
}
