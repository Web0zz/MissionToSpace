package com.web0zz.countdown

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.web0zz.countdown.util.toDateInt
import com.web0zz.domain.exception.Failure
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CountdownViewModel @AssistedInject constructor(
    @Assisted private val deployDate: String
) : ViewModel() {

    private val _countdownState = MutableSharedFlow<CountdownUiState>(replay = 0)
    val countdownState: SharedFlow<CountdownUiState> = _countdownState

    init {
        // Temp Implementation of countdown
        setLoading()
        initCountdown(deployDate.toDateInt())
    }

    // TODO This is Main logic of countdown. It can be outside of this class.
    private fun initCountdown(deployDate: Int) {}

    private fun setLoading() = viewModelScope.launch {
        _countdownState.emit(CountdownUiState.Loading)
    }

    private fun handleSuccess(countdown: Int) = viewModelScope.launch {
        _countdownState.emit(CountdownUiState.Success(countdown))
    }

    private fun handleError(failure: Failure) = viewModelScope.launch {
        _countdownState.emit(CountdownUiState.Error(failure))
    }

    sealed class CountdownUiState {
        object Loading : CountdownUiState()
        data class Success(val countdown: Int) : CountdownUiState()
        data class Error(val failure: Failure) : CountdownUiState()
    }

    @AssistedFactory
    interface CountdownViewModelFactory {
        fun create(deployDate: String): CountdownViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: CountdownViewModelFactory,
            deployDate: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(deployDate) as T
            }
        }
    }
}