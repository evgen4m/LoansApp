package com.example.loans.ui.fragments.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.loans.R
import com.example.loans.databinding.AuthFragmentLayoutBinding
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.extensions.afterTextChanged
import com.example.loans.presentation.fragments.login.LoginState
import com.example.loans.presentation.fragments.login.LoginViewModel
import com.example.loans.ui.activity.MainActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.auth_fragment_layout) {

    private var _loginBinding: AuthFragmentLayoutBinding? = null
    private val loginBinding get() = _loginBinding!!

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: LoginViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _loginBinding = AuthFragmentLayoutBinding.bind(view)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        observeViewModel()
        initViews()
    }

    private fun initViews() {

        loginBinding.buttonLogin.setOnClickListener {
            loginBinding.apply {
                viewModel.login(
                    name = textFirstnameInput.text.toString(),
                    password = textPassInput.text.toString()
                )
            }
        }

        loginBinding.buttonRegister.setOnClickListener {
            viewModel.navigateToRegistration()
        }

        loginBinding.apply {
            textFirstnameInput.afterTextChanged {
                updateData()
            }
            textPassInput.afterTextChanged {
                updateData()
            }
        }
    }

    private fun updateData() {
        viewModel.updateLoginData(
            name = loginBinding.textFirstnameInput.text.toString(),
            password = loginBinding.textPassInput.text.toString()
        )
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::checkLoginState)
        viewModel.navigateToRegistrationEvent.observe(viewLifecycleOwner) { showRegistrationScreen() }
        viewModel.userNameErrorEvent.observe(viewLifecycleOwner) { showUserNameError() }
        viewModel.passwordErrorEvent.observe(viewLifecycleOwner) { showPasswordError() }
        viewModel.loginAvailable.observe(viewLifecycleOwner, ::toggleButton)
    }

    private fun toggleButton(enable: Boolean) {
        loginBinding.buttonLogin.isEnabled = enable
    }

    private fun showPasswordError() {
        loginBinding.textPassInput.error = getString(R.string.password_is_short)
    }

    private fun showUserNameError() {
        loginBinding.textFirstnameInput.error = getString(R.string.enter_user_name)
    }

    private fun checkLoginState(loginFragmentState: LoginState) {
        when (loginFragmentState) {
            is LoginState.Success -> {
                updateUiWithUser()
            }
            is LoginState.Error -> {
                showLoginFailed(error = loginFragmentState.error)
            }
            is LoginState.FirstStart -> {
                showFirstStartScreen()
            }
            is LoginState.Loading -> {
                toggleProgress(visible = loginFragmentState.loading)
            }
        }
    }

    private fun showLoginFailed(error: ErrorEntity) {
        val textError = when (error) {
            is ErrorEntity.Network -> getString(R.string.network_error)
            is ErrorEntity.NotFound -> getString(R.string.user_not_found_error)
            is ErrorEntity.AccessDenied -> getString(R.string.access_denied_error)
            is ErrorEntity.ServiceUnavailable -> getString(R.string.service_unavailable_error)
            is ErrorEntity.Unauthorized -> getString(R.string.unauthorized_error)
            is ErrorEntity.Unknown -> getString(R.string.unknown_error)
            is ErrorEntity.BadRequest -> getString(R.string.bad_request)
        }
        Toast.makeText(requireContext(), textError, Toast.LENGTH_SHORT).show()
    }

    private fun updateUiWithUser() {
        Toast.makeText(requireContext(), getString(R.string.welcome), Toast.LENGTH_SHORT).show()
        navigateToHomeFragment()
    }

    private fun toggleProgress(visible: Boolean) {
        loginBinding.loadingProgress.isVisible = visible
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.home_graph, null, navOptions {
            popUpTo(R.id.login_graph) {
                inclusive = true
            }
        })
    }

    private fun showRegistrationScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun showFirstStartScreen() {
        findNavController().navigate(R.id.firstrun_graph, null, navOptions {
            popUpTo(R.id.login_graph) {
                inclusive = true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _loginBinding = null
    }

}