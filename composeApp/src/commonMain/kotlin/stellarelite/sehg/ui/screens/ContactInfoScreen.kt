package stellarelite.sehg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DangerRed = Color(0xFFF15C6D)

@Composable
fun ContactInfoScreen(
    contact: WaContact,
    onBack: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(GlassColors.WallpaperTop, GlassColors.WallpaperBottom))
            )
    ) {
        // 顶部栏：左返回 + 右编辑
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
            Spacer(Modifier.weight(1f))
            GlassCircleButton(
                Icons.Filled.Edit,
                contentDescription = "编辑",
                onClick = { }
            )
        }

        LazyColumn(Modifier.fillMaxSize()) {
            // 头像 + 名称 + 号码
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 28.dp, bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(GlassColors.Accent, Color(0xFF5AA7FF)))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            contact.name.take(1),
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(Modifier.height(14.dp))
                    Text(contact.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = GlassColors.TextPrimary)
                    Spacer(Modifier.height(4.dp))
                    Text(contact.phone, fontSize = 14.sp, color = GlassColors.TextSecondary)
                }
            }

            // 三个圆形按钮：语音通话 / 视频通话 / 搜索
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CircleAction(Icons.Filled.Call, "语音通话")
                    CircleAction(Icons.Filled.Videocam, "视频通话")
                    CircleAction(Icons.Filled.Search, "搜索")
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = GlassColors.GlassEdge)
            }

            // 添加备注
            item { InfoRow(Icons.Filled.Edit, "添加备注") }

            // 设置列表
            item { InfoRow(Icons.AutoMirrored.Filled.List, "列表") }
            item { InfoRow(Icons.Filled.Folder, "影音内容、链接和文档") }
            item { InfoRow(Icons.Filled.Star, "已加星标") }
            item { InfoRow(Icons.Filled.Notifications, "通知") }
            item { InfoRow(Icons.Filled.Palette, "聊天主题") }
            item { InfoRow(Icons.Filled.Image, "保存到“照片”") }
            item { InfoRow(Icons.Filled.Timer, "限时消息") }
            item { InfoRow(Icons.Filled.Lock, "锁定聊天") }
            item { InfoRow(Icons.Filled.Shield, "高级聊天隐私") }
            item { InfoRow(Icons.Filled.Security, "加密") }
            item { InfoRow(Icons.Filled.Info, "联系人详情") }

            item {
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = GlassColors.GlassEdge)
            }

            // 共同群组
            item {
                Text(
                    "共同群组",
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GlassColors.TextPrimary
                )
            }
            item {
                Text(
                    "没有共同群组",
                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 12.dp),
                    fontSize = 13.sp,
                    color = GlassColors.TextSecondary
                )
            }

            // 与联系人建立群组 / 添加到群组
            item { InfoRow(Icons.Filled.GroupAdd, "与“${contact.name}”建立群组") }
            item { InfoRow(Icons.Filled.PersonAdd, "添加到群组") }

            item {
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = GlassColors.GlassEdge)
            }

            // 操作项
            item { InfoRow(Icons.Filled.Share, "分享联系人信息") }
            item { InfoRow(Icons.Filled.Star, "添加到“特别关注”") }
            item { InfoRow(Icons.AutoMirrored.Filled.PlaylistAdd, "添加到列表") }
            item { InfoRow(Icons.Filled.Download, "导出聊天") }
            item { InfoRow(Icons.Filled.Delete, "清空聊天", danger = true) }
            item { InfoRow(Icons.Filled.Block, "拉黑“${contact.name}”", danger = true) }
            item { InfoRow(Icons.Filled.Report, "举报“${contact.name}”", danger = true) }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun CircleAction(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .glassPanel(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = GlassColors.Accent, modifier = Modifier.size(24.dp))
        }
        Spacer(Modifier.height(6.dp))
        Text(label, fontSize = 11.sp, color = GlassColors.TextPrimary)
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    danger: Boolean = false
) {
    val color = if (danger) DangerRed else GlassColors.TextPrimary
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = if (danger) DangerRed else GlassColors.TextSecondary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(28.dp))
        Text(label, fontSize = 15.sp, color = color)
    }
}
