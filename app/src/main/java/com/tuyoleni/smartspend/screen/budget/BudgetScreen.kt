package com.tuyoleni.smartspend.screen.budget

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tuyoleni.smartspend.screen.budget.newiplementation.BudgetScreenContent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetScreen(navController: NavController) {
    BudgetScreenContent(navController)
}