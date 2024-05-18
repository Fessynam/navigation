package com.tuyoleni.smartspend.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.tuyoleni.smartspend.data.spending.MonthlySpending
import com.tuyoleni.smartspend.data.spending.Spending

@RequiresApi(Build.VERSION_CODES.O)
fun calculateMonthlySpending(data: List<Spending>): List<MonthlySpending> {
    val monthlySpending = mutableMapOf<Pair<Int, Int>, Float>()

    for (spending in data) {
        val monthYear = Pair(spending.date.monthValue, spending.date.year)
        monthlySpending[monthYear] = monthlySpending.getOrDefault(monthYear, 0f) + spending.amount
    }

    return monthlySpending.toList().sortedBy { it.first.second * 100 + it.first.first }
        .map { (monthYear, earning) -> MonthlySpending(monthYear.second, monthYear.first, earning) }
}
