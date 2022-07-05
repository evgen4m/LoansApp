package com.example.loans.di.modules.presentation

import androidx.lifecycle.ViewModel
import com.example.loans.presentation.fragments.registration.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface RegistrationFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel

}
