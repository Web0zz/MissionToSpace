package com.web0zz.domain.model.crew

data class Crew(
    val agency: String?,
    val id: String,
    val image: String?,
    val launches: List<String>?,
    val name: String?,
    val status: String?,
    val wikipedia: String?
)