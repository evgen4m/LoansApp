package com.example.loans.di.modules.presentation

import androidx.lifecycle.ViewModel
import com.example.loans.presentation.fragments.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SplashFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun bindSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}