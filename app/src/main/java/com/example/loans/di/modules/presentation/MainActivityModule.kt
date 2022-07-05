package com.example.loans.di.modules.presentation

import com.example.loans.di.scope.FragmentScope
import com.example.loans.ui.fragments.detail.LoanDetailFragment
import com.example.loans.ui.fragments.information.InformationFragment
import com.example.loans.ui.fragments.history.HistoryFragment
import com.example.loans.ui.fragments.home.HomeFragment
import com.example.loans.ui.fragments.request.LoanRequestFragment
import com.example.loans.ui.fragments.login.LoginFragment
import com.example.loans.ui.fragments.registration.RegistrationFragment
import com.example.loans.ui.fragments.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    fun injectLoginFragment() : LoginFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [RegistrationFragmentModule::class])
    fun injectRegistrationFragment() : RegistrationFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    fun injectHomeFragment() : HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LoanRequestFragmentModule::class])
    fun injectLoanRequestFragment() : LoanRequestFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HistoryFragmentModule::class])
    fun injectHistoryFragment() : HistoryFragment


    @FragmentScope
    @ContributesAndroidInjector(modules = [FirstStartFragmentModule::class])
    fun injectFirstStartFragment() : InformationFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LoanDetailFragmentModule::class])
    fun injectLoanDetailFragment() : LoanDetailFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SplashFragmentModule::class])
    fun injectSplashFragment() : SplashFragment

}