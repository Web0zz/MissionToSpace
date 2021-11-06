package com.web0zz.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.google.common.truth.Truth.assertThat
import com.web0zz.cache.dao.LaunchesDao
import com.web0zz.cache.model.LaunchesEntity
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.repository.LaunchesRepository
import com.web0zz.network.SpaceXService
import com.web0zz.network.model.LaunchesDto
import com.web0zz.network.util.NetworkHelper
import com.web0zz.network.util.NetworkResponse
import com.web0zz.network.util.NetworkStatus
import com.web0zz.repository.mapper.DataMappersFacade
import com.web0zz.repository.mapper.mapLaunchesDto
import com.web0zz.repository.mapper.mapLaunchesDtoToEntity
import com.web0zz.repository.mapper.mapLaunchesEntity
import com.web0zz.repository.repository.LaunchesRepositoryImp
import com.web0zz.repository.repositoryUtil.expectedLaunches
import com.web0zz.repository.repositoryUtil.expectedLaunchesDto
import com.web0zz.repository.repositoryUtil.expectedLaunchesEntity
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

    private lateinit var dataMappersFacade: DataMappersFacade

    private lateinit var launchesDto: LaunchesDto
    private lateinit var launchesEntity: LaunchesEntity
    private lateinit var launches: Launches
    private lateinit var launchesId: String

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataMappersFacade = DataMappersFacade(
            { launchesDto -> mapLaunchesDto(launchesDto) },
            { launchesEntity -> mapLaunchesEntity(launchesEntity) },
            { launchesDto -> mapLaunchesDtoToEntity(launchesDto) }
        )

        launchesDto = expectedLaunchesDto
        launchesEntity = expectedLaunchesEntity
        launches = expectedLaunches
        launchesId = expectedLaunches.id

        launchesRepository =
            LaunchesRepositoryImp(apiService, launchesDao, dataMappersFacade, networkHelper)
    }

    @After
    fun destroyTest() {
        unmockkAll()
    }

    // LaunchesRepository.getLaunchesData

    @Test
    fun `return successful data from api when api request successful`() {
        // Setup
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunches() } returns NetworkResponse.Success(listOf(launchesDto))
        coEvery { launchesDao.insertLaunches(any()) } answers { }

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            assertThat(data).isInstanceOf(Ok::class.java)
            assertThat(data.component1()).isEqualTo(listOf(launches))
        }
    }

    @Test
    fun `return successful data from cache when api request failed`() {
        // Setup
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunches() } returns NetworkResponse.Error("")
        coEvery { launchesDao.getAllLaunches() } returns listOf(launchesEntity)

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            assertThat(data).isInstanceOf(Ok::class.java)
            assertThat(data.component1()).isEqualTo(listOf(launches))
        }
    }

    @Test
    fun `return error when api request failed and cache data is empty `() {
        val apiFailedMessage = "failed"
        
        // Setup
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunches() } returns NetworkResponse.Error(apiFailedMessage)
        coEvery { launchesDao.getAllLaunches() } returns listOf()

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            assertThat(data).isInstanceOf(Err::class.java)
            assertThat(data.component2()).isEqualTo(Failure.ApiResponseError(apiFailedMessage))
        }
    }

    @Test
    fun `return successful data from cache when cache data successful while network unavailable`() {
        // Setup
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.UnAvailable
        coEvery { launchesDao.getAllLaunches() } returns listOf(launchesEntity)

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            assertThat(data).isInstanceOf(Ok::class.java)
            assertThat(data.component1()).isEqualTo(listOf(launches))
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

            assertThat(data).isInstanceOf(Err::class.java)
            assertThat(data.component2()).isInstanceOf(Failure.NetworkConnectionError::class.java)
        }
    }

    @Test
    fun `return error when exception is caught`() {
        val exceptionMessage = "null"

        // Setup
        every { networkHelper.getConnectionStatus() } throws Exception(exceptionMessage)

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesData().single()

            assertThat(data).isInstanceOf(Err::class.java)
            assertThat(data.component2()).isEqualTo(Failure.UnknownError(exceptionMessage))
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

            assertThat(data).isInstanceOf(Ok::class.java)
            assertThat(data.component1()).isEqualTo(launches)
        }
    }

    @Test
    fun `return successful data byId from api when network available and api return successful response`() {
        val captureInsertedData = slot<List<LaunchesEntity>>()

        // Setup
        coEvery { launchesDao.getLaunchesById(launchesId) } returns listOf()
        coEvery { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunchesById(launchesId) } returns NetworkResponse.Success(launchesDto)
        coEvery { launchesDao.insertLaunches(capture(captureInsertedData)) } answers { }
        
        runBlocking { 
            val data = launchesRepository.getLaunchesById(launchesId).single()
            
            assertThat(data).isInstanceOf(Ok::class.java)
            assertThat(data.component1()).isEqualTo(launches)
        }
    }
    
    @Test
    fun `return error byId when cache is empty and api request failed`() {
        val apiFailedMessage = "failed"
        
        // Setup
        coEvery { launchesDao.getLaunchesById(launchesId) } returns listOf()
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.Available
        coEvery { apiService.getLaunchesById(launchesId) } returns NetworkResponse.Error(apiFailedMessage)
    
        runBlocking { 
            val data = launchesRepository.getLaunchesById(launchesId).single()
            
            assertThat(data).isInstanceOf(Err::class.java)
            assertThat(data.component2()).isEqualTo(Failure.ApiResponseError(apiFailedMessage))
        }
    }
    
    @Test
    fun `return error byId when cache is empty and network unavailable`() {
         val networkMessage = "Unavailable connection" //TODO move to const value
        
        // Setup
        coEvery { launchesDao.getLaunchesById(launchesId) } returns listOf()
        every { networkHelper.getConnectionStatus() } returns NetworkStatus.UnAvailable
        
        runBlocking { 
            val data = launchesRepository.getLaunchesById(launchesId).single()
            
            assertThat(data).isInstanceOf(Err::class.java)
            assertThat(data.component2()).isEqualTo(Failure.NetworkConnectionError(networkMessage))
        }
    }
    
    @Test
    fun `return error byId when exception is caught`() {
        val exceptionMessage = "null"

        // Setup
        coEvery { launchesDao.getLaunchesById(launchesId) } throws Exception(exceptionMessage)

        // Invoke repository function
        runBlocking {
            val data = launchesRepository.getLaunchesById(launchesId).single()

            assertThat(data).isInstanceOf(Err::class.java)
            assertThat(data.component2()).isEqualTo(Failure.UnknownError(exceptionMessage))
        }
    }
    ////////////////////////////////////////////////////////////////////
}