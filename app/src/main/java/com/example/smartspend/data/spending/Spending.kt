package com.example.smartspend.data.spending

import java.time.LocalDate

data class Spending(
    val date: LocalDate,
    val category: String,
    val amount: Int,
)

data class MonthlySpending(
    val year: Int,
    val month: Int,
    val amount: Float
)
