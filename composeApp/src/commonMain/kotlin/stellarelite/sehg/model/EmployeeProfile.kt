package stellarelite.sehg.model

/**
 * 员工档案
 */
data class EmployeeProfile(
    val id: String,
    val nameZh: String,
    val nameEn: String,
    val wechat: String,
    val subsidiary: String,
    val position: String,
    val phone: String
)
