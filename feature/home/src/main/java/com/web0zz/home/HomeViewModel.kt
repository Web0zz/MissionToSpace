package com.web0zz.home

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.core.base.BaseViewModel
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.launches.Launches
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

    private val _launchesHomeUi: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val launchesHomeUi: StateFlow<HomeUiState> = _launchesHomeUi

    private val _searchLaunches: MutableStateFlow<List<Launches>> = MutableStateFlow(listOf())
    val searchLaunches: StateFlow<List<Launches>> = _searchLaunches

    fun loadLaunches() = getLaunchesUseCase(UseCase.None(), viewModelScope) {
        viewModelScope.launch {
            it.onStart { setLoading() }
                .collect { result ->
                    result.mapBoth(::handleLaunchesList, ::handleFailure)
                }
        }
    }

    fun sortLaunches(sortByType: SortByType) {
        val launcheState = _launchesHomeUi.value

        if(launcheState is HomeUiState.Success) {
            when(sortByType) {
                SortByType.LAUNCH_DATE -> { handleLaunchesList(launcheState.data.sortedBy { it.dateUnix }) }
                SortByType.LAUNCH_NAME -> { handleLaunchesList(launcheState.data.sortedBy { it.name }) }
            }
        }
    }

    fun searchLaunches(searchText: String) {
        val launcheState = _launchesHomeUi.value

        if(launcheState is HomeUiState.Success) {
            setSearchList(
                launcheState.data.filter {
                    it.name?.contains(searchText) ?: false
                }
            )
        } else setSearchList(listOf())
    }

    private fun setLoading() {
        _launchesHomeUi.value = HomeUiState.Loading
    }

    private fun handleLaunchesList(launchesData: List<Launches>) {
        _launchesHomeUi.value = HomeUiState.Success(launchesData)
    }

    private fun setSearchList(launchesData: List<Launches>) {
        _searchLaunches.value = launchesData
    }

    private fun handleFailure(failure: Failure) {
        _launchesHomeUi.value = HomeUiState.Error(failure)
    }

    sealed class HomeUiState {
        object Loading : HomeUiState()
        data class Success(val data: List<Launches>) : HomeUiState()
        data class Error(val failure: Failure) : HomeUiState()
    }

    enum class SortByType {
        LAUNCH_DATE,
        LAUNCH_NAME
    }
}