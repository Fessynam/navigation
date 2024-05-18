package com.example.smartspend.screen.budget.newiplementation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.smartspend.data.budget.Budget
import com.example.smartspend.data.spending.spending

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetScreenContent(navController: NavController) {
//    val sheetState = rememberModalBottomSheetState()
//    val scope = rememberCoroutineScope()
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