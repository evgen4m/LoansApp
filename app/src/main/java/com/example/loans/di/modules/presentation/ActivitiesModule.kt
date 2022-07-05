package com.example.loans.di.modules.presentation

import com.example.loans.di.scope.ActivityScope
import com.example.loans.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun injectMainActivity() : MainActivity

}