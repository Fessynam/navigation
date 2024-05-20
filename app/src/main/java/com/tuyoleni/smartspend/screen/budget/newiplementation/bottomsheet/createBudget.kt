package com.tuyoleni.smartspend.screen.budget.newiplementation.bottomsheet

import android.os.Build
import androidx.annotation.RequiresApi
import com.tuyoleni.smartspend.FireStoreRepository
import com.tuyoleni.smartspend.data.budget.Budget
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun createBudget(
    selectedCategory: String,
    newCategory: String,
    threshHold: Int,
    categories: MutableList<String>,
    budgetData: MutableList<Budget>
) {
    if (selectedCategory.isNotEmpty() || newCategory.isNotEmpty()) {
        val newBudget = Budget(threshHold = threshHold,
            created = LocalDate.now(),
            category = selectedCategory.ifEmpty { newCategory })
        FireStoreRepository.addBudget(newBudget)

        if (newCategory.isNotEmpty() && !categories.contains(newCategory)) {
            categories.add(newCategory)
        }

        println("Category: ${newBudget.category}, Threshold: ${newBudget.threshHold}, Created: ${newBudget.created}")
    } else {
        println("Please select a category or enter a new category $newCategory")
    }
}