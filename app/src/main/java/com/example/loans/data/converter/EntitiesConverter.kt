package com.example.loans.data.converter

import com.example.loans.data.dataSource.local.cache.entity.LoanRoomEntity
import com.example.loans.domain.entities.Loan


fun Loan.mapToRoomEntity() : LoanRoomEntity {
    return LoanRoomEntity(
        id = this.id,
        amount = this.amount.toLong(),
        percent = this.percent,
        period = this.period,
        date = this.date,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        state = this.state
    )
}

fun LoanRoomEntity.mapToLocal() : Loan {
    return Loan(
        id = this.id,
        amount = this.amount,
        percent = this.percent,
        period = this.period,
        date = this.date,
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        state = this.state
    )
}