package com.example.loans.data.dataSource.local.cache.dataSource

import com.example.loans.data.dataSource.local.cache.entity.LoanRoomEntity

interface LoanCacheDataSource {

    suspend fun getAllLoan() : List<LoanRoomEntity>

    suspend fun saveLoanList(entries: List<LoanRoomEntity>)

}