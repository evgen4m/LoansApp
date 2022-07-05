package com.example.loans.domain.useCase.settings

import com.example.loans.domain.repository.SettingsRepository
import javax.inject.Inject

class ItFirstStartUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
){

    fun invoke() = settingsRepository.itFirstStart()

}