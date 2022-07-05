package com.example.loans.app

import android.app.Application
import com.example.loans.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class LoansApp : Application(), HasAndroidInjector {


    @Inject lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory().create(this).inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = injector

}