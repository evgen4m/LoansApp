package com.example.loans.presentation.fragments.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelProvidersFactory @Inject constructor(
    private val viewModelFactories: Map<Class<out ViewModel>,  @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModelFactories.getValue(modelClass as Class<out ViewModel>).get() as T

}