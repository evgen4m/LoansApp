package com.example.loans.presentation.fragments.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.ResponseResult
import com.example.loans.domain.useCase.loan.LoanRequestUseCase
import com.example.loans.extensions.InputValidator
import com.example.loans.extensions.LiveEvent
import com.example.loans.extensions.MutableLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoanRequestViewModel @Inject constructor(
    private val loanRequestUseCase: LoanRequestUseCase
) : ViewModel() {

    private val _state = MutableLiveData<LoanRequestState>()
    val state: LiveData<LoanRequestState> = _state

    private val _requestAvailable = MutableLiveData(false)
    val requestAvailable: LiveData<Boolean> = _requestAvailable

    private val _userLastnameErrorEvent = MutableLiveEvent()
    val userLastnameErrorEvent: LiveEvent = _userLastnameErrorEvent

    private val _userFirstnameErrorEvent = MutableLiveEvent()
    val userFirstnameErrorEvent: LiveEvent = _userFirstnameErrorEvent

    private val _userNumberErrorEvent = MutableLiveEvent()
    val userNumberErrorEvent: LiveEvent = _userNumberErrorEvent

    private val _amountErrorEvent = MutableLiveEvent()
    val amountErrorEvent: LiveEvent = _amountErrorEvent

    private val _navigateToHome = MutableLiveEvent()
    val navigateToHome: LiveEvent = _navigateToHome

    fun request(
        lastname: String,
        firstname: String,
        phoneNumber: String,
        amount: Int,
        percent: Double,
        period: Int
    ) {
        _state.value = LoanRequestState.Loading

        viewModelScope.launch {
            val result = loanRequestUseCase.invoke(
                lastName = lastname,
                firstName = firstname,
                phoneNumber = phoneNumber,
                amount = amount,
                percent = percent,
                period = period
            )
            handleRequestResult(result = result)
        }
    }

    fun navigateToHome() {
        _navigateToHome()
    }

    fun updateRequestData(
        lastname: String,
        firstname: String,
        phoneNumber: String,
        amount: String,
        maxAmount: Int
    ) {
        if (!userLastnameValid(name = lastname)) {
            _userLastnameErrorEvent()
            _requestAvailable.value = false
        } else if(!userFirstnameValid(name = firstname)) {
            _userFirstnameErrorEvent()
            _requestAvailable.value = false
        }else if (!phoneNumberValid(phoneNumber = phoneNumber)) {
            _userNumberErrorEvent()
            _requestAvailable.value = false
        }
        else if (!amountValid(amount = amount, maxAmount = maxAmount)) {
            _amountErrorEvent()
            _requestAvailable.value = false
        }
        else {
            _requestAvailable.value = true
        }
    }

    private fun handleRequestResult(result: ResponseResult<Loan>) {
        when (result) {
            is ResponseResult.Success -> {
                _state.value = LoanRequestState.Success(data = result.data)
            }
            is ResponseResult.Error -> {
                handleErrorResult(error = result.error)
            }

        }
    }

    private fun handleErrorResult(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> {
                _state.value = LoanRequestState.Error(error = ErrorEntity.Network)
            }
            is ErrorEntity.NotFound -> {
                _state.value = LoanRequestState.Error(error = ErrorEntity.NotFound)
            }
            is ErrorEntity.Unauthorized -> {
                _state.value = LoanRequestState.Error(error = ErrorEntity.Unauthorized)
            }
            is ErrorEntity.ServiceUnavailable -> {
                _state.value = LoanRequestState.Error(error = ErrorEntity.ServiceUnavailable)
            }
            is ErrorEntity.AccessDenied -> {
                _state.value = LoanRequestState.Error(error = ErrorEntity.AccessDenied)
            }
            is ErrorEntity.Unknown -> {
                _state.value = LoanRequestState.Error(error = ErrorEntity.Unknown)
            }
            is ErrorEntity.BadRequest -> {
                _state.value = LoanRequestState.Error(error = ErrorEntity.BadRequest)
            }
        }
    }

    private fun phoneNumberValid(phoneNumber: String): Boolean =
        InputValidator.phoneNumberValidate(phoneNumber = phoneNumber)

    private fun amountValid(amount: String, maxAmount: Int): Boolean {
        if (amount.isNotBlank()) {
            return amount.toInt() in 1000..maxAmount
        }
        return false
    }

    private fun userFirstnameValid(name: String) =
        InputValidator.userNameValidator(name = name)

    private fun userLastnameValid(name: String) =
        InputValidator.userNameValidator(name = name)

}