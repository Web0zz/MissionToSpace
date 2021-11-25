package com.web0zz.countdown

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.web0zz.core.base.BaseFragment
import com.web0zz.countdown.databinding.FragmentCountdownBinding
import com.web0zz.domain.exception.Failure
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountdownFragment : BaseFragment<FragmentCountdownBinding, CountdownViewModel>(
    FragmentCountdownBinding::inflate
) {
    override val mViewModel: CountdownViewModel by viewModels()

    override fun onCreateInvoke() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.countdownState.collect(::handleViewState)
            }
        }
    }

    private fun handleViewState(viewState: CountdownViewModel.CountdownUiState) {
        when (viewState) {
            is CountdownViewModel.CountdownUiState.Loading -> handleLoading()
            is CountdownViewModel.CountdownUiState.Success -> handleCountdown(viewState.countdown)
            is CountdownViewModel.CountdownUiState.Error -> handleFailure(viewState.failure)
        }
    }

    // Handle CountdownUiState

    private fun handleLoading() {
        // TODO set loading state
    }

    private fun handleCountdown(leftTime: Long) {
        // TODO set launches data to xml
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.UnknownError -> renderFailure()
            else -> renderFailure()
        }
    }

    // Render Data

    private fun renderFailure(/*@StringRes message: Int*/) {
        // TODO show error to user
    }
}