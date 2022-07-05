package com.example.loans.presentation.fragments.home

import com.example.loans.domain.entities.ErrorEntity

sealed class HomeState {

    object Loading: HomeState()

    object ForceUpdate: HomeState()

    object Success: HomeState()

    data class Error(val error: ErrorEntity) : HomeState()

}