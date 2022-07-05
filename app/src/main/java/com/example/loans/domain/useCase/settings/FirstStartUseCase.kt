package com.example.loans.domain.useCase.settings

import com.example.loans.domain.repository.SettingsRepository
import javax.inject.Inject

class FirstStartUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    fun firstStart(firstStart: Boolean) = settingsRepository.firstStart(firstStart = firstStart)

}