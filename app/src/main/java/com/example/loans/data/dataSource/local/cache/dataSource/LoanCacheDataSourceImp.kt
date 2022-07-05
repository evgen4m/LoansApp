package com.example.loans.data.dataSource.local.cache.dataSource


import com.example.loans.data.dataSource.local.cache.dao.LoanDao
import com.example.loans.data.dataSource.local.cache.entity.LoanRoomEntity
import javax.inject.Inject

class LoanCacheDataSourceImp @Inject constructor(
    private val loanDao: LoanDao
) : LoanCacheDataSource {


    override suspend fun getAllLoan(): List<LoanRoomEntity> =
        loanDao.getAllLoan()

    override suspend fun saveLoanList(entries: List<LoanRoomEntity>) =
        loanDao.saveAllLoan(entries = entries)

}