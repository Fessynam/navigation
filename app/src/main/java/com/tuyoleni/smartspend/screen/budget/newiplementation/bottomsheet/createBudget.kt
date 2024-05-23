package com.tuyoleni.smartspend.screen.budget.newiplementation.bottomsheet

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.tuyoleni.smartspend.FireStoreRepository
import com.tuyoleni.smartspend.data.budget.Budget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        val user = FirebaseAuth.getInstance().currentUser?.email
        val newBudget = user?.let {
            Budget(
                user = it,
                threshHold = threshHold,
                created = LocalDate.now(),
                category = selectedCategory.ifEmpty { newCategory })
        }
        CoroutineScope(Dispatchers.Default).launch {

            newBudget?.let { FireStoreRepository.addBudget(it) }
        }

        if (newCategory.isNotEmpty() && !categories.contains(newCategory)) {
            categories.add(newCategory)
        }

        println("Category: ${newBudget?.category}, Threshold: ${newBudget?.threshHold}, Created: ${newBudget?.created}, User: ${newBudget?.user}")
    } else {
        println("Please select a category or enter a new category $newCategory")
    }
}