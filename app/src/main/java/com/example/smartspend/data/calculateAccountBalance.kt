package com.example.smartspend.data

import com.example.smartspend.data.earnings.Earnings
import com.example.smartspend.data.spending.Spending

fun calculateAccountBalance(spendings: List<Spending>, earnings: List<Earnings>): Int {
    val totalSpending = spendings.sumOf { it.amount }
    val totalEarnings = earnings.sumOf { it.amount }
    return totalEarnings - totalSpending
}