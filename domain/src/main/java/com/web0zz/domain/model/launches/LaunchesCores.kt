package com.web0zz.domain.model.launches

data class LaunchesCores(
    val core: String,
    val flight: Int?,
    val gridfins: Boolean?,
    val legs: Boolean?,
    val reused: Boolean?,
    val landingAttempt: Boolean?,
    val landingSuccess: String?,
    val landingType: String?,
    val landpad: String?
)
