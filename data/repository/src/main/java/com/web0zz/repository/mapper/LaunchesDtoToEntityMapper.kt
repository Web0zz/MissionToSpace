package com.web0zz.repository.mapper

import com.web0zz.cache.model.*
import com.web0zz.network.model.CoresDto
import com.web0zz.network.model.FailuresDto
import com.web0zz.network.model.LaunchesDto

fun mapLaunchesDtoToEntity(input: LaunchesDto): LaunchesEntity {
    return LaunchesEntity(
        input.id,
        FairingsEntity(
            input.fairings.reused,
            input.fairings.recoveryAttempt,
            input.fairings.recovered,
            input.fairings.ships
        ),
        LinksEntity(
            LinksEntity.Companion.PatchEntity(
                input.links.patch.small,
                input.links.patch.large
            ),
            LinksEntity.Companion.RedditEntity(
                input.links.reddit.campaign,
                input.links.reddit.launch,
                input.links.reddit.media,
                input.links.reddit.recovery
            ),
            LinksEntity.Companion.FlickrEntity(
                input.links.flickr.small,
                input.links.flickr.original
            ),
            input.links.presskit,
            input.links.webcast,
            input.links.youtubeId,
            input.links.article,
            input.links.wikipedia,
        ),
        input.staticFireDateUtc,
        input.staticFireDateUnix,
        input.net,
        input.window,
        input.rocket,
        input.success,
        input.failures.map { mapFailuresDto(it) },
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
        input.cores.map { mapCoresDto(it) },
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