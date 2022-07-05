package com.example.loans.di.modules.presentation

import androidx.lifecycle.ViewModel
import com.example.loans.presentation.fragments.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HomeFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}
