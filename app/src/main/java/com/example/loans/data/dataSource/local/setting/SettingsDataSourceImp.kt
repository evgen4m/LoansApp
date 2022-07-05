package com.example.loans.data.dataSource.local.setting

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SettingsDataSourceImp @Inject constructor(private val context: Context) : SettingsDataSource {

    private companion object {
        const val STORAGE_NAME = "SettingsStorage"
        const val TOKEN_KEY = "tokenKey"
        const val FIRST_START_KEY = "firstStart"
    }

    private var settings: SharedPreferences? = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor? = settings?.edit()

    override fun saveToken(token: String) {
        editor?.putString(TOKEN_KEY, token)
        editor?.commit()
    }

    override fun getToken(): String? =
        settings?.getString(TOKEN_KEY, null)


    override fun clearToken() {
        editor?.remove("tokenKey")
        editor?.commit()
    }

    override fun itFirstStart(): Boolean =
        settings!!.getBoolean(FIRST_START_KEY, true)


    override fun firstStart(firstStart: Boolean) {
        editor?.putBoolean(FIRST_START_KEY, firstStart)
        editor?.commit()
    }


}