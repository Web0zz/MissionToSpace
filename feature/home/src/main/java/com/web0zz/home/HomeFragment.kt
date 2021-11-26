package com.web0zz.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.web0zz.core.base.BaseFragment
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.launches.Launches
import com.web0zz.home.databinding.FragmentHomeBinding
import com.web0zz.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {
    @Inject
    lateinit var navigator: Navigator

    override val mViewModel: HomeViewModel by viewModels()

    override fun onCreateInvoke() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.launchesHomeUi.collect(::handleViewState)
            }
        }
    }

    private fun handleViewState(viewState: HomeViewModel.HomeUiState) {
        when (viewState) {
            is HomeViewModel.HomeUiState.Loading -> handleLoading()
            is HomeViewModel.HomeUiState.Success -> handleLaunchesList(viewState.data)
            is HomeViewModel.HomeUiState.Error -> handleFailure(viewState.failure)
        }
    }

    private fun loadLaunchesList() {
        mViewModel.loadLaunches()
    }

    // Handle HomeUiState

    private fun handleLoading() {
        // TODO set loading state
    }

    private fun handleLaunchesList(launches: List<Launches>) {
        // TODO set launches data to xml
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.NetworkConnectionError -> renderFailure()
            is Failure.ApiResponseError -> renderFailure()
            is Failure.UnknownError -> renderFailure()
        }
    }

    // Render Data

    private fun renderFailure(/*@StringRes message: Int*/) {
        // TODO show error to user
    }
}