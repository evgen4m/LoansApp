package com.example.loans.di.modules.data

import com.example.loans.data.dataSource.local.setting.SettingsDataSource
import com.example.loans.data.dataSource.remote.api.AuthApi
import com.example.loans.data.dataSource.remote.api.LoanApi
import com.example.loans.data.dataSource.remote.authorization.AuthInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule() {

    private companion object {

        const val BASE_URL = "https://shiftlab.cft.ru:7777"

    }

    @Provides
    fun provideGson() : Gson =
        GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun provideOkHttpClient(settingsDataSource: SettingsDataSource) : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(settingsDataSource))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()


    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) = retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideLoanApi(retrofit: Retrofit) = retrofit.create(LoanApi::class.java)


}