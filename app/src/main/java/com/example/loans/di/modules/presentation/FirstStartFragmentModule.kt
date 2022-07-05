package com.example.loans.di.modules.presentation

import androidx.lifecycle.ViewModel
import com.example.loans.presentation.fragments.information.InformationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FirstStartFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(InformationViewModel::class)
    fun bindFirstStartViewModel(firstStartViewModel: InformationViewModel): ViewModel

}
