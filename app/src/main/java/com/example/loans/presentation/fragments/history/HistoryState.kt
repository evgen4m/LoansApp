package com.example.loans.presentation.fragments.history

import com.example.loans.domain.entities.ErrorEntity

sealed class HistoryState {

    object Loading: HistoryState()

    object ForceUpdate: HistoryState()

    object Success: HistoryState()

    data class Error(val error: ErrorEntity) : HistoryState()

}
