package com.web0zz.home

import com.web0zz.domain.usecase.GetLaunchesUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel
    @MockK
    private lateinit var getLaunchesUseCase: GetLaunchesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        homeViewModel = HomeViewModel(getLaunchesUseCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    // TODO Find a way to test ViewModel
    /*@Test
    fun `should return successful HomeUiState`() {
        val useCaseResult = flow<Result<List<Launches>, Failure>> { emit(Ok(listOf())) }
        coEvery { getLaunchesUseCase.run(any()) } returns useCaseResult

        runBlocking {
            homeViewModel.loadLaunches()

            val data = homeViewModel.launches.drop(1).first().let {
                it shouldBe HomeViewModel.HomeUiState.Success(listOf())
            }
        }
    }*/
}