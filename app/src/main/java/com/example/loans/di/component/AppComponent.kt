package com.example.loans.di.component

import com.example.loans.app.LoansApp
import com.example.loans.di.modules.data.DataModule
import com.example.loans.di.modules.domain.DomainModule
import com.example.loans.di.modules.presentation.ActivitiesModule
import com.example.loans.di.modules.presentation.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        ViewModelFactoryModule::class,
        ActivitiesModule::class,
        AndroidSupportInjectionModule::class
    ]
)
interface AppComponent : AndroidInjector<LoansApp> {

    @Component.Factory
    interface AppFactory : AndroidInjector.Factory<LoansApp>

}