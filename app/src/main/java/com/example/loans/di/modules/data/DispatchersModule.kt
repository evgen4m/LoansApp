package com.example.loans.di.modules.data

import com.example.loans.di.qualifiers.IoDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DispatchersModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher() : CoroutineDispatcher = Dispatchers.IO

}