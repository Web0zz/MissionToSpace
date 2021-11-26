package com.web0zz.domain.model.cores

data class Cores(
    val asdsAttempts: Int?,
    val asdsLandings: Int?,
    val block: Int?,
    val id: String,
    val lastUpdate: String?,
    val launches: List<String>?,
    val reuseCount: Int?,
    val rtlsAttempts: Int?,
    val rtlsLandings: Int?,
    val serial: String?,
    val status: String?
)