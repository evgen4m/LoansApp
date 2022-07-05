package com.example.loans.presentation.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.LoanConditions
import com.example.loans.domain.entities.ResponseResult
import com.example.loans.domain.useCase.authorization.LogoutUserUseCase
import com.example.loans.domain.useCase.loan.LoadAllLoanUseCase
import com.example.loans.domain.useCase.loan.LoadLoanConditionsUseCase
import com.example.loans.extensions.LiveEvent
import com.example.loans.extensions.MutableLiveEvent
import com.example.loans.extensions.SingleLiveEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val loadLoanConditionsUseCase: LoadLoanConditionsUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val loadAllLoanUseCase: LoadAllLoanUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _state

    private val _navigateToLoanRequestEvent = SingleLiveEvent<LoanConditions>()
    val navigateToLoanRequestEvent: LiveData<LoanConditions> = _navigateToLoanRequestEvent

    private val _navigateToHistoryEvent = MutableLiveEvent()
    val navigateToHistoryEvent: LiveEvent = _navigateToHistoryEvent

    private val _navigateToDetailEvent = SingleLiveEvent<Int>()
    val navigateToDetailEvent: LiveData<Int> = _navigateToDetailEvent

    private val _userLogout = MutableLiveEvent()
    val userLogout: LiveEvent = _userLogout

    private val _dataLoanConditions = MutableLiveData<LoanConditions>()
    val dataLoanConditions: LiveData<LoanConditions> = _dataLoanConditions

    private val _allLoan = MutableLiveData<List<Loan>>()
    val allLoan: LiveData<List<Loan>> = _allLoan

    init {
        _state.value = HomeState.Loading
    }

    fun navigateToDetail(id: Int) {
        _navigateToDetailEvent.value = id
    }

    fun navigateToHistory() {
        _navigateToHistoryEvent()
    }

    fun forceUpdate() {
        _state.value = HomeState.ForceUpdate
        loadDataConditions()
        loadAllLoan()
    }

    fun navigateToLoanRequest() {
        _navigateToLoanRequestEvent.value = dataLoanConditions.value
    }

    fun logout() {
        _userLogout()
        logoutUserUseCase.invoke()
    }

    fun loadDataConditions() {
        viewModelScope.launch {
            val resultLoanConditions = async { loadLoanConditionsUseCase.invoke() }
            handleLoanConditionsResult(result = resultLoanConditions.await())
        }
    }

    fun loadAllLoan() {
        viewModelScope.launch {
            val resultAllLoan = async { loadAllLoanUseCase.invoke() }
            handleLoansResult(result = resultAllLoan.await())
        }
    }

    private fun handleLoansResult(result: ResponseResult<List<Loan>?>) {
        when (result) {
            is ResponseResult.Success -> {
                _state.value = HomeState.Success
                _allLoan.value = result.data!!
            }
            is ResponseResult.Error -> {
                handleError(error = result.error)
            }
            is ResponseResult.LocalData -> {
                _allLoan.value = result.localData!!
                _state.value = HomeState.Error(error = result.error)
            }
        }
    }

    private fun handleLoanConditionsResult(result: ResponseResult<LoanConditions?>) {
        when (result) {
            is ResponseResult.Success -> {
                _state.value = HomeState.Success
                _dataLoanConditions.value = result.data!!
            }
            is ResponseResult.Error -> {
                handleError(error = result.error)
            }
        }
    }

    private fun handleError(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> {
                _state.value = HomeState.Error(error = ErrorEntity.Network)
            }
            is ErrorEntity.NotFound -> {
                _state.value = HomeState.Error(error = ErrorEntity.NotFound)
            }
            is ErrorEntity.Unauthorized -> {
                _state.value = HomeState.Error(error = ErrorEntity.Unauthorized)
            }
            is ErrorEntity.ServiceUnavailable -> {
                _state.value = HomeState.Error(error = ErrorEntity.ServiceUnavailable)
            }
            is ErrorEntity.AccessDenied -> {
                _state.value = HomeState.Error(error = ErrorEntity.AccessDenied)
            }
            is ErrorEntity.Unknown -> {
                _state.value = HomeState.Error(error = ErrorEntity.Unknown)
            }
            is ErrorEntity.BadRequest -> {
                _state.value = HomeState.Error(error = ErrorEntity.Unknown)
            }

        }
    }

}