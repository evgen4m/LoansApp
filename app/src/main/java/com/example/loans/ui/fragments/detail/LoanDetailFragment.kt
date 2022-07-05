package com.example.loans.ui.fragments.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.loans.R
import com.example.loans.databinding.LoanDetailLayoutBinding
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.LoanState
import com.example.loans.extensions.DateFormat
import com.example.loans.presentation.fragments.detail.DetailState
import com.example.loans.presentation.fragments.detail.DetailViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoanDetailFragment : Fragment(R.layout.loan_detail_layout) {

    private var _detailBinding: LoanDetailLayoutBinding? = null
    private val detailBinding get() = _detailBinding!!

    private val args: LoanDetailFragmentArgs by navArgs()

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: DetailViewModel by viewModels {
        factory
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _detailBinding = LoanDetailLayoutBinding.bind(view)

        viewModel.showLoanDetail(args.id)
        observeViewModel()
        initViews()
    }

    private fun initViews() {
        detailBinding.resultLayout.buttonUpdate.setOnClickListener {
            viewModel.showLoanDetail(args.id)
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::checkDetailState)
    }

    private fun checkDetailState(detailState: DetailState?) {
        when (detailState) {
            is DetailState.Success -> {
                showDetailSuccess()
                updateUiFromLoan(detailLoan = detailState.data)
            }
            is DetailState.Error -> {
                showDetailError(error = detailState.error)
            }
            is DetailState.Loading -> {
                showDetailLoading()
            }
        }
    }

    private fun updateUiFromLoan(detailLoan: Loan) {
        detailBinding.apply {
            textLastname.text = getString(
                R.string.detail_full_name,
                detailLoan.lastName,
                detailLoan.firstName
            )
            textNumber.text = getString(
                R.string.detail_number,
                detailLoan.phoneNumber
            )
            textAmount.text = getString(
                R.string.detail_amount,
                detailLoan.amount
            )
            textPercent.text = getString(
                R.string.detail_percent,
                detailLoan.percent.toString()
            ) + getString(R.string.percent)
            textPeriod.text = getString(
                R.string.detail_period,
                detailLoan.period.toString()
            )
            textDate.text = getString(
                R.string.detail_date,
                DateFormat.getDateTimeFormat(date = detailLoan.date)
            )
            val state = when (detailLoan.state) {
                LoanState.APPROVED -> {
                    receivingInfo.visibility = View.VISIBLE
                    getString(R.string.approved)
                }
                LoanState.REGISTERED -> getString(R.string.registered)
                LoanState.REJECTED -> getString(R.string.rejected)
            }
            textState.text = getString(
                R.string.detail_status,
                state
            )
        }
    }

    private fun showDetailError(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> { showNetworkError() }
            is ErrorEntity.NotFound -> showDetailError(error = getString(R.string.not_found_error))
            is ErrorEntity.AccessDenied -> showDetailError(error = getString(R.string.access_denied_error))
            is ErrorEntity.ServiceUnavailable -> showDetailError(error = getString(R.string.service_unavailable_error))
            is ErrorEntity.Unauthorized -> showDetailError(error = getString(R.string.unauthorized_error))
            is ErrorEntity.Unknown -> showDetailError(error = getString(R.string.unknown_error))
            is ErrorEntity.BadRequest -> showDetailError(error = getString(R.string.bad_request))
        }
    }

    private fun showDetailError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun showNetworkError() {
        detailBinding.apply {
            detailInfoLayout.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.VISIBLE
        }
    }

    private fun showDetailLoading() {
        detailBinding.apply {
            detailInfoLayout.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.VISIBLE
        }
    }

    private fun showDetailSuccess() {
        detailBinding.apply {
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.GONE
            detailInfoLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _detailBinding = null
    }


}