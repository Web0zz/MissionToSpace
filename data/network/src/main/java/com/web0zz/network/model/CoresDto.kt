package com.web0zz.network.model

import com.google.gson.annotations.SerializedName

data class CoresDto(
    @SerializedName("core") val core: String,
    @SerializedName("flight") val flight: Int?,
    @SerializedName("gridfins") val gridfins: Boolean?,
    @SerializedName("legs") val legs: Boolean?,
    @SerializedName("reused") val reused: Boolean?,
    @SerializedName("landing_attempt") val landingAttempt: Boolean?,
    @SerializedName("landing_success") val landingSuccess: String?,
    @SerializedName("landing_type") val landingType: String?,
    @SerializedName("landpad") val landpad: String?
)
