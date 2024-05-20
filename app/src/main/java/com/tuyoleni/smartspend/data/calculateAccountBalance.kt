package com.tuyoleni.smartspend.data

import com.tuyoleni.smartspend.data.earnings.Earnings
import com.tuyoleni.smartspend.data.spending.Spending

fun calculateAccountBalance(spendings: List<Spending>, earnings: List<Earnings>): Int {
    val totalSpending = spendings.sumOf { it.amount }
    val totalEarnings = earnings.sumOf { it.amount }
    return totalEarnings - totalSpending
}