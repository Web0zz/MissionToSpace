package com.web0zz.domain.model.companyinfo

data class CompanyInfo(
    val ceo: String?,
    val coo: String?,
    val cto: String?,
    val ctoPropulsion: String?,
    val employees: Int?,
    val founded: Int?,
    val founder: String?,
    val headquarters: Headquarters?,
    val id: String,
    val launchSites: Int?,
    val infoLinks: InfoLinks?,
    val name: String?,
    val summary: String?,
    val testSites: Int?,
    val valuation: Long?,
    val vehicles: Int?
)