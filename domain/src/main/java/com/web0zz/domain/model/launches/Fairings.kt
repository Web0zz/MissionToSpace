package com.web0zz.domain.model.launches

data class Fairings(
    val reused: Boolean?,
    val recoveryAttempt: Boolean?,
    val recovered: Boolean?,
    val ships: List<String>?
)
