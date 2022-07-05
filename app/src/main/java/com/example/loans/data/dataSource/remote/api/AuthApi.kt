package com.example.loans.data.dataSource.remote.api

import com.example.loans.domain.entities.AuthModel
import com.example.loans.domain.entities.UserEntity
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    suspend fun logIn(@Body authModel: AuthModel) : String

    @POST("/registration")
    suspend fun registration(@Body authModel: AuthModel) : UserEntity

}