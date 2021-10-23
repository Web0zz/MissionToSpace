package com.web0zz.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Launches Table")
data class LaunchesEntity(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val fairings : FairingsEntity,
    val links : LinksEntity,
    val staticFireDateUtc : String,
    val staticFireDateUnix : Int,
    val net : Boolean,
    val window : Int,
    val rocket : String,
    val success : Boolean,
    val failures : List<FailuresEntity>,
    val details : String,
    val crew : List<String>,
    val ships : List<String>,
    val capsules : List<String>,
    val payloads : List<String>,
    val launchpad : String,
    val flightNumber : Int,
    val name : String,
    val dateUtc : String,
    val dateUnix : Int,
    val dateLocal : String,
    val datePrecision : String,
    val upcoming : Boolean,
    val cores : List<CoresEntity>,
    val autoUpdate : Boolean,
    val tbd : Boolean,
    val launchLibraryId : String
)
