package com.example.smartspend.screen.budget.newiplementation.bottomsheet

import android.os.Build
import androidx.annotation.RequiresApi
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.data.spending.spending
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun createBudget(
    selectedCategory: String,
    newCategory: String,
    threshHold: Int,
    categories: MutableList<String>,
    budgetData: MutableList<Budget>
) {
    var newBudgets = mutableListOf<Budget>()
    val category = if (selectedCategory.isNotEmpty()) {
        selectedCategory
    } else if (newCategory.isNotEmpty()) {
        newCategory
    } else {
        ""
    }

    if (category.isNotEmpty()) {
        newBudgets = spending.map {
            Budget(
                threshHold = threshHold,
                created = LocalDate.now(),
                category = category
            )
        }.distinctBy { it.category }.toMutableList()
    }

    if (selectedCategory.isNotEmpty()) {
        categories.add(selectedCategory)
    } else if (newCategory.isNotEmpty()) {
        categories.add(newCategory)
    }

    budgetData.addAll(newBudgets)

    budgetData.forEach { budget ->
        println("Category: ${budget.category}, Threshold: ${budget.threshHold}, Created: ${budget.created}")
    }
}