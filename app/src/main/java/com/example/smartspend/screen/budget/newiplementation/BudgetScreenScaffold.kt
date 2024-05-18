package com.example.smartspend.screen.budget.newiplementation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.smartspend.components.navigation.top.ScreenTopAppBar
import com.example.smartspend.data.budget.Budget
import com.example.smartspend.screen.budget.newiplementation.bottomsheet.BudgetCreationBottomSheet

@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreenScaffold(
    navController: NavController,
    showBottomSheet: Boolean,
    onShowBottomSheetChange: (Boolean) -> Unit,
    onCategorySelected: (String) -> Unit,
    selectedCategory: String,
    onNewCategoryChange: (String) -> Unit,
    newCategory: String,
    onThreshHoldChange: (Int) -> Unit,
    threshHold: Int,
    categories: MutableList<String>,
    budgetData: MutableList<Budget>
) {
    Scaffold(
        topBar = { ScreenTopAppBar(title = navController.currentBackStackEntry?.destination?.route ?: "Nonexistent") },
        floatingActionButton = { CreateBudgetFab(onShowBottomSheetChange) }
    ) { padding ->
        Box(modifier = Modifier.padding(top = padding.calculateTopPadding())) {
            BudgetList(budgetData)
            if (showBottomSheet) {
                BudgetCreationBottomSheet(
                    onDismissRequest = { onShowBottomSheetChange(false) },
                    sheetState = rememberModalBottomSheetState(),
                    onCategorySelected = onCategorySelected,
                    selectedCategory = selectedCategory,
                    onNewCategoryChange = onNewCategoryChange,
                    newCategory = newCategory,
                    onThreshHoldChange = onThreshHoldChange,
                    threshHold = threshHold,
                    categories = categories,
                    budgetData = budgetData
                )
            }
        }
    }
}
