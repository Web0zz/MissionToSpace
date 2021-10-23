package com.web0zz.network.model

import com.google.gson.annotations.SerializedName

data class FailuresDto(
    @SerializedName("time") val time : Int,
    @SerializedName("altitude") val altitude : String,
    @SerializedName("reason") val reason : String
)
