package com.example.loans.ui.fragments.registration

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.loans.R
import com.example.loans.databinding.RegistrationFragmentLayoutBinding
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.extensions.afterTextChanged
import com.example.loans.presentation.fragments.registration.RegistrationViewModel
import com.example.loans.presentation.fragments.registration.RegistrationState
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RegistrationFragment : Fragment(R.layout.registration_fragment_layout) {

    private var _registerBinding: RegistrationFragmentLayoutBinding? = null
    private val registerBinding get() = _registerBinding!!

    private val viewModel: RegistrationViewModel by viewModels {
        factory
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _registerBinding = RegistrationFragmentLayoutBinding.bind(view)
        observeViewModel()
        initViews()
    }

    private fun initViews() {
        registerBinding.buttonRegister.setOnClickListener {
            viewModel.registration(
                name = registerBinding.textFirstnameInput.text.toString(),
                password = registerBinding.textPassInput.text.toString()
            )
        }

        registerBinding.apply {
            textPassInput.afterTextChanged {
                updateData()
            }
            textFirstnameInput.afterTextChanged {
                updateData()
            }
        }
    }

    private fun updateData() {
        viewModel.updateRegistrationData(
            name = registerBinding.textFirstnameInput.text.toString(),
            password = registerBinding.textPassInput.text.toString()
        )
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::checkRegistrationState)
        viewModel.registrationAvailable.observe(viewLifecycleOwner, ::toggleButton)

        viewModel.passwordErrorEvent.observe(viewLifecycleOwner) { showPasswordError() }
        viewModel.userNameErrorEvent.observe(viewLifecycleOwner) { showUserNameError() }
    }

    private fun showUserNameError() {
        registerBinding.textFirstnameInput.error = getString(R.string.field_error)
    }

    private fun toggleButton(enable: Boolean) {
        registerBinding.buttonRegister.isEnabled = enable
    }

    private fun showPasswordError() {
        registerBinding.textPassInput.error = getString(R.string.password_is_short)
    }

    private fun checkRegistrationState(registrationState: RegistrationState) {
        when (registrationState) {
            is RegistrationState.Success -> {
                showSuccessRegistration(userName = registrationState.data)
            }
            is RegistrationState.Error -> {
                showRegistrationError(error = registrationState.error)
            }
        }
    }

    private fun showSuccessRegistration(userName: String) {
        Toast.makeText(
            requireContext(),
            getString(R.string.success_registration, userName),
            Toast.LENGTH_SHORT
        ).show()
        showLoginScreen()
    }

    private fun showLoginScreen() {
        findNavController().popBackStack()
    }

    private fun showRegistrationError(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> showRegistrationError(error = getString(R.string.network_error))
            is ErrorEntity.NotFound -> showRegistrationError(error = getString(R.string.not_found_error))
            is ErrorEntity.AccessDenied -> showRegistrationError(error = getString(R.string.access_denied_error))
            is ErrorEntity.ServiceUnavailable -> showRegistrationError(error = getString(R.string.service_unavailable_error))
            is ErrorEntity.Unauthorized ->showRegistrationError(error = getString(R.string.unauthorized_error))
            is ErrorEntity.Unknown -> showRegistrationError(error = getString(R.string.unknown_error))
            is ErrorEntity.BadRequest -> showRegistrationError(error = getString(R.string.user_already_exist))
        }
    }

    private fun showRegistrationError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _registerBinding = null
    }

}