package com.example.loans.data.repository

import com.example.loans.data.converter.mapToLocal
import com.example.loans.data.converter.mapToRoomEntity
import com.example.loans.data.dataSource.local.cache.dataSource.LoanCacheDataSource
import com.example.loans.data.dataSource.remote.loan.LoanRemoteDataSource
import com.example.loans.data.util.BaseErrorHandler
import com.example.loans.di.qualifiers.IoDispatcher
import com.example.loans.domain.entities.*
import com.example.loans.domain.repository.LoanRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class LoanRepositoryImp @Inject constructor(
    private val loansRemoteSource: LoanRemoteDataSource,
    private val baseErrorHandler: BaseErrorHandler,
    private val loanCacheDataSource: LoanCacheDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LoanRepository {

    override suspend fun loadConditions(): ResponseResult<LoanConditions> = (ioDispatcher) {
        supervisorScope {
            try {
                val result = async { loansRemoteSource.getLoanConditions() }
                ResponseResult.Success(data = result.await())
            } catch (throwable: Throwable) {
                ResponseResult.Error(error = baseErrorHandler.get(throwable = throwable))
            }
        }
    }

    override suspend fun request(loanRequest: LoanRequest): ResponseResult<Loan> = (ioDispatcher) {
        supervisorScope {
            try {
                val result = async { loansRemoteSource.loanRequest(loanRequest = loanRequest) }
                ResponseResult.Success(data = result.await())
            } catch (throwable: Throwable) {
                ResponseResult.Error(error = baseErrorHandler.get(throwable = throwable))
            }
        }
    }

    override suspend fun loadAllLoan(): ResponseResult<List<Loan>?> = (ioDispatcher) {
        supervisorScope {
            try {
                val result = async { loansRemoteSource.getAllLoan() }
                loanCacheDataSource.saveLoanList(
                    entries = result.await().map { loan -> loan.mapToRoomEntity() })
                ResponseResult.Success(data = result.await())
            } catch (throwable: Throwable) {
                if (baseErrorHandler.get(throwable = throwable) == ErrorEntity.Network) {
                    val localResultDeferred = async { loanCacheDataSource.getAllLoan().map {
                        loanRoomEntity -> loanRoomEntity.mapToLocal()
                    } }
                    val localResult: List<Loan> = runCatching { localResultDeferred.await() }.getOrDefault(emptyList<Loan>())
                    ResponseResult.LocalData(localData = localResult, error = ErrorEntity.Network)
                } else {
                    ResponseResult.Error(error = baseErrorHandler.get(throwable = throwable))
                }
            }
        }
    }

    override suspend fun get(id: Int): ResponseResult<Loan> = (ioDispatcher) {
        supervisorScope {
            try {
                val result = async { loansRemoteSource.getLoanById(id = id) }
                ResponseResult.Success(data = result.await())
            } catch (throwable: Throwable) {
                ResponseResult.Error(error = baseErrorHandler.get(throwable = throwable))
            }
        }
    }

}