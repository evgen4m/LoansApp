package com.example.loans.data.repository

import com.example.loans.data.dataSource.local.setting.SettingsDataSource
import com.example.loans.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImp @Inject constructor(
    private val settingsDataSource: SettingsDataSource
) : SettingsRepository{

    override fun firstStart(firstStart: Boolean) =
        settingsDataSource.firstStart(firstStart = firstStart)

    override fun itFirstStart(): Boolean =
        settingsDataSource.itFirstStart()
}