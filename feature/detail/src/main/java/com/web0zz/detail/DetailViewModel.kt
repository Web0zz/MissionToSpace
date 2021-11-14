package com.web0zz.detail

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.core.base.BaseViewModel
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.usecase.GetLaunchesByIdUseCase
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
class DetailViewModel @Inject constructor(
    private val getLaunchesByIdUseCase: GetLaunchesByIdUseCase
) : BaseViewModel() {

    private val _launch: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val launche: StateFlow<DetailUiState> = _launch

    fun getLaunch(id: String) = getLaunchesByIdUseCase(id, viewModelScope) {
        viewModelScope.launch {
            it.onStart { setLoading() }
            .collect { result ->
                result.mapBoth(::handleLaunch, ::handleFailure)
            }
        }
    }

    private fun setLoading() {
        _launch.value = DetailUiState.Loading
    }

    private fun handleLaunch(launcheData: Launches) {
        _launch.value = DetailUiState.Success(launcheData)
    }

    private fun handleFailure(failure: Failure) {
        _launch.value = DetailUiState.Error(failure)
    }

    sealed class DetailUiState {
        object Loading : DetailUiState()
        data class Success(val data: Launches) : DetailUiState()
        data class Error(val failure: Failure) : DetailUiState()
    }
}