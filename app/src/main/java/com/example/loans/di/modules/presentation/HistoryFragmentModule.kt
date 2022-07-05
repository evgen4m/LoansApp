package com.example.loans.di.modules.presentation

import androidx.lifecycle.ViewModel
import com.example.loans.presentation.fragments.history.HistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface HistoryFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    fun bindHistoryViewModel(historyViewModel: HistoryViewModel): ViewModel

}
