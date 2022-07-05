package com.example.loans.ui.fragments.splash

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.loans.R
import com.example.loans.databinding.SplashFragmentLayoutBinding
import com.example.loans.presentation.fragments.splash.SplashState
import com.example.loans.presentation.fragments.splash.SplashViewModel
import com.example.loans.ui.activity.MainActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SplashFragment : Fragment(R.layout.splash_fragment_layout) {

    private var _splashBinding: SplashFragmentLayoutBinding? = null
    private val splashBinding get() = _splashBinding!!

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: SplashViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _splashBinding = SplashFragmentLayoutBinding.bind(view)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        renderAnimations()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::checkUserState)
    }

    private fun checkUserState(splashState: SplashState) {
        when (splashState) {
            is SplashState.UserAuthorized -> {
                showHomeScreen()
            }
            is SplashState.UserNotAuthorized -> {
                showLoginScreen()
            }
        }
    }

    private fun showLoginScreen() {
        findNavController().navigate(R.id.login_graph, null, navOptions {
            popUpTo(R.id.nav_graph) {
                inclusive = true
            }
        })
    }

    private fun showHomeScreen() {
        findNavController().navigate(R.id.home_graph, null, navOptions {
            popUpTo(R.id.nav_graph) {
                inclusive = true
            }
        })
        Toast.makeText(requireContext(), getString(R.string.welcome_back), Toast.LENGTH_SHORT)
            .show()
    }

    private fun renderAnimations() {
        splashBinding.loadingIndicator.alpha = 0f
        splashBinding.loadingIndicator.animate()
            .alpha(0.7f)
            .setDuration(1000)
            .start()

        splashBinding.pleaseWaitTextView.alpha = 0f
        splashBinding.pleaseWaitTextView.animate()
            .alpha(1f)
            .setStartDelay(500)
            .setDuration(1000)
            .start()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _splashBinding = null
    }

}