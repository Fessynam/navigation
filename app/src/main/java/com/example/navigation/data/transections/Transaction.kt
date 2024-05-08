package com.example.navigation.data.transections

import java.time.LocalDate

data class Transaction(
    val date: LocalDate,
    val earnings: Float,
    val spending: Float,
)

data class MonthData(
    val month: String,
    val earnings: Float,
    val spending: Float
)
