package com.example.loans.presentation.fragments.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.ResponseResult
import com.example.loans.domain.useCase.loan.LoadAllLoanUseCase
import com.example.loans.extensions.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val loadAllLoanUseCase: LoadAllLoanUseCase
) : ViewModel() {

    private val _state = MutableLiveData<HistoryState>()
    val state: LiveData<HistoryState> = _state

    private val _allLoan = MutableLiveData<List<Loan>>()
    val allLoan: LiveData<List<Loan>> = _allLoan

    private val _navigateToDetailEvent = SingleLiveEvent<Int>()
    val navigateToDetailEvent: LiveData<Int> = _navigateToDetailEvent

    init {
        _state.value = HistoryState.Loading
        loadLoanList()
    }

    fun forceUpdate() {
        _state.value = HistoryState.ForceUpdate
        loadLoanList()
    }

    fun navigateToDetail(id: Int) {
        _navigateToDetailEvent.value = id
    }

    private fun loadLoanList() {
        viewModelScope.launch {
            val result = loadAllLoanUseCase.invoke()
            handleLoanResult(result = result)
        }
    }

    private fun handleLoanResult(result: ResponseResult<List<Loan>?>) {
        when(result) {
            is ResponseResult.Success -> {
                _allLoan.value = result.data!!
                _state.value = HistoryState.Success
            }
            is ResponseResult.Error -> {
                handleError(error = result.error)
            }
            is ResponseResult.LocalData -> {
                _allLoan.value = result.localData!!
                _state.value = HistoryState.Error(error = result.error)
            }
        }
    }

    private fun handleError(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> {
                _state.value = HistoryState.Error(error = ErrorEntity.Network)
            }
            is ErrorEntity.NotFound -> {
                _state.value = HistoryState.Error(error = ErrorEntity.NotFound)
            }
            is ErrorEntity.Unauthorized -> {
                _state.value = HistoryState.Error(error = ErrorEntity.Unauthorized)
            }
            is ErrorEntity.ServiceUnavailable -> {
                _state.value = HistoryState.Error(error = ErrorEntity.ServiceUnavailable)
            }
            is ErrorEntity.AccessDenied -> {
                _state.value = HistoryState.Error(error = ErrorEntity.AccessDenied)
            }
            is ErrorEntity.Unknown -> {
                _state.value = HistoryState.Error(error = ErrorEntity.Unknown)
            }
            is ErrorEntity.BadRequest -> {
                _state.value = HistoryState.Error(error = ErrorEntity.Unknown)
            }
        }
    }

}