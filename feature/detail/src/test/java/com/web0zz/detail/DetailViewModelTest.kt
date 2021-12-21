package com.web0zz.detail

import com.web0zz.domain.usecase.GetLaunchesByIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class DetailViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var getLaunchesByIdUseCase: GetLaunchesByIdUseCase

    val detailViewModel: DetailViewModel by lazy {
        DetailViewModel(getLaunchesByIdUseCase)
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
}