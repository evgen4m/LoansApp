package com.example.loans.presentation.fragments.detail

import com.example.loans.domain.entities.ErrorEntity
import com.example.loans.domain.entities.Loan

sealed class DetailState {

    object Loading: DetailState()

    data class Success(val data: Loan) : DetailState()

    data class Error(val error: ErrorEntity) : DetailState()

}