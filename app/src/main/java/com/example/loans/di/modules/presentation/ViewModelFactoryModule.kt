package com.example.loans.di.modules.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loans.presentation.fragments.util.ViewModelProvidersFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelProviderFactory(
        viewModelProviderProvidersFactories: ViewModelProvidersFactory
    ): ViewModelProvider.Factory

}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)