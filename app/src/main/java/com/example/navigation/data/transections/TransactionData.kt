package com.example.navigation.data.transections

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
val transactions = listOf(
    Transaction(LocalDate.of(2024, 1, 1), 1000f, 0f),
    Transaction(LocalDate.of(2024, 1, 2), 0f, 300f),
    Transaction(LocalDate.of(2024, 1, 3), 0f, 200f),
    Transaction(LocalDate.of(2024, 1, 4), 1200f, 0f),
    Transaction(LocalDate.of(2024, 1, 5), 0f, 400f),
    Transaction(LocalDate.of(2024, 1, 6), 1400f, 0f),
    Transaction(LocalDate.of(2024, 2, 1), 2000f, 0f),
    Transaction(LocalDate.of(2024, 2, 2), 0f, 600f),
    Transaction(LocalDate.of(2024, 2, 3), 0f, 400f),
    Transaction(LocalDate.of(2024, 3, 1), 1500f, 0f),
    Transaction(LocalDate.of(2024, 3, 2), 0f, 500f),
    Transaction(LocalDate.of(2024, 3, 3), 0f, 300f),
    Transaction(LocalDate.of(2024, 4, 1), 1800f, 0f),
    Transaction(LocalDate.of(2024, 4, 2), 0f, 700f),
    Transaction(LocalDate.of(2024, 4, 3), 0f, 400f),
    Transaction(LocalDate.of(2024, 5, 1), 2200f, 0f),
    Transaction(LocalDate.of(2024, 5, 2), 0f, 800f),
    Transaction(LocalDate.of(2024, 5, 3), 0f, 600f),
    Transaction(LocalDate.of(2024, 6, 1), 2500f, 0f),
    Transaction(LocalDate.of(2024, 6, 2), 0f, 900f),
    Transaction(LocalDate.of(2024, 6, 3), 0f, 500f),
    Transaction(LocalDate.of(2024, 7, 1), 2000f, 0f),
    Transaction(LocalDate.of(2024, 7, 2), 0f, 700f),
    Transaction(LocalDate.of(2024, 7, 3), 0f, 300f),
    Transaction(LocalDate.of(2024, 8, 1), 1800f, 0f),
    Transaction(LocalDate.of(2024, 8, 2), 0f, 600f),
    Transaction(LocalDate.of(2024, 8, 3), 0f, 400f),
    Transaction(LocalDate.of(2024, 9, 1), 1500f, 0f),
    Transaction(LocalDate.of(2024, 9, 2), 0f, 500f),
    Transaction(LocalDate.of(2024, 9, 3), 0f, 300f),
    Transaction(LocalDate.of(2024, 10, 1), 2000f, 0f),
    Transaction(LocalDate.of(2024, 10, 2), 0f, 700f),
    Transaction(LocalDate.of(2024, 10, 3), 0f, 400f),
    Transaction(LocalDate.of(2024, 11, 1), 2200f, 0f),
    Transaction(LocalDate.of(2024, 11, 2), 0f, 800f),
    Transaction(LocalDate.of(2024, 11, 3), 0f, 600f),
    Transaction(LocalDate.of(2024, 12, 1), 2500f, 0f),
    Transaction(LocalDate.of(2024, 12, 2), 0f, 900f),
    Transaction(LocalDate.of(2024, 12, 3), 0f, 500f),
    Transaction(LocalDate.of(2025, 1, 1), 2000f, 0f),
    Transaction(LocalDate.of(2025, 1, 2), 0f, 700f),
    Transaction(LocalDate.of(2025, 1, 3), 0f, 300f),
    Transaction(LocalDate.of(2025, 2, 1), 1800f, 0f),
    Transaction(LocalDate.of(2025, 2, 2), 0f, 600f),
    Transaction(LocalDate.of(2025, 2, 3), 0f, 400f),
    Transaction(LocalDate.of(2025, 3, 1), 1500f, 0f),
    Transaction(LocalDate.of(2025, 3, 2), 0f, 500f),
    Transaction(LocalDate.of(2025, 3, 3), 0f, 300f),
    Transaction(LocalDate.of(2025, 4, 1), 2000f, 0f),
    Transaction(LocalDate.of(2025, 4, 2), 0f, 700f),
    Transaction(LocalDate.of(2025, 4, 3), 0f, 400f),
    Transaction(LocalDate.of(2025, 5, 1), 2200f, 0f),
    Transaction(LocalDate.of(2025, 5, 2), 0f, 800f),
    Transaction(LocalDate.of(2025, 5, 3), 0f, 600f),
    Transaction(LocalDate.of(2025, 6, 1), 2500f, 0f),
    Transaction(LocalDate.of(2025, 6, 2), 0f, 900f),
    Transaction(LocalDate.of(2025, 6, 3), 0f, 500f),
    Transaction(LocalDate.of(2025, 7, 1), 2000f, 0f),
    Transaction(LocalDate.of(2025, 7, 2), 0f, 700f),
    Transaction(LocalDate.of(2025, 7, 3), 0f, 300f),
    Transaction(LocalDate.of(2025, 8, 1), 1800f, 0f),
    Transaction(LocalDate.of(2025, 8, 2), 0f, 600f),
    Transaction(LocalDate.of(2025, 8, 3), 0f, 400f),
    Transaction(LocalDate.of(2025, 9, 1), 1500f, 0f),
    Transaction(LocalDate.of(2025, 9, 2), 0f, 500f),
    Transaction(LocalDate.of(2025, 9, 3), 0f, 300f),
    Transaction(LocalDate.of(2025, 10, 1), 2000f, 0f),
)


//Grouping data by month
@RequiresApi(Build.VERSION_CODES.O)
fun List<Transaction>.groupByMonth(): List<MonthData> {
    return this.groupBy { it.date.month }
        .map { (month, transactions) ->
            val totalEarnings = transactions.sumByDouble { it.earnings.toDouble() }.toFloat()
            val totalSpending = transactions.sumByDouble { it.spending.toDouble() }.toFloat()
            MonthData(month.toString(), totalEarnings, totalSpending)
        }
}