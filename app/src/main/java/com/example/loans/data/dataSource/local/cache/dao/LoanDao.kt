package com.example.loans.data.dataSource.local.cache.dao

import androidx.room.*
import com.example.loans.data.dataSource.local.cache.entity.LoanRoomEntity

@Dao
interface LoanDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllLoan(entries: List<LoanRoomEntity>)

    @Query("SELECT * FROM ${LoanRoomEntity.LOAN_ENTITY_TABLE}")
    suspend fun getAllLoan() : List<LoanRoomEntity>

}