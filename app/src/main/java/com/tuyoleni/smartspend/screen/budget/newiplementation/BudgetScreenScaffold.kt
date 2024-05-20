package com.tuyoleni.smartspend.screen.budget.newiplementation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.screen.budget.newiplementation.bottomsheet.BudgetCreationBottomSheet

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
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = navController.currentBackStackEntry?.destination?.route ?: "Nonexistent",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                CreateBudgetFab(onShowBottomSheetChange)
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
        )
    }) { padding ->
        Box(
            modifier = Modifier.padding(top = padding.calculateTopPadding())
        ){
            BudgetList(budgetData = budgetData)
        }
    }

    if (showBottomSheet) {
        BudgetCreationBottomSheet(
            onDismissRequest = { onShowBottomSheetChange(false) },
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
