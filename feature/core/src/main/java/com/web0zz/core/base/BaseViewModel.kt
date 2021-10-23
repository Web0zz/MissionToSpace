package com.web0zz.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    // TODO int -> failure model

    private val _failure: MutableLiveData<Int> = MutableLiveData()
    val failure: LiveData<Int> = _failure

    protected fun handleFailure(failure: Int) {
        _failure.value = failure
    }
}