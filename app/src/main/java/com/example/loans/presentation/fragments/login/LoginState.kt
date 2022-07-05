package com.example.loans.presentation.fragments.login

import com.example.loans.domain.entities.ErrorEntity

sealed class LoginState {

    object FirstStart: LoginState()

    object Success: LoginState()

    data class Loading(val loading: Boolean): LoginState()

    data class Error(val error: ErrorEntity): LoginState()

}
