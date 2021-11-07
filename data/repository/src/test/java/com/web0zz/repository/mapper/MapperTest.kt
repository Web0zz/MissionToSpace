package com.web0zz.repository.mapper

import com.google.common.truth.Truth.assertThat
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.domain.model.Launches
import com.web0zz.network.model.LaunchesDto
import com.web0zz.repository.testUtil.expectedLaunches
import com.web0zz.repository.testUtil.expectedLaunchesDto
import com.web0zz.repository.testUtil.expectedLaunchesEntity
import org.junit.Before
import org.junit.Test

class MapperTest {
    private lateinit var launchesDto: LaunchesDto
    private lateinit var launchesEntity: LaunchesEntity
    private lateinit var launches: Launches

    @Before
    fun setup() {
        launchesDto = expectedLaunchesDto
        launchesEntity = expectedLaunchesEntity
        launches = expectedLaunches
    }

    @Test
    fun `return Launches when successful mapping the LaunchesDto`() {
        val returned = mapLaunchesDto(launchesDto)

        assertThat(returned).isEqualTo(launches)
    }

    @Test
    fun `return Launches when successful mapping the LaunchesEntity`() {
        val returned = mapLaunchesEntity(launchesEntity)

        assertThat(returned).isEqualTo(launches)
    }

    @Test
    fun `return LaunchesEntity when successful mapping the LaunchesDto`() {
        val returned = mapLaunchesDtoToEntity(launchesDto)

        assertThat(returned).isEqualTo(launchesEntity)
    }
}