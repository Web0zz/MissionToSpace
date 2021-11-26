package com.web0zz.domain.model.rockets

data class FirstStage(
    val burn_time_sec: Int?,
    val engines: Int?,
    val fuel_amount_tons: Int?,
    val reusable: Boolean?,
    val thrust_sea_level: ThrustSeaLevelX?,
    val thrust_vacuum: ThrustVacuumX?
) {
    companion object {
        data class ThrustSeaLevelX(
            val kN: Int?,
            val lbf: Int?
        )

        data class ThrustVacuumX(
            val kN: Int?,
            val lbf: Int?
        )
    }
}