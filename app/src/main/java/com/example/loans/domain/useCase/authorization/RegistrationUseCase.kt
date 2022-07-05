package com.example.loans.domain.useCase.authorization

import com.example.loans.domain.entities.AuthModel
import com.example.loans.domain.repository.AuthRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun invoke(name: String, password: String) =
        authRepository.registration(authModel = AuthModel(name = name, password = password))

}