package com.example.loans.presentation.fragments.splash

sealed class SplashState {

    object UserAuthorized : SplashState()

    object UserNotAuthorized: SplashState()

}