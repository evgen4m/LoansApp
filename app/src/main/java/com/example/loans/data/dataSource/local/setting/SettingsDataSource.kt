package com.example.loans.data.dataSource.local.setting

interface SettingsDataSource {

     fun saveToken(token: String)

     fun getToken() : String?

     fun clearToken()

     fun itFirstStart() : Boolean

     fun firstStart(firstStart: Boolean)

}