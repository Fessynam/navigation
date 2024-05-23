package com.tuyoleni.smartspend.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.tuyoleni.smartspend.data.spending.MonthlySpending
import com.tuyoleni.smartspend.data.spending.Spending
import kotlinx.coroutines.flow.Flow

@RequiresApi(Build.VERSION_CODES.O)
suspend fun calculateMonthlySpending(data: List<Spending>): List<MonthlySpending> {
    val monthlySpending = mutableMapOf<Pair<Int, Int>, Float>()

    for (spending in data) {
        val monthYear = Pair(spending.date.monthValue, spending.date.year)
        monthlySpending[monthYear] = monthlySpending.getOrDefault(monthYear, 0f) + spending.amount
    }

    return monthlySpending.entries.map { (monthYear, amount) ->
        MonthlySpending(monthYear.second, monthYear.first, amount.toInt())
    }.sortedBy { it.year * 100 + it.month }
}
