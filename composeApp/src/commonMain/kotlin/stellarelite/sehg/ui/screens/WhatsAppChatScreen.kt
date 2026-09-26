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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    onBack: () -> Unit,
    onOpenContactInfo: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(GlassColors.WallpaperTop, GlassColors.WallpaperBottom))
            )
    ) {
        ChatHeader(contactName = contactName, onBack = onBack, onOpenContactInfo = onOpenContactInfo)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
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
private fun ChatHeader(contactName: String, onBack: () -> Unit, onOpenContactInfo: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .glassPanel(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlassCircleButton(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "返回",
            onClick = onBack
        )
        Spacer(Modifier.width(8.dp))
        // 圆形头像
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(GlassColors.Accent, Color(0xFF5AA7FF))))
                .clickable(onClick = onOpenContactInfo),
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
        GlassCircleButton(Icons.Filled.Videocam, contentDescription = "视频", onClick = { })
        Spacer(Modifier.width(8.dp))
        GlassCircleButton(Icons.Filled.Call, contentDescription = "通话", onClick = { })
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
                .clip(RoundedCornerShape(10.dp))
                .background(GlassColors.GlassFill.copy(alpha = 0.6f))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            fontSize = 12.sp,
            color = GlassColors.TextSecondary
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
                        RoundedCornerShape(topStart = 18.dp, topEnd = 6.dp, bottomStart = 18.dp, bottomEnd = 18.dp)
                    } else {
                        RoundedCornerShape(topStart = 6.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 18.dp)
                    }
                )
                .background(
                    if (msg.isSent) {
                        Brush.linearGradient(listOf(GlassColors.Accent, Color(0xFF5AA7FF)))
                    } else {
                        Brush.linearGradient(
                            listOf(
                                GlassColors.GlassFill.copy(alpha = 0.85f),
                                GlassColors.GlassFill.copy(alpha = 0.85f)
                            )
                        )
                    }
                )
                .padding(start = 12.dp, end = 10.dp, top = 8.dp, bottom = 6.dp)
        ) {
            Column {
                Text(msg.text, fontSize = 15.sp, lineHeight = 20.sp, color = if (msg.isSent) Color.White else GlassColors.TextPrimary)
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        msg.time,
                        fontSize = 10.sp,
                        color = if (msg.isSent) Color.White.copy(alpha = 0.8f) else GlassColors.TextSecondary
                    )
                    if (msg.isSent) {
                        Spacer(Modifier.width(3.dp))
                        Icon(
                            Icons.Filled.DoneAll,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.9f),
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
            .glassPanel(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlassCircleButton(Icons.Filled.Add, contentDescription = "添加", onClick = { }, size = 38.dp)
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(22.dp))
                .background(GlassColors.GlassFill)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text("消息", fontSize = 15.sp, color = GlassColors.TextSecondary)
        }
        Spacer(Modifier.width(8.dp))
        GlassCircleButton(Icons.Filled.EmojiEmotions, contentDescription = "表情", onClick = { }, size = 38.dp)
        Spacer(Modifier.width(8.dp))
        GlassCircleButton(Icons.Filled.PhotoCamera, contentDescription = "相机", onClick = { }, size = 38.dp)
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(GlassColors.Accent, Color(0xFF5AA7FF)))),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Mic, contentDescription = "语音", tint = Color.White, modifier = Modifier.size(22.dp))
        }
    }
}
