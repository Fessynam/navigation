package com.tuyoleni.smartspend.data.budget

import java.time.LocalDate

data class Budget(
    val threshHold: Int,
    val created: LocalDate,
    val category: String,
)
