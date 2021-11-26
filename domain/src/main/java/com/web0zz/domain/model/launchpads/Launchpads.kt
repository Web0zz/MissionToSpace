package com.web0zz.domain.model.launchpads

data class Launchpads(
    val fullName: String?,
    val id: String,
    val latitude: Double?,
    val launchAttempts: Int?,
    val launchSuccesses: Int?,
    val launches: List<String>?,
    val locality: String?,
    val longitude: Double?,
    val name: String?,
    val region: String?,
    val rockets: List<String>?,
    val status: String?,
    val timezone: String?
)