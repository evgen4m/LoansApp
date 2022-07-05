package com.example.loans.presentation.fragments.registration

import com.example.loans.domain.entities.ErrorEntity

sealed class RegistrationState {

    data class Success(val data: String): RegistrationState()

    data class Error(val error: ErrorEntity): RegistrationState()

}
