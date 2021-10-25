package com.web0zz.cache.model

data class FairingsEntity(
    val reused: Boolean,
    val recoveryAttempt: Boolean,
    val recovered: Boolean,
    val ships: List<String>
)
