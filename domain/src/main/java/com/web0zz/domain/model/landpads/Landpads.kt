package com.web0zz.domain.model.landpads

data class Landpads(
    val details: String?,
    val fullName: String?,
    val id: String,
    val landingAttempts: Int?,
    val landingSuccesses: Int?,
    val latitude: Double?,
    val launches: List<String>?,
    val locality: String?,
    val longitude: Double?,
    val name: String?,
    val region: String?,
    val status: String?,
    val type: String?,
    val wikipedia: String?
)