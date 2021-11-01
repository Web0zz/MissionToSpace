package com.web0zz.network.util

import com.web0zz.network.model.*

internal const val notFoundMessage = "Not Found"

internal val expectedLaunchesDto = listOf(
    LaunchesDto(
        fairings = FairingsDto(
            reused = false,
            recoveryAttempt = false,
            recovered = false,
            ships = emptyList()
        ),
        links = LinksDto(
            patch = LinksDto.Companion.PatchDto(
                small = "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png",
                large = "https://images2.imgbox.com/40/e3/GypSkayF_o.png"
            ),
            reddit = LinksDto.Companion.RedditDto(
                campaign = null,
                launch = null,
                media = null,
                recovery = null
            ),
            flickr = LinksDto.Companion.FlickrDto(
                small = emptyList(),
                original = emptyList()
            ),
            presskit = null,
            webcast = "https://www.youtube.com/watch?v=0a_00nJ_Y88",
            youtubeId = "0a_00nJ_Y88",
            article = "https://www.space.com/2196-spacex-inaugural-falcon-1-rocket-lost-launch.html",
            wikipedia = "https://en.wikipedia.org/wiki/DemoSat"
        ),
        staticFireDateUtc = "2006-03-17T00:00:00.000Z",
        staticFireDateUnix = 1142553600,
        net = false,
        window = 0,
        rocket = "5e9d0d95eda69955f709d1eb",
        success = false,
        failures = listOf(
            FailuresDto(
                time = 33,
                altitude = null,
                reason = "merlin engine failure"
            )
        ),
        details = "Engine failure at 33 seconds and loss of vehicle",
        crew = emptyList(),
        ships = emptyList(),
        capsules = emptyList(),
        payloads = listOf(
            "5eb0e4b5b6c3bb0006eeb1e1"
        ),
        launchpad = "5e9e4502f5090995de566f86",
        flightNumber = 1,
        name = "FalconSat",
        dateUtc = "2006-03-24T22:30:00.000Z",
        dateUnix = 1143239400,
        dateLocal = "2006-03-25T10:30:00+12:00",
        datePrecision = "hour",
        upcoming = false,
        cores = listOf(
            CoresDto(
                core = "5e9e289df35918033d3b2623",
                flight = 1,
                gridfins = false,
                legs = false,
                reused = false,
                landingAttempt = false,
                landingSuccess = null,
                landingType = null,
                landpad = null
            )
        ),
        autoUpdate = true,
        tbd = false,
        launchLibraryId = null,
        id = "5eb87cd9ffd86e000604b32a"
    )
)