package com.example.navigation.data.budget

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class Budget(
    val threshHold: Int,
    val created: LocalDate,
    val category: String,
)
