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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// WhatsApp 深色主题配色
private object WaColors {
    val Header = Color(0xFF202C33)        // 标题栏深色
    val Wallpaper = Color(0xFF0B141A)     // 聊天背景深色
    val SentBubble = Color(0xFF005C4B)    // 发送气泡深绿
    val ReceivedBubble = Color(0xFF202C33) // 接收气泡深灰
    val TextPrimary = Color(0xFFE9EDEF)
    val TextSecondary = Color(0xFF8696A0)
    val InputBar = Color(0xFF202C33)
    val InputField = Color(0xFF2A3942)
    val IconGrey = Color(0xFF8696A0)
    val MicGreen = Color(0xFF00A884)
    val ReadBlue = Color(0xFF53BDEB)
    val DatePillBg = Color(0xFF182229)
}

data class ChatMessage(
    val text: String,
    val isSent: Boolean,
    val time: String
)

// 示例对话（炙巷食铺客服），董事长可随时替换为真实话术
private val sampleMessages = listOf(
    ChatMessage("您好，欢迎光临炙巷食铺 🍢", isSent = false, time = "14:30"),
    ChatMessage("你好，想问问今天有什么优惠？", isSent = true, time = "14:31"),
    ChatMessage("今天有 2 个活动：满 100 减 10，套餐第二份半价", isSent = false, time = "14:32"),
    ChatMessage("好的，帮我订今晚 7 点，两位", isSent = true, time = "14:33"),
    ChatMessage("收到，已为您预留今晚 7 点两位，请问贵姓？", isSent = false, time = "14:33"),
    ChatMessage("姓陈", isSent = true, time = "14:34"),
    ChatMessage("好的陈先生，今晚 7 点见！🍢", isSent = false, time = "14:35")
)

@Composable
fun WhatsAppChatScreen(
    contactName: String = "炙巷食铺",
    onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        ChatHeader(contactName = contactName, onBack = onBack)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(WaColors.Wallpaper),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 8.dp)
        ) {
            item { DatePill("今天") }
            items(sampleMessages) { msg ->
                MessageBubble(msg)
            }
        }

        ChatInputBar()
    }
}

@Composable
private fun ChatHeader(contactName: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(WaColors.Header)
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
            .padding(horizontal = 4.dp, vertical = 6.dp),
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
        Spacer(Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(WaColors.MicGreen),
            contentAlignment = Alignment.Center
        ) {
            Text(
                contactName.take(1),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(contactName, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            Text("在线", fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
        }
        Icon(Icons.Filled.Videocam, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(18.dp))
        Icon(Icons.Filled.Call, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(18.dp))
        Icon(Icons.Filled.MoreVert, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun DatePill(label: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(WaColors.DatePillBg)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 12.sp,
            color = WaColors.TextSecondary
        )
    }
}

@Composable
private fun MessageBubble(msg: ChatMessage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = if (msg.isSent) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .clip(
                    if (msg.isSent) {
                        RoundedCornerShape(topStart = 8.dp, topEnd = 2.dp, bottomStart = 8.dp, bottomEnd = 8.dp)
                    } else {
                        RoundedCornerShape(topStart = 2.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 8.dp)
                    }
                )
                .background(if (msg.isSent) WaColors.SentBubble else WaColors.ReceivedBubble)
                .padding(start = 10.dp, end = 8.dp, top = 6.dp, bottom = 4.dp)
        ) {
            Column {
                Text(msg.text, fontSize = 15.sp, lineHeight = 20.sp, color = WaColors.TextPrimary)
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(msg.time, fontSize = 10.sp, color = WaColors.TextSecondary)
                    if (msg.isSent) {
                        Spacer(Modifier.width(3.dp))
                        Icon(
                            Icons.Filled.DoneAll,
                            contentDescription = null,
                            tint = WaColors.ReadBlue,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatInputBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(WaColors.InputBar)
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.EmojiEmotions, contentDescription = null, tint = WaColors.IconGrey, modifier = Modifier.size(26.dp))
        Spacer(Modifier.width(6.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(WaColors.InputField)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text("消息", fontSize = 15.sp, color = WaColors.TextSecondary)
        }
        Spacer(Modifier.width(6.dp))
        Icon(Icons.Filled.AttachFile, contentDescription = null, tint = WaColors.IconGrey, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(6.dp))
        Icon(Icons.Filled.PhotoCamera, contentDescription = null, tint = WaColors.IconGrey, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(6.dp))
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(WaColors.MicGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Mic, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
        }
    }
}
