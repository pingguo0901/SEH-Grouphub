package stellarelite.sehg.model

/**
 * 员工个人档案（完整表单）
 * 对应设计图：员工个人档案 / MAKLUMAT PERIBADI PEKERJA
 */
data class EmployeeArchive(
    // 页眉信息
    val archiveNo: String = "",        // 档案编号
    val businessEntity: String = "",   // 业务主体
    val contactDept: String = "",      // 联系系部
    val department: String = "",       // 部门
    val position: String = "",         // 岗位
    val status: String = "",           // 状态标签
    val photoUrl: String = "",         // 照片

    // 模块1 个人基础资料
    val name: String = "",             // 姓名
    val gender: String = "",           // 性别
    val nricPassport: String = "",     // NRIC/Passport
    val emfNo: String = "",            // EMF编号
    val birthDate: String = "",        // 出生日期
    val nationality: String = "",      // 国籍
    val email: String = "",            // 邮箱地址
    val maritalStatus: String = "",    // 婚姻状态
    val phone: String = "",            // 联系电话
    val address: String = "",          // 居住地址
    val emergencyName: String = "",    // 紧急联系人姓名
    val emergencyRelation: String = "",// 紧急联系人关系
    val emergencyPhone: String = "",   // 紧急联系人联系电话

    // 模块2 雇佣信息
    val basicSalary: String = "",      // 基础薪资
    val epfNo: String = "",            // EPF会员编号
    val socsoNo: String = "",          // SOCSO编号
    val eisNo: String = "",            // EIS编号
    val lhdnNo: String = "",           // LHDN税务编号
    val bankName: String = "",         // 银行名称
    val bankHolder: String = "",       // 银行持有人
    val bankAccount: String = "",      // 银行账户

    // 模块3 雇佣合约信息
    val joinDate: String = "",         // 入职日期
    val probationStart: String = "",   // 试用期起始
    val probationEnd: String = "",     // 试用期截止
    val contractExpiry: String = "",   // 合约到期
    val employmentType: String = "",   // 雇佣类型
    val workLocation: String = "",     // 工作地点
    val supervisor: String = "",       // 直属主管

    // 模块4 证件与附件
    val nricCopy: String = "",         // NRIC/Passport复印件
    val contractDoc: String = "",      // 雇佣合约
    val license: String = "",          // 相关执照（驾照/行业）
    val workPermit: String = "",       // 跨境资格/工作许可
    val healthCert: String = "",       // 健康证明

    // 模块5 假期与考勤摘要
    val annualLeaveLeft: String = "",  // 年假剩余天数
    val sickLeaveLeft: String = "",    // 病假剩余天数
    val lastAnnualLeave: String = "",  // 最后年假日期
    val lastSickLeave: String = "",    // 最后病假日期
    val lastPersonalLeave: String = "",// 最后事假日期
    val deptPhone: String = "",        // 系部联系电话
    val personalLeaveRecords: String = "", // 事假记录
    val lateRecords: String = "",      // 迟到记录
    val absenceRecords: String = "",   // 旷工记录
    val statusRecords: String = "",    // 状态记录

    // 模块6 变更记录与认证
    val changeDate: String = "",       // 修改日期
    val changeOperator: String = "",   // 修改操作人
    val changeBefore: String = "",     // 变更前
    val changeAfter: String = ""       // 变更后
) {
    companion object {
        /**
         * 由简要档案生成一份完整个人档案示例数据（待接入 Supabase 后替换）
         */
        fun sampleFrom(emp: EmployeeProfile): EmployeeArchive = EmployeeArchive(
            archiveNo = "A-${emp.id}",
            businessEntity = emp.subsidiary,
            contactDept = emp.subsidiary,
            department = emp.subsidiary,
            position = emp.position,
            status = "在职",
            name = emp.nameZh,
            gender = "男",
            nricPassport = "900101-${emp.id}-0000",
            emfNo = "EMF${emp.id}",
            birthDate = "1990-01-01",
            nationality = "马来西亚",
            email = "${emp.wechat}@stellarelite.com",
            maritalStatus = "未婚",
            phone = emp.phone,
            address = "Kuala Lumpur, Malaysia",
            emergencyName = "待填写",
            emergencyRelation = "待填写",
            emergencyPhone = "待填写",
            basicSalary = "RM 3,000",
            epfNo = "EPF-${emp.id}",
            socsoNo = "SOCSO-${emp.id}",
            eisNo = "EIS-${emp.id}",
            lhdnNo = "LHDN-${emp.id}",
            bankName = "Maybank",
            bankHolder = emp.nameEn,
            bankAccount = "1122-${emp.id}-8899",
            joinDate = "2023-06-01",
            probationStart = "2023-06-01",
            probationEnd = "2023-09-01",
            contractExpiry = "长期",
            employmentType = "全职",
            workLocation = emp.subsidiary,
            supervisor = "董事长",
            nricCopy = "已提交",
            contractDoc = "已提交",
            license = if (emp.position == "司机") "已提交" else "不适用",
            workPermit = "已提交",
            healthCert = "已提交",
            annualLeaveLeft = "10 天",
            sickLeaveLeft = "8 天",
            lastAnnualLeave = "2026-08-10",
            lastSickLeave = "2026-07-05",
            lastPersonalLeave = "2026-06-18",
            deptPhone = emp.phone,
            personalLeaveRecords = "1 次",
            lateRecords = "0 次",
            absenceRecords = "0 次",
            statusRecords = "正常",
            changeDate = "2026-09-24",
            changeOperator = "小聪",
            changeBefore = "—",
            changeAfter = "—"
        )
    }
}
