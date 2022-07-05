package com.example.loans.presentation.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.ResponseResult
import com.example.loans.domain.useCase.authorization.LoginUseCase
import com.example.loans.domain.useCase.settings.FirstStartUseCase
import com.example.loans.domain.useCase.settings.ItFirstStartUseCase
import com.example.loans.extensions.LiveEvent
import com.example.loans.extensions.MutableLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val firstStartUseCase: FirstStartUseCase,
    private val itFirstStartUseCase: ItFirstStartUseCase
) : ViewModel() {

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> = _state

    private val _passwordErrorEvent = MutableLiveEvent()
    val passwordErrorEvent: LiveEvent = _passwordErrorEvent

    private val _userNameErrorEvent = MutableLiveEvent()
    val userNameErrorEvent: LiveEvent = _userNameErrorEvent

    private val _loginAvailable = MutableLiveData(false)
    val loginAvailable: LiveData<Boolean> = _loginAvailable

    private val _navigateToRegistrationEvent = MutableLiveEvent()
    val navigateToRegistrationEvent: LiveEvent = _navigateToRegistrationEvent


    fun login(name: String, password: String) {
        _state.value = LoginState.Loading(loading = true)
        viewModelScope.launch {
            val result = loginUseCase.invoke(name = name, password = password)
            handleLoginResult(result)
        }
    }

    private fun itFirstStart() : Boolean {
        return itFirstStartUseCase.invoke()
    }

    fun updateLoginData(name: String, password: String) {
        handleLoginData(name = name, password = password)
    }

    fun navigateToRegistration() {
        _navigateToRegistrationEvent()
    }

    private fun handleLoginResult(result: ResponseResult<String>) {
        _state.value = LoginState.Loading(loading = false)
        when (result) {
            is ResponseResult.Success -> {
                if (itFirstStart()) {
                    _state.value = LoginState.FirstStart
                    firstStartUseCase.firstStart(firstStart = false)
                }else {
                    _state.value = LoginState.Success
                }
            }
            is ResponseResult.Error -> {
               handleError(error = result.error)
            }
        }
    }

    private fun handleError(error: ErrorEntity) {
        when (error) {
            is ErrorEntity.Network -> {
                _state.value = LoginState.Error(error = ErrorEntity.Network)
            }
            is ErrorEntity.NotFound -> {
                _state.value = LoginState.Error(error = ErrorEntity.NotFound)
            }
            is ErrorEntity.Unauthorized -> {
                _state.value = LoginState.Error(error = ErrorEntity.Unauthorized)
            }
            is ErrorEntity.ServiceUnavailable -> {
                _state.value = LoginState.Error(error = ErrorEntity.ServiceUnavailable)
            }
            is ErrorEntity.AccessDenied -> {
                _state.value = LoginState.Error(error = ErrorEntity.AccessDenied)
            }
            is ErrorEntity.Unknown -> {
                _state.value = LoginState.Error(error = ErrorEntity.Unknown)
            }
            is ErrorEntity.BadRequest -> {
                _state.value = LoginState.Error(error = ErrorEntity.BadRequest)
            }
        }
    }

    private fun handleLoginData(name: String, password: String) {
        if (!userNameIsNotEmpty(name = name)) {
            _userNameErrorEvent()
            _loginAvailable.value = false
        } else if (!isPasswordValid(password = password)) {
            _passwordErrorEvent()
            _loginAvailable.value = false
        } else {
            _loginAvailable.value = true
        }
    }

    private fun userNameIsNotEmpty(name: String) : Boolean =
        name.isNotBlank()

    private fun isPasswordValid(password: String): Boolean =
        password.length >= 5


}