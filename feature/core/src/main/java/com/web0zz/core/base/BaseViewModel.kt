package com.web0zz.core.base

import androidx.lifecycle.ViewModel
import com.web0zz.domain.exception.Failure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {

    private val _failure: MutableStateFlow<Failure?> = MutableStateFlow(null)
    val failure: StateFlow<Failure?> = _failure

    protected fun handleFailure(failure: Failure) {
        _failure.value = failure
    }
}