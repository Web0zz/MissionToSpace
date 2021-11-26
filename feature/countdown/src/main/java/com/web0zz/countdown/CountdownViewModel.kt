package com.web0zz.countdown

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.web0zz.core.base.BaseViewModel
import com.web0zz.countdown.util.toDateLong
import com.web0zz.domain.exception.Failure
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.*

class CountdownViewModel @AssistedInject constructor(
    @Assisted private val deployDate: String
) : BaseViewModel() {

    private val _countdownState = MutableSharedFlow<CountdownUiState>(replay = 0)
    val countdownState: SharedFlow<CountdownUiState> = _countdownState

    init {
        // Temp Implementation of countdown
        setLoading()
        initCountdown(deployDate.toDateLong())
    }

    // TODO countdown must be tested
    private fun initCountdown(deployDate: Long) {
        try {
            val timeLeft = deployDate - Calendar.getInstance().timeInMillis

            object : CountDownTimer(timeLeft, 1000) {
                override fun onTick(p0: Long) {
                    handleSuccess(p0)
                }

                override fun onFinish() {
                    handleSuccess(0)
                }
            }
        } catch (e: Exception) {
            handleError(Failure.UnknownError(e.message ?: "Don't know what happened"))
        }
    }

    private fun setLoading() = viewModelScope.launch {
        _countdownState.emit(CountdownUiState.Loading)
    }

    private fun handleSuccess(countdown: Long) = viewModelScope.launch {
        _countdownState.emit(CountdownUiState.Success(countdown))
    }

    private fun handleError(failure: Failure) = viewModelScope.launch {
        _countdownState.emit(CountdownUiState.Error(failure))
    }

    sealed class CountdownUiState {
        object Loading : CountdownUiState()
        data class Success(val countdown: Long) : CountdownUiState()
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