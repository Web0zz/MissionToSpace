package com.web0zz.detail

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.core.base.BaseViewModel
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.launches.Launches
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

    private val _launchDetailUi: MutableStateFlow<DetailUiState> = MutableStateFlow(DetailUiState.Loading)
    val launcheDetailUi: StateFlow<DetailUiState> = _launchDetailUi

    fun getLaunch(id: String) = getLaunchesByIdUseCase(id, viewModelScope) {
        viewModelScope.launch {
            it.onStart { setLoading() }
                .collect { result ->
                    result.mapBoth(::handleLaunch, ::handleFailure)
                }
        }
    }

    private fun setLoading() {
        _launchDetailUi.value = DetailUiState.Loading
    }

    private fun handleLaunch(launcheData: Launches) {
        _launchDetailUi.value = DetailUiState.Success(launcheData)
    }

    private fun handleFailure(failure: Failure) {
        _launchDetailUi.value = DetailUiState.Error(failure)
    }

    sealed class DetailUiState {
        object Loading : DetailUiState()
        data class Success(val data: Launches) : DetailUiState()
        data class Error(val failure: Failure) : DetailUiState()
    }
}