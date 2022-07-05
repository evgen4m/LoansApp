package com.example.loans.data.dataSource.remote.authorization

import com.example.loans.data.dataSource.local.setting.SettingsDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val settingsDataSource: SettingsDataSource
) : Interceptor {

    private companion object {
        private const val AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val finalToken: String? = settingsDataSource.getToken()
        if (finalToken != null) {
            request = request.newBuilder()
                .addHeader(AUTHORIZATION, finalToken)
                .build()
        }
        return chain.proceed(request)
    }
}