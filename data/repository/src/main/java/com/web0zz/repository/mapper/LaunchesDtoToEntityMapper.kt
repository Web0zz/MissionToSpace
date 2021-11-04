package com.web0zz.repository.mapper

import com.web0zz.cache.model.*
import com.web0zz.network.model.CoresDto
import com.web0zz.network.model.FailuresDto
import com.web0zz.network.model.LaunchesDto

fun mapLaunchesDtoToEntity(input: LaunchesDto): LaunchesEntity {
    return LaunchesEntity(
        input.id,
        input.fairings?.let {
            FairingsEntity(
                it.reused,
                it.recoveryAttempt,
                it.recovered,
                it.ships
            )
        },
        input.links?.let { links ->
            LinksEntity(
                links.patch?.let {
                    LinksEntity.Companion.PatchEntity(
                        it.small,
                        it.large
                    )
                },
                links.reddit?.let {
                    LinksEntity.Companion.RedditEntity(
                        it.campaign,
                        it.launch,
                        it.media,
                        it.recovery
                    )
                },
                links.flickr?.let {
                    LinksEntity.Companion.FlickrEntity(
                        it.small,
                        it.original
                    )
                },
                links.presskit,
                links.webcast,
                links.youtubeId,
                links.article,
                links.wikipedia,
            )
        },
        input.staticFireDateUtc,
        input.staticFireDateUnix,
        input.net,
        input.window,
        input.rocket,
        input.success,
        input.failures?.let { fail -> fail.map { mapFailuresDto(it) } },
        input.details,
        input.crew,
        input.ships,
        input.capsules,
        input.payloads,
        input.launchpad,
        input.flightNumber,
        input.name,
        input.dateUtc,
        input.dateUnix,
        input.dateLocal,
        input.datePrecision,
        input.upcoming,
        input.cores?.let { core -> core.map { mapCoresDto(it) } },
        input.autoUpdate,
        input.tbd,
        input.launchLibraryId
    )
}

private fun mapFailuresDto(input: FailuresDto): FailuresEntity {
    return FailuresEntity(
        input.time,
        input.altitude,
        input.reason
    )
}

private fun mapCoresDto(input: CoresDto): CoresEntity {
    return CoresEntity(
        input.core,
        input.flight,
        input.gridfins,
        input.legs,
        input.reused,
        input.landingAttempt,
        input.landingSuccess,
        input.landingType,
        input.landpad
    )
}