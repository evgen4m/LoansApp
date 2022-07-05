package com.example.loans.domain.useCase.authorization

import com.example.loans.domain.repository.AuthRepository
import javax.inject.Inject

class UserIsAuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    fun invoke() = authRepository.isAuthorized()

}