package com.example.loans.presentation.fragments.request

import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan

sealed class LoanRequestState {

    object Loading: LoanRequestState()

    data class Success(val data: Loan): LoanRequestState()

    data class Error(val error: ErrorEntity) : LoanRequestState()

}