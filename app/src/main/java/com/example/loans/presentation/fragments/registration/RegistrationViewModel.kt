package com.example.loans.presentation.fragments.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.ResponseResult
import com.example.loans.domain.entities.UserEntity
import com.example.loans.domain.useCase.authorization.RegistrationUseCase
import com.example.loans.extensions.LiveEvent
import com.example.loans.extensions.MutableLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {

    private val _state = MutableLiveData<RegistrationState>()
    val state: LiveData<RegistrationState> = _state

    private val _registrationAvailable = MutableLiveData(false)
    val registrationAvailable: LiveData<Boolean> = _registrationAvailable

    private val _passwordErrorEvent = MutableLiveEvent()
    val passwordErrorEvent: LiveEvent = _passwordErrorEvent

    private val _userNameErrorEvent = MutableLiveEvent()
    val userNameErrorEvent: LiveEvent = _userNameErrorEvent

    fun registration(name: String, password: String) {
        viewModelScope.launch {
            val result = registrationUseCase.invoke(name = name, password = password)
            handleRegistrationResult(result = result)
        }
    }

    private fun handleRegistrationResult(result: ResponseResult<UserEntity>) {
        when(result) {
            is ResponseResult.Success -> {
                _state.value = RegistrationState.Success(data = result.data.name)
            }
            is ResponseResult.Error -> {
                handleRegistrationError(error = result.error)
            }
        }
    }

    private fun handleRegistrationError(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> {
                _state.value = RegistrationState.Error(error = ErrorEntity.Network)
            }
            is ErrorEntity.NotFound -> {
                _state.value = RegistrationState.Error(error = ErrorEntity.NotFound)
            }
            is ErrorEntity.Unauthorized -> {
                _state.value = RegistrationState.Error(error = ErrorEntity.Unauthorized)
            }
            is ErrorEntity.ServiceUnavailable -> {
                _state.value = RegistrationState.Error(error = ErrorEntity.ServiceUnavailable)
            }
            is ErrorEntity.AccessDenied -> {
                _state.value = RegistrationState.Error(error = ErrorEntity.AccessDenied)
            }
            is ErrorEntity.Unknown -> {
                _state.value = RegistrationState.Error(error = ErrorEntity.Unknown)
            }
            is ErrorEntity.BadRequest -> {
                _state.value = RegistrationState.Error(error = ErrorEntity.BadRequest)
            }
        }
    }

    fun updateRegistrationData(name: String, password: String) {
        handleLoginData(name = name, password = password)
    }

    private fun handleLoginData(name: String, password: String) {
        if (!userNameIsNotEmpty(name = name)) {
            _userNameErrorEvent()
            _registrationAvailable.value = false
        } else if (!isPasswordValid(password = password)) {
            _passwordErrorEvent()
            _registrationAvailable.value = false
        } else {
            _registrationAvailable.value = true
        }
    }

    private fun userNameIsNotEmpty(name: String) : Boolean =
        name.isNotBlank()

    private fun isPasswordValid(password: String): Boolean =
        password.length >= 5



}