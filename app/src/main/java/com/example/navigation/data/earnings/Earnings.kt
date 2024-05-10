package com.example.navigation.data.earnings

import java.time.LocalDate

data class Earnings(
    val date: LocalDate,
    val category: String,
    val amount: Int,
)

data class MonthlyEarning(
    val year: Int,
    val month: Int,
    val amount: Float
)