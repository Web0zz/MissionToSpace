package com.web0zz.domain.model.capsules

data class Capsules(
    val id: String,
    val landLandings: Int?,
    val lastUpdate: String?,
    val launches: List<String>?,
    val reuseCount: Int?,
    val serial: String?,
    val status: String?,
    val type: String?,
    val waterLandings: Int?
)