package com.tuyoleni.smartspend.data.budget

import java.io.IOException
import java.time.LocalDate


data class Budget(
    val threshHold: Int,
    val created: LocalDate,
    val category: String,
) {
    @Throws(IOException::class)
    fun toMap(): Map<String, Any> {
        try {
            return mapOf(
                "threshHold" to threshHold,
                "created" to created.toString(),
                "category" to category
            )
        } catch (e: Exception) {
            throw IOException("Error converting budget to map: ${e.message}", e)
        }
    }
}