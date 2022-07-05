package com.example.loans.data.dataSource.local.cache.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.loans.data.dataSource.local.cache.dao.LoanDao
import com.example.loans.data.dataSource.local.cache.entity.LoanRoomEntity

@Database(
    entities = [LoanRoomEntity::class],
    version = 3,
    exportSchema = true
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun itemsListDao() : LoanDao

}