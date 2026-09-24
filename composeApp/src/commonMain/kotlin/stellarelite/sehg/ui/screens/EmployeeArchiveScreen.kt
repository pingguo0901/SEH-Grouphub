package stellarelite.sehg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stellarelite.sehg.model.EmployeeArchive
import stellarelite.sehg.model.EmployeeProfile
import stellarelite.sehg.ui.theme.HoldingsColors

// 档案页配色（对应设计图：白底 + 浅绿模块标题 + 灰色边框）
private val ArchiveGreen = Color(0xFF3E7C4F)      // 模块标题深绿（文字/强调）
private val ArchiveGreenLight = Color(0xFFC0D4C0) // 模块标题浅绿背景
private val ArchiveBorder = Color(0xFFBDBDBD)     // 表单边框
private val ArchiveLabel = Color(0xFF4A5A48)      // 字段标签绿灰
private val ArchiveText = Color(0xFF1E2E1A)       // 字段值深色

@Composable
fun EmployeeArchiveScreen(emp: EmployeeProfile, onBack: () -> Unit) {
    var archive by remember { mutableStateOf(EmployeeArchive.sampleFrom(emp)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // 顶部栏（应用内导航）
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
            Text("个人档案", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = HoldingsColors.Surface)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 16.dp)
        ) {
            // ===== 标题 =====
            Text(
                "员工个人档案",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = ArchiveText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(2.dp))
            Text(
                "MAKLUMAT PERIBADI PEKERJA",
                fontSize = 13.sp,
                letterSpacing = 1.sp,
                color = ArchiveGreen,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            // ===== 页眉信息区 + 照片 =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ArchiveBorder, RoundedCornerShape(4.dp))
                    .padding(12.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    HeaderField("档案编号", archive.archiveNo) { archive = archive.copy(archiveNo = it) }
                    HeaderField("业务主体", archive.businessEntity) { archive = archive.copy(businessEntity = it) }
                    HeaderField("联系系部", archive.contactDept) { archive = archive.copy(contactDept = it) }
                    HeaderField("部门", archive.department) { archive = archive.copy(department = it) }
                    HeaderField("岗位", archive.position) { archive = archive.copy(position = it) }
                    HeaderField("状态", archive.status) { archive = archive.copy(status = it) }
                }
                Spacer(Modifier.width(12.dp))
                // 照片框
                Box(
                    modifier = Modifier
                        .size(width = 96.dp, height = 120.dp)
                        .border(1.dp, ArchiveGreen, RoundedCornerShape(0.dp))
                        .background(ArchiveGreenLight.copy(alpha = 0.35f))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Text("照片", fontSize = 14.sp, color = ArchiveGreen, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(Modifier.height(14.dp))

            // ===== 模块1 个人基础资料 =====
            ArchiveSection("1. 个人基础资料") {
                TwoColField("姓名", archive.name) { archive = archive.copy(name = it) }
                TwoColField("性别", archive.gender) { archive = archive.copy(gender = it) }
                TwoColField("NRIC/Passport", archive.nricPassport) { archive = archive.copy(nricPassport = it) }
                TwoColField("EMF编号", archive.emfNo) { archive = archive.copy(emfNo = it) }
                TwoColField("出生日期", archive.birthDate) { archive = archive.copy(birthDate = it) }
                TwoColField("国籍", archive.nationality) { archive = archive.copy(nationality = it) }
                TwoColField("邮箱地址", archive.email) { archive = archive.copy(email = it) }
                TwoColField("婚姻状态", archive.maritalStatus) { archive = archive.copy(maritalStatus = it) }
                TwoColField("联系电话", archive.phone) { archive = archive.copy(phone = it) }
                TwoColField("居住地址", archive.address) { archive = archive.copy(address = it) }
                TwoColField("紧急联系人姓名", archive.emergencyName) { archive = archive.copy(emergencyName = it) }
                TwoColField("紧急联系人关系", archive.emergencyRelation) { archive = archive.copy(emergencyRelation = it) }
                TwoColField("紧急联系人联系电话", archive.emergencyPhone) { archive = archive.copy(emergencyPhone = it) }
            }

            // ===== 模块2 雇佣信息 =====
            ArchiveSection("2. 雇佣信息") {
                OneColField("基础薪资", archive.basicSalary) { archive = archive.copy(basicSalary = it) }
                OneColField("EPF会员编号", archive.epfNo) { archive = archive.copy(epfNo = it) }
                OneColField("SOCSO编号", archive.socsoNo) { archive = archive.copy(socsoNo = it) }
                OneColField("EIS编号", archive.eisNo) { archive = archive.copy(eisNo = it) }
                OneColField("LHDN税务编号", archive.lhdnNo) { archive = archive.copy(lhdnNo = it) }
                OneColField("银行名称", archive.bankName) { archive = archive.copy(bankName = it) }
                OneColField("银行持有人", archive.bankHolder) { archive = archive.copy(bankHolder = it) }
                OneColField("银行账户", archive.bankAccount) { archive = archive.copy(bankAccount = it) }
            }

            // ===== 模块3 雇佣合约信息 =====
            ArchiveSection("3. 雇佣合约信息") {
                TwoColField("入职日期", archive.joinDate) { archive = archive.copy(joinDate = it) }
                TwoColField("试用期截止", archive.probationEnd) { archive = archive.copy(probationEnd = it) }
                TwoColField("试用期起始", archive.probationStart) { archive = archive.copy(probationStart = it) }
                TwoColField("合约到期", archive.contractExpiry) { archive = archive.copy(contractExpiry = it) }
                TwoColField("雇佣类型", archive.employmentType) { archive = archive.copy(employmentType = it) }
                TwoColField("直属主管", archive.supervisor) { archive = archive.copy(supervisor = it) }
                TwoColField("工作地点", archive.workLocation) { archive = archive.copy(workLocation = it) }
            }

            // ===== 模块4 证件与附件 =====
            ArchiveSection("4. 证件与附件") {
                OneColField("NRIC/Passport复印件", archive.nricCopy) { archive = archive.copy(nricCopy = it) }
                OneColField("雇佣合约", archive.contractDoc) { archive = archive.copy(contractDoc = it) }
                OneColField("相关执照（驾照/行业）", archive.license) { archive = archive.copy(license = it) }
                OneColField("跨境资格/工作许可", archive.workPermit) { archive = archive.copy(workPermit = it) }
                OneColField("健康证明", archive.healthCert) { archive = archive.copy(healthCert = it) }
            }

            // ===== 模块5 假期与考勤摘要 =====
            ArchiveSection("5. 假期与考勤摘要") {
                TwoColField("年假剩余天数", archive.annualLeaveLeft) { archive = archive.copy(annualLeaveLeft = it) }
                TwoColField("系部联系电话", archive.deptPhone) { archive = archive.copy(deptPhone = it) }
                TwoColField("病假剩余天数", archive.sickLeaveLeft) { archive = archive.copy(sickLeaveLeft = it) }
                TwoColField("事假记录", archive.personalLeaveRecords) { archive = archive.copy(personalLeaveRecords = it) }
                TwoColField("最后年假日期", archive.lastAnnualLeave) { archive = archive.copy(lastAnnualLeave = it) }
                TwoColField("迟到记录", archive.lateRecords) { archive = archive.copy(lateRecords = it) }
                TwoColField("最后病假日期", archive.lastSickLeave) { archive = archive.copy(lastSickLeave = it) }
                TwoColField("旷工记录", archive.absenceRecords) { archive = archive.copy(absenceRecords = it) }
                TwoColField("最后事假日期", archive.lastPersonalLeave) { archive = archive.copy(lastPersonalLeave = it) }
                TwoColField("状态记录", archive.statusRecords) { archive = archive.copy(statusRecords = it) }
            }

            // ===== 模块6 变更记录与认证 =====
            ArchiveSection("6. 变更记录与认证") {
                OneColField("修改日期", archive.changeDate) { archive = archive.copy(changeDate = it) }
                OneColField("修改操作人", archive.changeOperator) { archive = archive.copy(changeOperator = it) }
                OneColField("变更前", archive.changeBefore) { archive = archive.copy(changeBefore = it) }
                OneColField("变更后", archive.changeAfter) { archive = archive.copy(changeAfter = it) }
            }

            Spacer(Modifier.height(14.dp))

            // ===== 底部签名区 =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, ArchiveBorder, RoundedCornerShape(4.dp))
                    .padding(12.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    SignatureLine("员工签名")
                    SignatureLine("HR签名")
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    SignatureLine("上级主管签名")
                    SignatureLine("日期")
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

// ===== 布局组件 =====

@Composable
private fun ArchiveSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        // 浅绿标题块（左侧斜角装饰）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ArchiveGreenLight)
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(20.dp)
                    .background(ArchiveGreen)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = ArchiveGreen
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, ArchiveBorder)
                .padding(horizontal = 10.dp, vertical = 8.dp),
            content = content
        )
    }
}

@Composable
private fun ColumnScope.HeaderField(
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            fontSize = 12.sp,
            color = ArchiveLabel,
            modifier = Modifier.width(66.dp)
        )
        EditableText(
            value = value,
            onChange = onChange,
            modifier = Modifier.weight(1f),
            fontSize = 12
        )
    }
}

