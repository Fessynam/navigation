package com.tuyoleni.smartspend.screen.budget.newiplementation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun CreateBudgetFab(onShowBottomSheetChange: (Boolean) -> Unit) {
    FloatingActionButton(
        onClick = { onShowBottomSheetChange(true) },
        modifier = Modifier.padding(0.dp),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        elevation = FloatingActionButtonDefaults.elevation(0.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
        )
    }
}