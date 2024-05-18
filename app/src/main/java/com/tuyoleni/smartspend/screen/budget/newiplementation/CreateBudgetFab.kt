package com.tuyoleni.smartspend.screen.budget.newiplementation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CreateBudgetFab(onShowBottomSheetChange: (Boolean) -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text("Create Budget") },
        icon = { Icon(Icons.Filled.Add, contentDescription = null) },
        onClick = { onShowBottomSheetChange(true) },
    )
}