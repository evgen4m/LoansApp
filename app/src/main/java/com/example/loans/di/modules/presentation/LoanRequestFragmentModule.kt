package com.example.loans.di.modules.presentation

import androidx.lifecycle.ViewModel
import com.example.loans.presentation.fragments.request.LoanRequestViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LoanRequestFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoanRequestViewModel::class)
    fun bindLoanRequestViewModel(loanRequestViewModel: LoanRequestViewModel): ViewModel

}
