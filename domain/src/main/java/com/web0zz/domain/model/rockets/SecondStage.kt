package com.web0zz.domain.model.rockets

data class SecondStage(
    val burnTimeSec: Int?,
    val engines: Int?,
    val fuelAmountTons: Int?,
    val rocketPayloads: RocketPayloads?,
    val reusable: Boolean?,
    val thrust: Thrust?
) {
    companion object {
        data class RocketPayloads(
            val compositeFairing: CompositeFairing?,
            val option1: String?
        )

        data class Thrust(
            val kN: Int?,
            val lbf: Int?
        )
    }
}