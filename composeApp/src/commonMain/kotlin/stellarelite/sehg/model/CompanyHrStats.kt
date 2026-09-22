package stellarelite.sehg.model

/**
 * 集团/子公司员工统计
 */
data class CompanyHrStats(
    val companyNameZh: String,
    val companyNameEn: String,
    val note: String = "",
    val totalEmployees: Int,
    val active: Int,        // 在职
    val pending: Int,       // 待入职
    val resigned: Int,      // 辞职
    val resigningSoon: Int, // 即将辞职
    val onLeave: Int,       // 放假
    val onDuty: Int         // 值班
)
