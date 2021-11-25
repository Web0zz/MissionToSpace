package com.web0zz.home

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.core.base.BaseViewModel
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.usecase.GetLaunchesUseCase
import com.web0zz.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : BaseViewModel() {

    private val _launches: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val launches: StateFlow<HomeUiState> = _launches

    fun loadLaunches() = getLaunchesUseCase(UseCase.None(), viewModelScope) {
        viewModelScope.launch {
            it.onStart { setLoading() }
                .collect { result ->
                    result.mapBoth(::handleLaunchesList, ::handleFailure)
                }
        }
    }

    private fun setLoading() {
        _launches.value = HomeUiState.Loading
    }

    private fun handleLaunchesList(launchesData: List<Launches>) {
        _launches.value = HomeUiState.Success(launchesData)
    }

    private fun handleFailure(failure: Failure) {
        _launches.value = HomeUiState.Error(failure)
    }

    sealed class HomeUiState {
        object Loading : HomeUiState()
        data class Success(val data: List<Launches>) : HomeUiState()
        data class Error(val failure: Failure) : HomeUiState()
    }
}