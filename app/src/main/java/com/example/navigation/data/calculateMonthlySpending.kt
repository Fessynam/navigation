package com.example.navigation.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.navigation.data.spending.MonthlySpending
import com.example.navigation.data.spending.Spending

@RequiresApi(Build.VERSION_CODES.O)
fun calculateMonthlySpending(data: List<Spending>): List<MonthlySpending> {
    val monthlySpending = mutableMapOf<Int, Float>()

    for (spending in data) {
        val month = spending.date.monthValue
        monthlySpending[month] = monthlySpending.getOrDefault(month, 0f) + spending.amount
    }

    return monthlySpending.toList().sortedBy { it.first }
        .map { (month, spending) -> MonthlySpending(month, spending) }
}
