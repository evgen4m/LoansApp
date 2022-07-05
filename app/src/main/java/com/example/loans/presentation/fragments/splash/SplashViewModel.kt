package com.example.loans.presentation.fragments.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.loans.domain.repository.AuthRepository
import com.example.loans.extensions.SingleLiveEvent
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = SingleLiveEvent<SplashState>()
    val state: LiveData<SplashState> = _state

    init {
        val result = authRepository.isAuthorized()
        if (result) {
            _state.value = SplashState.UserAuthorized
        } else {
            _state.value = SplashState.UserNotAuthorized
        }
    }


}