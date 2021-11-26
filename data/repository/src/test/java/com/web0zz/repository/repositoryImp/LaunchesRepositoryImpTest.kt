package com.web0zz.repository.repositoryImp

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.web0zz.cache.dao.LaunchesDao
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.launches.Launches
import com.web0zz.domain.repository.LaunchesRepository
import com.web0zz.network.SpaceXService
import com.web0zz.network.model.LaunchesDto
import com.web0zz.network.util.NetworkHelper
import com.web0zz.network.util.NetworkResponse
import com.web0zz.network.util.NetworkStatus
import com.web0zz.repository.mapper.DataMappersFacade
import com.web0zz.repository.repository.LaunchesRepositoryImp
import com.web0zz.repository.testUtil.expectedLaunches
import com.web0zz.repository.testUtil.expectedLaunchesDto
import com.web0zz.repository.testUtil.expectedLaunchesEntity
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class LaunchesRepositoryImpTest {
    private lateinit var launchesRepository: LaunchesRepository

    @MockK
    private lateinit var apiService: SpaceXService

    @MockK
    private lateinit var launchesDao: LaunchesDao

    @MockK
    private lateinit var networkHelper: NetworkHelper

    @MockK
    private lateinit var dataMappersFacade: DataMappersFacade

    private lateinit var launchesDto: LaunchesDto
    private lateinit var launchesEntity: LaunchesEntity
    private lateinit var launches: Launches

    private lateinit var launchesId: String

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // Expected Dummy Models
        launchesDto = expectedLaunchesDto
        launchesEntity = expectedLaunchesEntity
        launches = expectedLaunches
        launchesId = expectedLaunches.id

        // Mapper Setup
        every { dataMappersFacade.launchesDtoMapper(any()) } returns launches
        every { dataMappersFacade.launchesEntityMapper(any()) } returns launches
        every { dataMappersFacade.launchesDtoToEntityMapper(any()) } returns launchesEntity

        launchesRepository =
            LaunchesRepositoryImp(apiService, launchesDao, dataMappersFacade, networkHelper)
    }

    @After
    fun destroyTest() {
        unmockkAll()
    }

    // LaunchesRepository.getLaunchesData

    @Test
    fun `return successful data from API when the API request successful`() {
        // Setup
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunches() } returns NetworkResponse.Success(listOf(launchesDto))
        coEvery { launchesDao.insertLaunches(any()) } answers { }

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            data shouldBe Ok(listOf(launches))
        }
    }

    @Test
    fun `return successful data from the cache when the API request failed`() {
        // Setup
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunches() } returns NetworkResponse.Error("")
        coEvery { launchesDao.getAllLaunches() } returns listOf(launchesEntity)

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            data shouldBe Ok(listOf(launches))
        }
    }

    @Test
    fun `return error when API request failed and cache data is empty `() {
        val apiFailedMessage = "failed"

        // Setup
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunches() } returns NetworkResponse.Error(apiFailedMessage)
        coEvery { launchesDao.getAllLaunches() } returns listOf()

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            data shouldBe Err(Failure.ApiResponseError(apiFailedMessage))
        }
    }

    @Test
    fun `return successful data from the cache when cache data is successful while the network is unavailable`() {
        // Setup
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.UnAvailable
        coEvery { launchesDao.getAllLaunches() } returns listOf(launchesEntity)

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            data shouldBe Ok(listOf(launches))
        }
    }

    @Test
    fun `return error when network unavailable and cache data is empty`() {
        // Setup
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.UnAvailable
        coEvery { launchesDao.getAllLaunches() } returns listOf()

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            data shouldBe Err(Failure.NetworkConnectionError("No network"))
        }
    }

    @Test
    fun `return error when an exception is caught`() {
        val exceptionMessage = "null"

        // Setup
        every { networkHelper.getConnectionStatus() } throws Exception(exceptionMessage)

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            data shouldBe Err(Failure.UnknownError(exceptionMessage))
        }
    }
    ////////////////////////////////////////////////////////////////////

    // LaunchesRepository.getLaunchesById

    @Test
    fun `return successful data byId from cache data successful`() {
        // Setup
        coEvery { launchesDao.getLaunchesById(launchesId) } returns listOf(launchesEntity)

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesById(launchesId).single()

            data shouldBe Ok(launches)
        }
    }

    @Test
    fun `return successful data byId from API when network available and API return a successful response`() {
        val captureInsertedData = slot<List<LaunchesEntity>>()

        // Setup
        coEvery { launchesDao.getLaunchesById(launchesId) } returns listOf()
        coEvery { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunchesById(launchesId) } returns NetworkResponse.Success(
            launchesDto
        )
        coEvery { launchesDao.insertLaunches(capture(captureInsertedData)) } answers { }

        runBlocking {
            val data = launchesRepository.getLaunchesById(launchesId).single()

            data shouldBe Ok(launches)
        }
    }

    @Test
    fun `return error byId when the cache is empty and API request failed`() {
        val apiFailedMessage = "failed"

        // Setup
        coEvery { launchesDao.getLaunchesById(launchesId) } returns listOf()
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunchesById(launchesId) } returns NetworkResponse.Error(
            apiFailedMessage
        )

        runBlocking {
            val data = launchesRepository.getLaunchesById(launchesId).single()

            data shouldBe Err(Failure.ApiResponseError(apiFailedMessage))

        }
    }

    @Test
    fun `return error byId when the cache is empty and network unavailable`() {
        val networkMessage = "Unavailable connection" //TODO move to const value

        // Setup
        coEvery { launchesDao.getLaunchesById(launchesId) } returns listOf()
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.UnAvailable

        runBlocking {
            val data = launchesRepository.getLaunchesById(launchesId).single()

            data shouldBe Err(Failure.NetworkConnectionError(networkMessage))
        }
    }

    @Test
    fun `return error byId when an exception is caught`() {
        val exceptionMessage = "null"

        // Setup
        coEvery { launchesDao.getLaunchesById(launchesId) } throws Exception(exceptionMessage)

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesById(launchesId).single()

            data shouldBe Err(Failure.UnknownError(exceptionMessage))
        }
    }
    ////////////////////////////////////////////////////////////////////
}