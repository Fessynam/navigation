@file:Suppress("DEPRECATION")

package com.example.navigation.components.elements.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetCardList(
    category: String,
    modifier: Modifier = Modifier,
) {
    val swipeDirection = setOf(SwipeToDismissBoxValue.EndToStart)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        item {
            BudgetCardItem(
                category = category,
                swipeDirections = swipeDirection,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetCardItem(
    category: String,
    swipeDirections: Set<SwipeToDismissBoxValue>,
    modifier: Modifier = Modifier,
) {
    SwipeToDismiss(
        state = rememberSwipeToDismissBoxState(),
        background = {
            SwipeBackground()
        },
        dismissContent = {
            CategoryContent(category = category, modifier = modifier)
        },
        modifier = modifier,
        directions = swipeDirections
    )
}

@Composable
private fun SwipeBackground() {
    Box(
        Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.2f)),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            modifier = Modifier.padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Delete Category", color = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun CategoryContent(category: String, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.Black)
        ) {
            // Display the chart content here
            // SimpleBarChart(data = listOf(100f, 200f, 300f))
        }
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Indicator for each category
                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(category, fontSize = 16.sp)
            }
        }
    }
}

@Preview
@Composable
private fun BudgetCardPreview() {
    BudgetCardList(category = "Food")
}