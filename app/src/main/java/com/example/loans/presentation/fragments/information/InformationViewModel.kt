package com.example.loans.presentation.fragments.information

import androidx.lifecycle.ViewModel
import com.example.loans.extensions.LiveEvent
import com.example.loans.extensions.MutableLiveEvent
import javax.inject.Inject

class InformationViewModel @Inject constructor() : ViewModel() {

    private val _navigateToHomeEvent = MutableLiveEvent()
    val navigateToHomeEvent: LiveEvent = _navigateToHomeEvent

    fun navigateToHome() {
        _navigateToHomeEvent()
    }

}