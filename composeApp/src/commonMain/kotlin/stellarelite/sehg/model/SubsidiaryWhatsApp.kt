package stellarelite.sehg.model

/**
 * 子公司 WhatsApp 快捷入口
 * @param nameZh 子公司中文名
 * @param nameEn 子公司英文名
 * @param phone 国际区号 + 号码（不含 "+"，例如 "85251403695"）；为空表示号码待配置
 */
data class SubsidiaryWhatsApp(
    val nameZh: String,
    val nameEn: String,
    val phone: String = ""
)

// 三个子公司 WhatsApp 入口（号码待董事长确认后补齐）
val subsidiaryWhatsAppList = listOf(
    SubsidiaryWhatsApp(
        nameZh = "炙巷食铺",
        nameEn = "ZHI XIANG FOOD ENTERPRISE",
        phone = "85251403695"
    ),
    SubsidiaryWhatsApp(
        nameZh = "星域臻旅",
        nameEn = "STELLAR ELITE ENTERPRISE",
        phone = "8617098925396"
    ),
    SubsidiaryWhatsApp(
        nameZh = "星域科技",
        nameEn = "STELLAR TECH STUDIO",
        phone = "6581945601"
    )
)
