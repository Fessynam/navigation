package com.example.smartspend.components.elements

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EarningSpendingSelector(selectedIndex: MutableState<Int>) {
    val options = mutableListOf("Earning", "Spending")

    MultiChoiceSegmentedButtonRow {
        options.forEachIndexed { index, option ->
            SegmentedButton(
                checked = selectedIndex.value == index,
                onCheckedChange = { checked ->
                    if (checked) {
                        selectedIndex.value = index
                    }
                },
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                )
            ) {
                Text(text = option)
            }
        }
    }
}
