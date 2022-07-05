package com.example.loans.data.dataSource.remote.api

import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.LoanConditions
import com.example.loans.domain.entities.LoanRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoanApi {

    @GET("/loans/conditions")
    suspend fun getLoanConditions() : LoanConditions

    @POST("/loans")
    suspend fun loanRequest(@Body loanRequest: LoanRequest) : Loan

    @GET("/loans/all")
    suspend fun getAllLoan() : List<Loan>

    @GET("/loans/{id}")
    suspend fun getLoanById(@Path("id") id: Int) : Loan

}