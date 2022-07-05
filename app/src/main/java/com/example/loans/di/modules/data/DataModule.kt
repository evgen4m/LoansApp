package com.example.loans.di.modules.data

import android.content.Context
import com.example.loans.app.LoansApp
import com.example.loans.data.dataSource.local.cache.dataSource.LoanCacheDataSource
import com.example.loans.data.dataSource.local.cache.dataSource.LoanCacheDataSourceImp
import com.example.loans.data.dataSource.local.setting.SettingsDataSource
import com.example.loans.data.dataSource.local.setting.SettingsDataSourceImp
import com.example.loans.data.dataSource.remote.authorization.AuthDataSource
import com.example.loans.data.dataSource.remote.authorization.AuthDataSourceImp
import com.example.loans.data.dataSource.remote.loan.LoanRemoteDataSource
import com.example.loans.data.dataSource.remote.loan.LoanRemoteDataSourceImp
import dagger.Binds
import dagger.Module

@Module(includes = [RetrofitModule::class, RoomModule::class, DispatchersModule::class])
interface DataModule {

    @Binds
    fun bindAuthDataSource(authDataSourceImp: AuthDataSourceImp) : AuthDataSource

    @Binds
    fun bindSettingsDataSource(settingsDataSourceImp: SettingsDataSourceImp) : SettingsDataSource

    @Binds
    fun bindLoansDataSource(loansDataSourceImp: LoanRemoteDataSourceImp) : LoanRemoteDataSource

    @Binds
    fun bindLoansConditionsLocalDataSource(loanConditionsDataSourceImp: LoanCacheDataSourceImp) : LoanCacheDataSource

    @Binds
    fun bindAppContext(app: LoansApp) : Context

}