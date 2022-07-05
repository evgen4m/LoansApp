package com.example.loans.data.dataSource.local.cache.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.loans.data.dataSource.local.cache.entity.LoanRoomEntity.Companion.LOAN_ENTITY_TABLE
import com.example.loans.domain.entities.LoanState

@Entity(tableName = LOAN_ENTITY_TABLE)
data class LoanRoomEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "amount")
    val amount: Long,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "firstname")
    val firstName: String,

    @ColumnInfo(name = "lastname")
    val lastName: String,

    @ColumnInfo(name = "percent")
    val percent: Double,

    @ColumnInfo(name = "period")
    val period: Int,

    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,

    @ColumnInfo(name = "state")
    val state: LoanState
) {

    companion object {
        const val LOAN_ENTITY_TABLE = "LoanEntityTable"
    }

}