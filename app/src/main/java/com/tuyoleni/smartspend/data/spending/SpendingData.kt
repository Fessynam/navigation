package com.tuyoleni.smartspend.data.spending

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
val spending: List<Spending> = listOf(
    Spending(LocalDate.of(2024, 1, 2), "Food", 1500),
    Spending(LocalDate.of(2024, 2, 2), "Shopping", 1200),
    Spending(LocalDate.of(2024, 2, 3), "Entertainment", 1500),
    Spending(LocalDate.of(2024, 3, 2), "Transportation", 1300),
    Spending(LocalDate.of(2024, 3, 3), "Utilities", 3000),
    Spending(LocalDate.of(2024, 4, 2), "Food", 1800),
    Spending(LocalDate.of(2024, 4, 3), "Shopping", 2000),
    Spending(LocalDate.of(2024, 7, 2), "Entertainment", 1500),
    Spending(LocalDate.of(2024, 8, 2), "Transportation", 1400),
    Spending(LocalDate.of(2024, 8, 3), "Utilities", 1600),
    Spending(LocalDate.of(2024, 9, 2), "Food", 1700),
    Spending(LocalDate.of(2024, 10, 2), "Shopping", 2500),
    Spending(LocalDate.of(2024, 10, 3), "Entertainment", 1700),
    Spending(LocalDate.of(2024, 11, 2), "Transportation", 1500),
    Spending(LocalDate.of(2024, 11, 3), "Utilities", 1800),

    Spending(LocalDate.of(2023, 1, 2), "Food", 10200),
    Spending(LocalDate.of(2023, 2, 2), "Shopping", 6000),
    Spending(LocalDate.of(2023, 2, 3), "Entertainment", 1700),
    Spending(LocalDate.of(2023, 3, 2), "Transportation", 1600),
    Spending(LocalDate.of(2023, 3, 3), "Utilities", 1800),
    Spending(LocalDate.of(2023, 4, 2), "Food", 1300),
    Spending(LocalDate.of(2023, 4, 3), "Shopping", 1500),
    Spending(LocalDate.of(2023, 7, 2), "Entertainment", 1900),
    Spending(LocalDate.of(2023, 8, 2), "Transportation", 1700),
    Spending(LocalDate.of(2023, 8, 3), "Utilities", 2000),
)