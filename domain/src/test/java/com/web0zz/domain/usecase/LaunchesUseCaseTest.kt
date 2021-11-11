package com.web0zz.domain.usecase

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.repository.LaunchesRepository
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class LaunchesUseCaseTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var launchesRepository: LaunchesRepository

    @MockK
    lateinit var expectedLaunches: Launches

    private val getLaunchesUseCase: GetLaunchesUseCase by lazy {
        GetLaunchesUseCase(launchesRepository, testDispatcher)
    }
    private val getLaunchesByIdUseCase: GetLaunchesByIdUseCase by lazy {
        GetLaunchesByIdUseCase(launchesRepository, testDispatcher)
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `should invoke getLaunchesUseCase and return response successfully`() {
        val expectedResult = Ok(listOf<Launches>())
        val answerRun = flow<Result<List<Launches>, Failure>> { emit(expectedResult) }
        coEvery { launchesRepository.getLaunchesData() } returns answerRun

        runBlockingTest {
            val data = getLaunchesUseCase.run(UseCase.None()).single()

            data shouldBe expectedResult
        }
    }

    @Test
    fun `should invoke getLaunchesById and return response successfully`() {
        val expectedResult = Ok(expectedLaunches)
        val paramId = "123"
        val answerRun = flow<Result<Launches, Failure>> { emit(expectedResult) }
        coEvery { launchesRepository.getLaunchesById(paramId) } returns answerRun

        runBlockingTest {
            val data = getLaunchesByIdUseCase.run(paramId).single()

            data shouldBe expectedResult
        }
    }
}