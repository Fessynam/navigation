package com.tuyoleni.smartspend.screen.budget.newiplementation

import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.tuyoleni.smartspend.FireStoreRepository
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.data.spending.spending
@RequiresApi(O)
@Composable
fun BudgetScreenContent(navController: NavController) {
    val (showBottomSheet, setShowBottomSheet) = remember { mutableStateOf(false) }
    val (selectedCategory, setSelectedCategory) = remember { mutableStateOf("") }
    val (newCategory, setNewCategory) = remember { mutableStateOf("") }
    val (threshHold, setThreshHold) = remember { mutableIntStateOf(0) }
    val categories = remember {
        mutableStateListOf<String>().apply {
            addAll(spending.map { it.category }.distinct())
        }
    }
    val uniqueCategories = categories.distinct()
    val budgetData = remember {
        mutableStateListOf<Budget>()
    }

    LaunchedEffect(Unit) {
        FireStoreRepository.getBudget { budgets ->
            budgetData.clear()
            budgetData.addAll(budgets as Collection<Budget>)
        }
    }

    BudgetScreenScaffold(
        navController = navController,
        showBottomSheet = showBottomSheet,
        onShowBottomSheetChange = setShowBottomSheet,
        onCategorySelected = setSelectedCategory,
        selectedCategory = selectedCategory,
        onNewCategoryChange = setNewCategory,
        newCategory = newCategory,
        onThreshHoldChange = setThreshHold,
        threshHold = threshHold,
        categories = uniqueCategories.toMutableList(),
        budgetData = budgetData
    )
}