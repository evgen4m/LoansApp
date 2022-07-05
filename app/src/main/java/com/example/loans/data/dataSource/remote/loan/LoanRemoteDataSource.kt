package com.example.loans.data.dataSource.remote.loan

import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.LoanConditions
import com.example.loans.domain.entities.LoanRequest

interface LoanRemoteDataSource {

    suspend fun getLoanConditions() : LoanConditions

    suspend fun loanRequest(loanRequest: LoanRequest) : Loan

    suspend fun getAllLoan() : List<Loan>

    suspend fun getLoanById(id: Int) : Loan

}