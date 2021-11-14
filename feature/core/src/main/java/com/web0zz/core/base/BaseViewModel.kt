package com.web0zz.core.base

import androidx.lifecycle.ViewModel
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {}