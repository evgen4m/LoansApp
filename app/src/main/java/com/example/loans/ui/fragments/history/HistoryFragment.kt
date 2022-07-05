package com.example.loans.ui.fragments.history

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.loans.R
import com.example.loans.databinding.HistoryLayoutBinding
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan
import com.example.loans.presentation.fragments.history.HistoryState
import com.example.loans.presentation.fragments.history.HistoryViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HistoryFragment: Fragment(R.layout.history_layout) {

    private var _historyBinding: HistoryLayoutBinding? = null
    private val historyBinding get() = _historyBinding!!

    @Inject lateinit var factory: ViewModelProvider.Factory

    private val viewModel: HistoryViewModel by viewModels { factory }

    private var loanListAdapter: LoanListAdapter? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _historyBinding = HistoryLayoutBinding.bind(view)
        loanListAdapter = LoanListAdapter( onClick = { id -> viewModel.navigateToDetail(id = id) })
        historyBinding.historyFragmentRecycler.adapter = loanListAdapter

        observeViewModel()
        initViews()
    }

    private fun initViews() {
        historyBinding.historyRefresh.setOnRefreshListener {
            viewModel.forceUpdate()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::showHistoryState)
        viewModel.navigateToDetailEvent.observe(viewLifecycleOwner, ::showDetailScreen)
        viewModel.allLoan.observe(viewLifecycleOwner, ::updateListLoan)
    }

    private fun updateListLoan(list: List<Loan>) {
        loanListAdapter?.updateList(list = list)
    }

    private fun showHistoryState(historyState: HistoryState) {
        when(historyState) {
            is HistoryState.Success -> { showHistorySuccess() }
            is HistoryState.Error -> { showHistoryError(error = historyState.error) }
            is HistoryState.Loading -> { showHistoryLoading() }
            is HistoryState.ForceUpdate -> { updateUiByForceUpdate() }
        }
    }

    private fun showHistoryError(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> { showNetworkError() }
            is ErrorEntity.NotFound -> showHistoryError(error = getString(R.string.not_found_error))
            is ErrorEntity.AccessDenied -> showHistoryError(error = getString(R.string.not_found_error))
            is ErrorEntity.ServiceUnavailable -> showHistoryError(error = getString(R.string.service_unavailable_error))
            is ErrorEntity.Unauthorized -> showHistoryError(error = getString(R.string.unauthorized_error))
            is ErrorEntity.Unknown -> showHistoryError(error = getString(R.string.unknown_error))
            is ErrorEntity.BadRequest -> showHistoryError(error = getString(R.string.bad_request))
        }
    }

    private fun showHistoryError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun showNetworkError() {
        historyBinding.apply {
            historyRefresh.isRefreshing = false
            historyFragmentRecycler.visibility = View.VISIBLE
            textNetworkError.visibility = View.VISIBLE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.GONE
        }
    }

    private fun showHistoryLoading() {
        historyBinding.apply {
            historyFragmentRecycler.visibility = View.GONE
            textNetworkError.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.VISIBLE
        }
    }

    private fun showHistorySuccess() {
        historyBinding.apply {
            historyRefresh.isRefreshing = false
            textNetworkError.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.GONE
            historyFragmentRecycler.visibility = View.VISIBLE
        }
    }

    private fun updateUiByForceUpdate() {
        historyBinding.apply {
            textNetworkError.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.GONE
            historyFragmentRecycler.visibility = View.GONE
        }
    }

    private fun showDetailScreen(id: Int) {
        val directions = HistoryFragmentDirections.actionHistoryFragmentToLoanDetailFragment2(id = id)
        findNavController().navigate(directions = directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _historyBinding = null
    }

}