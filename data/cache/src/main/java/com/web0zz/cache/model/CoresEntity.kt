package com.web0zz.cache.model

data class CoresEntity(
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
