package com.example.loans.di.modules.domain

import com.example.loans.data.repository.AuthRepositoryImp
import com.example.loans.data.repository.LoanRepositoryImp
import com.example.loans.data.repository.SettingsRepositoryImp
import com.example.loans.domain.repository.AuthRepository
import com.example.loans.domain.repository.LoanRepository
import com.example.loans.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindAuthRepository(authRepositoryImp: AuthRepositoryImp) : AuthRepository

    @Binds
    fun bindLoanRepository(loanRepositoryImp: LoanRepositoryImp) : LoanRepository

    @Binds
    fun bindSettingsRepository(settingsRepositoryImp: SettingsRepositoryImp) : SettingsRepository

}