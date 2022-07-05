package com.example.loans.presentation.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.ResponseResult
import com.example.loans.domain.useCase.loan.LoanUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val loanUseCase: LoanUseCase
) : ViewModel() {

    private val _state = MutableLiveData<DetailState>()
    val state: LiveData<DetailState> = _state

    fun showLoanDetail(id: Int) {
        _state.value = DetailState.Loading

        viewModelScope.launch {
            val result = loanUseCase.invoke(id = id)
            handleResult(result = result)
        }
    }

    private fun handleResult(result: ResponseResult<Loan>) {
        when (result) {
            is ResponseResult.Success -> {
                _state.value = DetailState.Success(data = result.data)
            }
            is ResponseResult.Error -> {
                handleErrorResult(error = result.error)
            }
        }
    }

    private fun handleErrorResult(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> {
                _state.value = DetailState.Error(error = ErrorEntity.Network)
            }
            is ErrorEntity.NotFound -> {
                _state.value = DetailState.Error(error = ErrorEntity.NotFound)
            }
            is ErrorEntity.Unauthorized -> {
                _state.value = DetailState.Error(error = ErrorEntity.Unauthorized)
            }
            is ErrorEntity.ServiceUnavailable -> {
                _state.value = DetailState.Error(error = ErrorEntity.ServiceUnavailable)
            }
            is ErrorEntity.AccessDenied -> {
                _state.value = DetailState.Error(error = ErrorEntity.AccessDenied)
            }
            is ErrorEntity.Unknown -> {
                _state.value = DetailState.Error(error = ErrorEntity.Unknown)
            }
            is ErrorEntity.BadRequest -> {
                _state.value = DetailState.Error(error = ErrorEntity.Unknown)
            }
        }
    }

}