@Composable
private fun ColumnScope.TwoColField(
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, ArchiveBorder.copy(alpha = 0.5f))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            fontSize = 12.sp,
            color = ArchiveLabel,
            modifier = Modifier.width(118.dp)
        )
        EditableText(
            value = value,
            onChange = onChange,
            modifier = Modifier.weight(1f),
            fontSize = 13
        )
    }
}

@Composable
private fun ColumnScope.OneColField(
    label: String,
    value: String,
    onChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, ArchiveBorder.copy(alpha = 0.5f))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            fontSize = 12.sp,
            color = ArchiveLabel,
            modifier = Modifier.width(140.dp)
        )
        EditableText(
            value = value,
            onChange = onChange,
            modifier = Modifier.weight(1f),
            fontSize = 13
        )
    }
}

@Composable
private fun EditableText(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fontSize: Int = 13
) {
    BasicTextField(
        value = value,
        onValueChange = onChange,
        singleLine = true,
        textStyle = TextStyle(fontSize = fontSize.sp, color = ArchiveText),
        cursorBrush = SolidColor(ArchiveGreen),
        modifier = modifier
    )
}

@Composable
private fun SignatureLine(label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$label：",
            fontSize = 12.sp,
            color = ArchiveLabel
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(20.dp)
                .border(0.5.dp, ArchiveBorder.copy(alpha = 0.7f))
        )
    }
}
