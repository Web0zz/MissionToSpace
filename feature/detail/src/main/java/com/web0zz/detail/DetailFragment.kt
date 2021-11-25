package com.web0zz.detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.web0zz.core.base.BaseFragment
import com.web0zz.detail.databinding.FragmentDetailBinding
import com.web0zz.domain.exception.Failure
import com.web0zz.domain.model.Launches
import com.web0zz.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>(
    FragmentDetailBinding::inflate
) {
    @Inject
    lateinit var navigator: Navigator

    override val mViewModel: DetailViewModel by viewModels()

    override fun onCreateInvoke() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.launche.collect(::handleViewState)
            }
        }
    }

    private fun handleViewState(viewState: DetailViewModel.DetailUiState) {
        when (viewState) {
            is DetailViewModel.DetailUiState.Loading -> handleLoading()
            is DetailViewModel.DetailUiState.Success -> handleLaunche(viewState.data)
            is DetailViewModel.DetailUiState.Error -> handleFailure(viewState.failure)
        }
    }

    private fun loadLaunchesList(id: String) {
        mViewModel.getLaunch(id)
    }

    // Handle DetailUiState

    private fun handleLoading() {
        // TODO set loading state
    }

    private fun handleLaunche(launches: Launches) {
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