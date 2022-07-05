package com.example.loans.domain.useCase.loan

import com.example.loans.domain.entities.LoanRequest
import com.example.loans.domain.repository.LoanRepository
import javax.inject.Inject

class LoanRequestUseCase @Inject constructor(
    private val loanRepository: LoanRepository
) {

    suspend fun invoke(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        amount: Int,
        percent: Double,
        period: Int
    ) = loanRepository.request(
        loanRequest = LoanRequest(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            amount = amount,
            period = period,
            percent = percent
        )
    )

}