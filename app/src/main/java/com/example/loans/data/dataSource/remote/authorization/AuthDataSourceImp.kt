package com.example.loans.data.dataSource.remote.authorization

import com.example.loans.data.dataSource.remote.api.AuthApi
import com.example.loans.domain.entities.AuthModel
import javax.inject.Inject

class AuthDataSourceImp @Inject constructor(
    private val api: AuthApi,
) : AuthDataSource {

    override suspend fun logIn(authModel: AuthModel): String =
        api.logIn(authModel = authModel)


    override suspend fun registration(authModel: AuthModel) =
        api.registration(authModel = authModel)

}