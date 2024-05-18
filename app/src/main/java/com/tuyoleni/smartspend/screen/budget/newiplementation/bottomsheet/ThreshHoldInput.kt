package com.example.smartspend.screen.budget.newiplementation.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun ThreshHoldInput(
    onThreshHoldChange: (Int) -> Unit,
    threshHold: Int,
    onCreateBudget: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        TextField(
            value = threshHold.toString(),
            onValueChange = { input ->
                if (input.isBlank()) {
                    onThreshHoldChange(0)
                } else {
                    val parsed = input.toIntOrNull()
                    if (parsed != null) {
                        onThreshHoldChange(parsed)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(0.7f)
                .clip(RoundedCornerShape(14.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = onCreateBudget,
            modifier = Modifier
                .weight(0.4f)
                .height(58.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(color = MaterialTheme.colorScheme.onPrimary)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Create Budget",
            )
        }
    }
}