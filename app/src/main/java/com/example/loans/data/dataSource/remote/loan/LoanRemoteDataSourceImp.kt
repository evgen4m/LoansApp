package com.example.loans.data.dataSource.remote.loan

import com.example.loans.data.dataSource.remote.api.LoanApi
import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.LoanConditions
import com.example.loans.domain.entities.LoanRequest
import javax.inject.Inject

class LoanRemoteDataSourceImp @Inject constructor(
    private val loanApi: LoanApi
): LoanRemoteDataSource {


    override suspend fun getLoanConditions(): LoanConditions =
        loanApi.getLoanConditions()

    override suspend fun loanRequest(loanRequest: LoanRequest): Loan =
        loanApi.loanRequest(loanRequest = loanRequest)

    override suspend fun getAllLoan(): List<Loan> =
        loanApi.getAllLoan()

    override suspend fun getLoanById(id: Int): Loan =
        loanApi.getLoanById(id = id)


}