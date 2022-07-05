package com.example.loans.ui.fragments.home

import android.annotation.SuppressLint
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
import com.example.loans.databinding.HomePageFragmentBinding
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.LoanConditions
import com.example.loans.presentation.fragments.home.HomeState
import com.example.loans.presentation.fragments.home.HomeViewModel
import com.example.loans.ui.activity.MainActivity
import com.example.loans.ui.fragments.history.LoanListAdapter
import com.example.loans.ui.util.CustomAction
import com.example.loans.ui.util.FragmentHasCustomToolbarAction
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.home_page_fragment), FragmentHasCustomToolbarAction {

    private var _homeBinding: HomePageFragmentBinding? = null
    private val homeBinding get() = _homeBinding!!

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels { factory }

    private var loanListAdapter: LoanListAdapter? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _homeBinding = HomePageFragmentBinding.bind(view)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        loanListAdapter = LoanListAdapter(onClick = { id -> viewModel.navigateToDetail(id = id) })
        homeBinding.loanHomeHistoryLayout.homeHistoryRecycler.adapter = loanListAdapter

        viewModel.loadAllLoan()
        viewModel.loadDataConditions()
        observeViewModel()
        initViews()
    }

    private fun initViews() {
        homeBinding.apply {
            loanInfoLayout.buttonLoanRequest.setOnClickListener {
                viewModel.navigateToLoanRequest()
            }
            refreshLayout.setOnRefreshListener {
                viewModel.forceUpdate()
            }
            loanHomeHistoryLayout.buttonShowHistory.setOnClickListener {
                viewModel.navigateToHistory()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, ::checkHomeState)
        viewModel.userLogout.observe(viewLifecycleOwner) { showLoginScreen() }
        viewModel.navigateToLoanRequestEvent.observe(viewLifecycleOwner, ::showLoanRequestScreen)
        viewModel.navigateToDetailEvent.observe(viewLifecycleOwner, ::showLoanDetailScreen)
        viewModel.dataLoanConditions.observe(viewLifecycleOwner, ::updateLoanConditionsUi)
        viewModel.allLoan.observe(viewLifecycleOwner, ::updateLoanList)
        viewModel.navigateToHistoryEvent.observe(viewLifecycleOwner) { showHistoryScreen() }
    }

    private fun updateLoanList(list: List<Loan>) {
        if (list.isNotEmpty()) {
            if (list.size > 3) {
                loanListAdapter?.updateList(list = list.subList(0, 3))
                homeBinding.loanHomeHistoryLayout.buttonShowHistory.visibility = View.VISIBLE
            } else {
                loanListAdapter?.updateList(list = list)
                homeBinding.loanHomeHistoryLayout.buttonShowHistory.visibility = View.GONE
            }
            homeBinding.loanHomeHistoryLayout.textHistory.text = getString(R.string.history)
        } else {
            showEmptyListError()
        }
    }

    private fun checkHomeState(homeState: HomeState) {
        when (homeState) {
            is HomeState.Success -> {
                homeBinding.refreshLayout.isRefreshing = false
                showHomeSuccess()
            }
            is HomeState.Error -> {
                homeBinding.refreshLayout.isRefreshing = false
                showHomeError(error = homeState.error)
            }
            is HomeState.Loading -> {
                showDataLoading()
            }
            is HomeState.ForceUpdate -> {
                updateUiByForceUpdate()
            }
        }
    }

    private fun showHomeError(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> {
                showNetworkError()
            }
            is ErrorEntity.NotFound -> showHomeError(error = getString(R.string.not_found_error))
            is ErrorEntity.AccessDenied -> showHomeError(error = getString(R.string.access_denied_error))
            is ErrorEntity.ServiceUnavailable -> showHomeError(error = getString(R.string.service_unavailable_error))
            is ErrorEntity.Unauthorized -> showHomeError(error = getString(R.string.unauthorized_error))
            is ErrorEntity.Unknown -> showHomeError(error = getString(R.string.unknown_error))
            is ErrorEntity.BadRequest -> showHomeError(error = getString(R.string.unknown_error))
        }
    }

    private fun showHomeError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun showHomeSuccess() {
        homeBinding.apply {
            textNetworkError.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.GONE
            loanInfoLayout.loanConditionsContainer.visibility = View.VISIBLE
            loanHomeHistoryLayout.homeHistory.visibility = View.VISIBLE
        }
    }

    private fun showNetworkError() {
        homeBinding.apply {
            resultLayout.loadingProgress.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.GONE
            loanInfoLayout.loanConditionsContainer.visibility = View.GONE
            loanHomeHistoryLayout.homeHistory.visibility = View.VISIBLE
            textNetworkError.visibility = View.VISIBLE
        }
    }

    private fun showEmptyListError() {
        homeBinding.apply {
            loanHomeHistoryLayout.buttonShowHistory.visibility = View.GONE
            loanHomeHistoryLayout.homeHistory.visibility = View.VISIBLE
            loanHomeHistoryLayout.textHistory.text = getString(R.string.empty_history)
        }
    }

    private fun showDataLoading() {
        homeBinding.apply {
            loanInfoLayout.loanConditionsContainer.visibility = View.GONE
            loanHomeHistoryLayout.homeHistory.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.VISIBLE
            textNetworkError.visibility = View.GONE
        }
    }

    private fun updateUiByForceUpdate() {
        homeBinding.apply {
            loanInfoLayout.loanConditionsContainer.visibility = View.GONE
            loanHomeHistoryLayout.homeHistory.visibility = View.GONE
            resultLayout.resultContainer.visibility = View.GONE
            resultLayout.loadingProgress.visibility = View.GONE
            textNetworkError.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateLoanConditionsUi(data: LoanConditions) {
        homeBinding.apply {
            loanInfoLayout.textMaxAmount.text =
                getString(R.string.maxAmount, data.maxAmount.toString())
            loanInfoLayout.textPeriod.text = getString(R.string.period, data.period.toString())
            loanInfoLayout.textPercent.text = getString(
                R.string.percentValue,
                data.percent.toString()
            ) + getString(R.string.percent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

    private fun onLogoutPressed() {
        viewModel.logout()
        Toast.makeText(requireContext(), getString(R.string.all_the_best), Toast.LENGTH_SHORT)
            .show()
    }

    override fun getCustomAction(): CustomAction {
        return CustomAction(
            iconRes = R.drawable.ic_round_logout_24,
            textRes = R.string.logout,
            onCustomAction = {
                onLogoutPressed()
            }
        )
    }

    private fun showLoginScreen() {
        findNavController().navigate(R.id.login_graph, null, navOptions {
            popUpTo(R.id.home_graph) {
                inclusive = true
            }
        })
    }

    private fun showLoanRequestScreen(data: LoanConditions) {
        val directions =
            HomeFragmentDirections.actionHomeFragmentToLoanRequestFragment(loanConditions = data)
        findNavController().navigate(directions = directions)
    }

    private fun showHistoryScreen() {
        findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
    }

    private fun showLoanDetailScreen(id: Int) {
        val directions = HomeFragmentDirections.actionHomeFragmentToLoanDetailFragment(id = id)
        findNavController().navigate(directions)
    }


}