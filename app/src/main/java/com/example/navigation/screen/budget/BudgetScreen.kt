package com.example.navigation.screen.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.navigation.components.elements.cards.BudgetCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = navController.currentBackStackEntry?.destination?.route ?: "Nonexistent",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier.background(Color.Transparent)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            BudgetCards(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding())
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BudgetCards(
    modifier: Modifier = Modifier
) {
    val categories = listOf("Food", "Transport", "Entertainment")
    val swipeDirections = setOf(
        SwipeToDismissBoxValue.StartToEnd,
        SwipeToDismissBoxValue.EndToStart
    )

    LazyColumn(
        modifier = modifier
    ) {
        items(categories) { category ->
            BudgetCardItem(
                category = category,
                swipeDirections = swipeDirections
            )
        }
    }
}

@Preview
@Composable
private fun BudgetScreenPreview() {
    val navController = rememberNavController()
    BudgetScreen(navController)
}