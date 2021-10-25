package com.web0zz.network.model

import com.google.gson.annotations.SerializedName

data class FairingsDto(
    @SerializedName("reused") val reused: Boolean,
    @SerializedName("recovery_attempt") val recoveryAttempt: Boolean,
    @SerializedName("recovered") val recovered: Boolean,
    @SerializedName("ships") val ships: List<String>
)
