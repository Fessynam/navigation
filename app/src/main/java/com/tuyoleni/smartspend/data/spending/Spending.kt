package com.tuyoleni.smartspend.data.spending

import java.io.IOException
import java.time.LocalDate

data class Spending(
    val user: String,
    val date: LocalDate,
    val category: String,
    val amount: Int,
) {
    fun toMap(): Map<String, Any> {
        try {
            return mapOf(
                "user" to user,
                "date" to date.toString(),
                "category" to category,
                "amount" to amount
            )
        } catch (e: IOException) {
            throw IOException("Failed to convert spending to map${e.message}", e)
        }
    }
}

data class MonthlySpending(
    val year: Int,
    val month: Int,
    val amount: Int
)
