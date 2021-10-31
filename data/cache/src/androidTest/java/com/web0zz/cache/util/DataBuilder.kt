package com.web0zz.cache.util

import com.web0zz.cache.model.FairingsEntity
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.cache.model.LinksEntity

internal object DataBuilder {

    fun createLaunches(rep: Int): List<LaunchesEntity> {
        val tmpList: MutableList<LaunchesEntity> = mutableListOf()
        for (x in 0..rep) {
            tmpList.add(
                LaunchesEntity(
                    "$x",
                    FairingsEntity(
                        reused = false, recoveryAttempt = false, recovered = false, emptyList()
                    ),
                    LinksEntity(
                        LinksEntity.Companion.PatchEntity("", ""),
                        LinksEntity.Companion.RedditEntity("", "", "", ""),
                        LinksEntity.Companion.FlickrEntity(emptyList(), emptyList()),
                        "",
                        "",
                        "",
                        "",
                        ""
                    ),
                    "",
                    1,
                    false,
                    1,
                    "",
                    false,
                    emptyList(),
                    "",
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    emptyList(),
                    "",
                    1,
                    "",
                    "",
                    1,
                    "",
                    "",
                    false,
                    emptyList(),
                    autoUpdate = false,
                    tbd = false,
                    ""
                )
            )
        }
        return tmpList
    }
}