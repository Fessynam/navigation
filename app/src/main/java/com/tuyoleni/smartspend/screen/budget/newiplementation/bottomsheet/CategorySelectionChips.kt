package com.example.smartspend.screen.budget.newiplementation.bottomsheet

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CategorySelectionChips(
    onCategorySelected: (String) -> Unit,
    selectedCategory: String,
    onNewCategoryChange: (String) -> Unit,
    newCategory: String,
    categories: List<String>
) {
    LazyRow {
        items(items = categories, key = { it + categories.indexOf(it).toString() }) { category ->
            InputChip(selected = category == selectedCategory, onClick = {
                if (category == selectedCategory) {
                    onCategorySelected("")
                    onNewCategoryChange("")
                } else {
                    onCategorySelected(category)
                    onNewCategoryChange("")
                }
            }, label = { Text(text = category) }, modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    TextField(value = newCategory,
        onValueChange = {
            if (selectedCategory.isNotEmpty()) {
                // Do nothing, input is disabled
            } else {
                onNewCategoryChange(it)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp)),
        enabled = selectedCategory.isEmpty(),
        placeholder = {
            Text("Add new category")
        })
}