package com.example.loans.domain.useCase.loan

import com.example.loans.domain.repository.LoanRepository
import javax.inject.Inject

class LoadAllLoanUseCase @Inject constructor(
    private val loanRepository: LoanRepository
) {

    suspend fun invoke() = loanRepository.loadAllLoan()

}