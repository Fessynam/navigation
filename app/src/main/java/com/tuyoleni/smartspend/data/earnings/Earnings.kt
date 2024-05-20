package com.tuyoleni.smartspend.data.earnings

import java.io.IOException
import java.time.LocalDate

data class Earnings(
    val date: LocalDate,
    val category: String,
    val amount: Int,
) {
    fun toMap(): Map<String, Any> {
        try {
            return mapOf(
                "date" to date.toString(), "category" to category, "amount" to amount
            )
        } catch (e: IOException) {
            throw IOException("Failed to convert spending to map${e.message}", e)
        }
    }
}


data class MonthlyEarning(
    val year: Int,
    val month: Int,
    val amount: Float
)