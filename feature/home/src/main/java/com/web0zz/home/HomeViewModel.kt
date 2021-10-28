package com.web0zz.home

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.web0zz.core.base.BaseViewModel
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.usecase.GetLaunchesUseCase
import com.web0zz.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : BaseViewModel() {

    private val _launches: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val launches: StateFlow<HomeUiState> = _launches

    fun loadLaunches() = getLaunchesUseCase(UseCase.None(), viewModelScope, ::handleLaunchesList)

    // TODO can't get failure data with this algorithm find a way to pass
    private fun handleLaunchesList(launchesData: Flow<Result<List<Launches>, Failure>>) =
        viewModelScope.launch {
            launchesData.collect { result ->
                when(result) {
                    is Ok -> HomeUiState.Success(result.value)
                    is Err -> HomeUiState.Error("Error")
                }
            }
        }

    sealed class HomeUiState {
        object Loading : HomeUiState()
        data class Success(val data: List<Launches>) : HomeUiState()
        data class Error(val error: String) : HomeUiState()
    }
}