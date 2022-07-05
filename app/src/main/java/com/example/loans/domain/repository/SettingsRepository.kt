package com.example.loans.domain.repository

interface SettingsRepository {

    fun firstStart(firstStart: Boolean)

    fun itFirstStart() : Boolean

}