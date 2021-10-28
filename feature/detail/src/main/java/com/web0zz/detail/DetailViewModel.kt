package com.web0zz.detail

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.web0zz.core.base.BaseViewModel
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.domain.usecase.GetLaunchesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

    fun getLaunch(id: String) = getLaunchesByIdUseCase(id, viewModelScope, ::handleLaunch)

    // TODO can't get failure data with this algorithm find a way to pass
    private fun handleLaunch(launcheData: Flow<Result<List<Launches>, Failure>>) =
        viewModelScope.launch {
            launcheData.collect { result ->
                when(result) {
                    is Ok -> DetailUiState.Success(result.value.first())
                    is Err -> DetailUiState.Error("Error")
                }
            }
        }

    sealed class DetailUiState {
        object Loading : DetailUiState()
        data class Success(val data: Launches) : DetailUiState()
        data class Error(val error: String) : DetailUiState()
    }
}