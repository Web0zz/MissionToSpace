package com.web0zz.domain.model.ships

data class Ships(
    val abs: Int?,
    val active: Boolean?,
    val shipClass: Int?,
    val courseDeg: Any?,
    val homePort: String?,
    val id: String,
    val image: String?,
    val imo: Int?,
    val lastAisUpdate: Any?,
    val latitude: Any?,
    val launches: List<String>?,
    val legacyId: String?,
    val link: String?,
    val longitude: Any?,
    val massKg: Int?,
    val massLbs: Int?,
    val mmsi: Int?,
    val model: Any?,
    val name: String?,
    val roles: List<String>?,
    val speedKn: Any?,
    val status: String?,
    val type: String?,
    val yearBuilt: Int?
)