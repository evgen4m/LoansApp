package com.example.loans.domain.entities

import java.io.Serializable

data class LoanConditions(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
) : Serializable
