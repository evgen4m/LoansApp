package com.example.loans.domain.repository

import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.LoanConditions
import com.example.loans.domain.entities.LoanRequest
import com.example.loans.domain.entities.ResponseResult

interface LoanRepository {

    suspend fun loadConditions() : ResponseResult<LoanConditions>

    suspend fun request(loanRequest: LoanRequest) : ResponseResult<Loan>

    suspend fun loadAllLoan() : ResponseResult<List<Loan>?>

    suspend fun get(id: Int) : ResponseResult<Loan>

}