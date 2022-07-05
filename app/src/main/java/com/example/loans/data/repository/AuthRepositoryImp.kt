package com.example.loans.data.repository

import com.example.loans.data.dataSource.local.setting.SettingsDataSource
import com.example.loans.data.dataSource.remote.authorization.AuthDataSource
import com.example.loans.data.util.BaseErrorHandler
import com.example.loans.di.qualifiers.IoDispatcher
import com.example.loans.domain.entities.AuthModel
import com.example.loans.domain.entities.ResponseResult
import com.example.loans.domain.entities.UserEntity
import com.example.loans.domain.repository.AuthRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val settingsDataSource: SettingsDataSource,
    private val errorHandlerImpl: BaseErrorHandler,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun login(authModel: AuthModel): ResponseResult<String> =
        withContext(ioDispatcher) {
            supervisorScope {
                try {
                    val result = async { authDataSource.logIn(authModel = authModel) }
                    settingsDataSource.saveToken(token = result.await())
                    ResponseResult.Success(data = result.await())
                } catch (throwable: Throwable) {
                    ResponseResult.Error(error = errorHandlerImpl.get(throwable = throwable))
                }
            }
        }

    override suspend fun registration(authModel: AuthModel): ResponseResult<UserEntity> =
        withContext(ioDispatcher) {
            supervisorScope {
                try {
                    val result = async { authDataSource.registration(authModel = authModel) }
                    ResponseResult.Success(data = result.await())
                } catch (throwable: Throwable) {
                    ResponseResult.Error(error = errorHandlerImpl.get(throwable = throwable))
                }
            }
        }

    override fun isAuthorized(): Boolean =
        settingsDataSource.getToken() != null
    

    override fun logout(): Boolean {
        settingsDataSource.clearToken()
        return settingsDataSource.getToken() == null
    }



}