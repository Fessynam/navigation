package com.example.navigation.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.navigation.data.earnings.Earnings
import com.example.navigation.data.earnings.MonthlyEarning

@RequiresApi(Build.VERSION_CODES.O)
fun calculateMonthlyEarnings(earnings: List<Earnings>): List<MonthlyEarning> {
    val monthlyEarnings = mutableMapOf<Int, Float>()

    for (earning in earnings) {
        val month = earning.date.monthValue
        monthlyEarnings[month] = monthlyEarnings.getOrDefault(month, 0f) + earning.amount
    }

    return monthlyEarnings.toList().sortedBy { it.first }
        .map { (month, earning) -> MonthlyEarning(month, earning) }
}