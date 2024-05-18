package com.example.smartspend.screen.budget.newiplementation.bottomsheet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartspend.data.budget.Budget

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetCreationBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onCategorySelected: (String) -> Unit,
    selectedCategory: String,
    onNewCategoryChange: (String) -> Unit,
    newCategory: String,
    onThreshHoldChange: (Int) -> Unit,
    threshHold: Int,
    categories: MutableList<String>,
    budgetData: MutableList<Budget>
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            CategorySelectionChips(
                onCategorySelected = onCategorySelected,
                selectedCategory = selectedCategory,
                onNewCategoryChange = onNewCategoryChange,
                newCategory = newCategory,
                categories = categories
            )
            Spacer(modifier = Modifier.height(16.dp))
            ThreshHoldInput(
                onThreshHoldChange = onThreshHoldChange,
                threshHold = threshHold,
                onCreateBudget = {
                    createBudget(
                        selectedCategory = selectedCategory,
                        newCategory = newCategory,
                        threshHold = threshHold,
                        categories = categories,
                        budgetData = budgetData
                    )
                    onDismissRequest()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}