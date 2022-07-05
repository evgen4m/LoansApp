package com.example.loans.ui.fragments.request

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.loans.R
import com.example.loans.databinding.LoanRequestFragmentBinding
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan
import com.example.loans.extensions.afterTextChanged
import com.example.loans.presentation.fragments.request.LoanRequestState
import com.example.loans.presentation.fragments.request.LoanRequestViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoanRequestFragment : Fragment(R.layout.loan_request_fragment) {

    private var _requestBinding: LoanRequestFragmentBinding? = null
    private val requestBinding get() = _requestBinding!!

    private val args: LoanRequestFragmentArgs by navArgs()

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: LoanRequestViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _requestBinding = LoanRequestFragmentBinding.bind(view)
        observeViewModel()
        initViews()
        updateUi()
    }

    private fun initViews() {
        requestBinding.apply {

            buttonLoanRequest.setOnClickListener {
                viewModel.request(
                    firstname = requestBinding.textFirstnameInput.text.toString(),
                    lastname = requestBinding.textLastnameInput.text.toString(),
                    phoneNumber = requestBinding.textNumberInput.text.toString(),
                    amount = requestBinding.textAmountInput.text.toString().toInt(),
                    percent = args.loanConditions.percent,
                    period = args.loanConditions.period
                )
            }

            textLastnameInput.apply {
                afterTextChanged {
                    updateRequestData()
                }
            }

            textFirstnameInput.apply {
                afterTextChanged {
                    updateRequestData()
                }
            }

            textNumberInput.apply {
                afterTextChanged {
                    updateRequestData()
                }
            }

            textAmountInput.apply {
                afterTextChanged {
                    updateRequestData()
                }
            }

        }
    }

    private fun updateRequestData() {
        requestBinding.apply {
            viewModel.updateRequestData(
                firstname = textFirstnameInput.text.toString(),
                lastname = textLastnameInput.text.toString(),
                phoneNumber = textNumberInput.text.toString(),
                amount = textAmountInput.text.toString(),
                maxAmount = args.loanConditions.maxAmount
            )
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::checkLoanRequestState)
        viewModel.requestAvailable.observe(viewLifecycleOwner, ::requestAvailable)

        viewModel.amountErrorEvent.observe(viewLifecycleOwner) { showAmountError() }
        viewModel.userFirstnameErrorEvent.observe(viewLifecycleOwner) { showUserNameError() }
        viewModel.userLastnameErrorEvent.observe(viewLifecycleOwner) { showUserLastNameError() }
        viewModel.userNumberErrorEvent.observe(viewLifecycleOwner) { showPhoneNumberError() }
        viewModel.navigateToHome.observe(viewLifecycleOwner) { showHomeScreen() }
    }

    private fun showPhoneNumberError() {
        requestBinding.textNumberInput.error = getString(R.string.phoneNumber_error)
    }

    private fun showUserLastNameError() {
        requestBinding.textLastnameInput.error = getString(R.string.lastname_error)
    }

    private fun showUserNameError() {
        requestBinding.textFirstnameInput.error = getString(R.string.firstname_error)
    }

    private fun showAmountError() {
        requestBinding.textAmountInput.error =
            getString(R.string.amount_error, args.loanConditions.maxAmount.toString())
    }

    private fun requestAvailable(visible: Boolean) {
        requestBinding.buttonLoanRequest.isEnabled = visible
    }

    private fun showHomeScreen() {
        findNavController().popBackStack()
    }

    private fun checkLoanRequestState(loanRequestState: LoanRequestState) {
        when (loanRequestState) {
            is LoanRequestState.Success -> {
                showSuccessRequestScreen(result = loanRequestState.data)
            }
            is LoanRequestState.Error -> {
                showError(error = loanRequestState.error)
            }
            is LoanRequestState.Loading -> {
                showRequestLoading()
            }
        }
    }

    private fun showError(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> { showErrorScreen(R.string.network_error) }
            is ErrorEntity.NotFound -> { showErrorScreen(R.string.not_found_error) }
            is ErrorEntity.AccessDenied -> { showErrorScreen(R.string.access_denied_error) }
            is ErrorEntity.ServiceUnavailable -> { showErrorScreen(R.string.service_unavailable_error) }
            is ErrorEntity.Unauthorized -> { showErrorScreen(R.string.unauthorized_error) }
            is ErrorEntity.Unknown -> { showErrorScreen(R.string.unknown_error) }
            is ErrorEntity.BadRequest -> { showErrorScreen(R.string.not_found_conditions) }
        }
    }

    private fun showRequestLoading() {
        requestBinding.apply {
            requestLayout.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.VISIBLE
        }
    }

    private fun showSuccessRequestScreen(result: Loan) {
        requestBinding.apply {
            requestLayout.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.VISIBLE
            resultLayout.textResult.text = getString(R.string.request_success, result.firstName)
            resultLayout.buttonUpdate.text = getString(R.string.back_to_home)
            resultLayout.buttonUpdate.setOnClickListener {
                viewModel.navigateToHome()
            }
        }
    }

    private fun showErrorScreen(error: Int) {
        requestBinding.apply {
            requestLayout.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.VISIBLE
            resultLayout.textResult.text = getString(error)
            resultLayout.loadingProgress.visibility = View.GONE
            resultLayout.buttonUpdate.text = getString(R.string.back_to_home)
            resultLayout.buttonUpdate.setOnClickListener {
                viewModel.navigateToHome()
            }
        }
    }

    private fun updateUi() {
        requestBinding.apply {
            requestLayout.visibility = View.VISIBLE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.GONE
            textPercent.text = getString(
                R.string.interest_rate_value,
                args.loanConditions.percent.toString()
            ) + getString(R.string.percent)
            textPeriod.text =
                getString(R.string.loan_term_value, args.loanConditions.period.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _requestBinding = null
    }
}