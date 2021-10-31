package com.web0zz.detail

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.core.base.BaseViewModel
import com.web0zz.domain.model.Launches
import com.web0zz.domain.usecase.GetLaunchesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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
            it.collect { result ->
                result.mapBoth(::handleLaunch, ::handleFailure)
            }
        }
    }

    private fun handleLaunch(launcheData: List<Launches>) {
        _launch.value = DetailUiState.Success(launcheData.first())
    }

    sealed class DetailUiState {
        object Loading : DetailUiState()
        data class Success(val data: Launches) : DetailUiState()
        data class Error(val error: String) : DetailUiState()
    }
}