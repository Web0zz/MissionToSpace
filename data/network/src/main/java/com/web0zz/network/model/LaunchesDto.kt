package com.web0zz.network.model

import com.google.gson.annotations.SerializedName

data class LaunchesDto(
    @SerializedName("fairings") val fairings: FairingsDto,
    @SerializedName("links") val links: LinksDto,
    @SerializedName("static_fire_date_utc") val staticFireDateUtc: String,
    @SerializedName("static_fire_date_unix") val staticFireDateUnix: Int,
    @SerializedName("net") val net: Boolean,
    @SerializedName("window") val window: Int,
    @SerializedName("rocket") val rocket: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("failures") val failures: List<FailuresDto>,
    @SerializedName("details") val details: String,
    @SerializedName("crew") val crew: List<String>,
    @SerializedName("ships") val ships: List<String>,
    @SerializedName("capsules") val capsules: List<String>,
    @SerializedName("payloads") val payloads: List<String>,
    @SerializedName("launchpad") val launchpad: String,
    @SerializedName("flight_number") val flightNumber: Int,
    @SerializedName("name") val name: String,
    @SerializedName("date_utc") val dateUtc: String,
    @SerializedName("date_unix") val dateUnix: Int,
    @SerializedName("date_local") val dateLocal: String,
    @SerializedName("date_precision") val datePrecision: String,
    @SerializedName("upcoming") val upcoming: Boolean,
    @SerializedName("cores") val cores: List<CoresDto>,
    @SerializedName("auto_update") val autoUpdate: Boolean,
    @SerializedName("tbd") val tbd: Boolean,
    @SerializedName("launch_library_id") val launchLibraryId: String,
    @SerializedName("id") val id: String
)