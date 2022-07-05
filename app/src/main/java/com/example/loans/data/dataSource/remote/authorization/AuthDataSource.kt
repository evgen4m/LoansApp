package com.example.loans.data.dataSource.remote.authorization

import com.example.loans.domain.entities.AuthModel
import com.example.loans.domain.entities.UserEntity

interface AuthDataSource {

    suspend fun logIn(authModel: AuthModel) : String

    suspend fun registration(authModel: AuthModel) : UserEntity

}