package com.example.loans.di.modules.presentation

import androidx.lifecycle.ViewModel
import com.example.loans.presentation.fragments.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoginFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

}
