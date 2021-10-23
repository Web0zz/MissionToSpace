package com.web0zz.domain.model

data class Cores(
    val coreId: Int = 0,
    val core : String,
    val flight : Int,
    val gridfins : Boolean,
    val legs : Boolean,
    val reused : Boolean,
    val landingAttempt : Boolean,
    val landingSuccess : String,
    val landingType : String,
    val landpad : String
)
