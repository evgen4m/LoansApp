package com.example.loans.domain.repository

import com.example.loans.domain.entities.AuthModel
import com.example.loans.domain.entities.ResponseResult
import com.example.loans.domain.entities.UserEntity

interface AuthRepository {

    suspend fun login(authModel: AuthModel) : ResponseResult<String>

    suspend fun registration(authModel: AuthModel) : ResponseResult<UserEntity>

    fun isAuthorized() : Boolean

    fun logout() : Boolean

}