package com.tuyoleni.smartspend.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.tuyoleni.smartspend.data.earnings.Earnings
import com.tuyoleni.smartspend.data.earnings.MonthlyEarning

@RequiresApi(Build.VERSION_CODES.O)
fun calculateMonthlyEarnings(earnings: List<Earnings>): List<MonthlyEarning> {
    val monthlyEarnings = mutableMapOf<Pair<Int, Int>, Float>()

    for (earning in earnings) {
        val monthYear = Pair(earning.date.monthValue, earning.date.year)
        monthlyEarnings[monthYear] = monthlyEarnings.getOrDefault(monthYear, 0f) + earning.amount
    }

    return monthlyEarnings.toList().sortedBy { it.first.second * 100 + it.first.first }
        .map { (monthYear, earning) -> MonthlyEarning(monthYear.second, monthYear.first, earning) }
}