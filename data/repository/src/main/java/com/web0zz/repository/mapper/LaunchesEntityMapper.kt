package com.web0zz.repository.mapper

import com.web0zz.cache.model.CoresEntity
import com.web0zz.cache.model.FailuresEntity
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.domain.model.*

fun mapLaunchesEntity(input: LaunchesEntity): Launches {
    return Launches(
        input.id,
        input.fairings?.let {
            Fairings(
                it.reused,
                it.recoveryAttempt,
                it.recovered,
                it.ships
            )
        },
        input.links?.let {
            Links(
                it.patch?.let { patch ->
                    Links.Companion.Patch(
                        patch.small,
                        patch.large
                    )
                },
                it.reddit?.let { reddit ->
                    Links.Companion.Reddit(
                        reddit.campaign,
                        reddit.launch,
                        reddit.media,
                        reddit.recovery
                    )
                },
                it.flickr?.let { flickr ->
                    Links.Companion.Flickr(
                        flickr.small,
                        flickr.original
                    )
                },
                it.presskit,
                it.webcast,
                it.youtubeId,
                it.article,
                it.wikipedia,
            )
        },
        input.staticFireDateUtc,
        input.staticFireDateUnix,
        input.net,
        input.window,
        input.rocket,
        input.success,
        input.failures?.let { fail -> fail.map { mapFailuresEntity(it) } },
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
        input.cores?.let { core -> core.map { mapCoresEntity(it) } },
        input.autoUpdate,
        input.tbd,
        input.launchLibraryId
    )
}

private fun mapFailuresEntity(input: FailuresEntity): Failures {
    return Failures(
        input.time,
        input.altitude,
        input.reason
    )
}

private fun mapCoresEntity(input: CoresEntity): Cores {
    return Cores(
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