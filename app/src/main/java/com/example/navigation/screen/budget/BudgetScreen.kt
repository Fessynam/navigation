@file:Suppress("NAME_SHADOWING")

package com.example.navigation.screen.budget

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.navigation.components.elements.cards.SwipeToDeleteContainer
import com.example.navigation.data.budget.Budget
import com.example.navigation.data.spending.spending
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetScreen(navController: NavController) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }
    var newCategory by remember { mutableStateOf("") }
    var threshHold by remember { mutableIntStateOf(0) }
    val categories = remember {
        mutableStateListOf<String>().apply {
            addAll(spending.map { it.category }.distinct())
        }
    }

    val budgetData = remember {
        mutableStateListOf<Budget>()
    }

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
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Create Budget") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    showBottomSheet = true
                },
                modifier = Modifier.padding(4.dp)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(top = padding.calculateTopPadding())) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(items = budgetData, key = { it.category }) { category ->
                    SwipeToDeleteContainer(
                        item = category,
                        onDelete = { budgetData.remove(category) }
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color.Black)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(text = category.category)
                                Text(text = category.created.toString())
                                Text(text = category.threshHold.toString())
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    Column(Modifier.padding(horizontal = 16.dp)) {
                        LazyRow {
                            items(items = categories, key = { it }) { category ->
                                InputChip(
                                    selected = category == selectedCategory,
                                    onClick = {
                                        if (category == selectedCategory) {
                                            selectedCategory = ""
                                        } else {
                                            selectedCategory = category
                                            newCategory = ""
                                        }
                                    },
                                    label = { Text(text = category) },
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newCategory,
                            onValueChange = {
                                if (selectedCategory.isNotEmpty()) {
                                    // Do nothing, input is disabled
                                } else {
                                    newCategory = it
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp)),
                            enabled = selectedCategory.isEmpty(),
                            placeholder = { Text("Add new category") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            TextField(
                                value = threshHold.toString(),
                                onValueChange = { input ->
                                    if (input.isBlank()) {
                                        threshHold = 0
                                    } else {
                                        val parsed = input.toIntOrNull()
                                        if (parsed != null) {
                                            threshHold = parsed
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
                                onClick = {
                                    var newBudgets = mutableListOf<Budget>()
                                    val category = if (selectedCategory.isNotEmpty()) {
                                        selectedCategory
                                    } else if (newCategory.isNotEmpty()) {
                                        newCategory
                                    } else {
                                        ""
                                    }

                                    if (category.isNotEmpty()) {
                                        newBudgets = spending.map {
                                            Budget(
                                                threshHold = threshHold,
                                                created = LocalDate.now(),
                                                category = category
                                            )
                                        }.distinctBy { it.category }.toMutableList()
                                    }

                                    if (selectedCategory.isNotEmpty()) {
                                        categories.add(selectedCategory)
                                    } else if (newCategory.isNotEmpty()) {
                                        categories.add(newCategory)
                                    }

                                    budgetData.addAll(newBudgets)

                                    budgetData.forEach { budget ->
                                        println("Category: ${budget.category}, Threshold: ${budget.threshHold}, Created: ${budget.created}")
                                    }

                                    selectedCategory = ""
                                    newCategory = ""
                                    threshHold = 0

                                    showBottomSheet = false
                                },
                                modifier = Modifier
                                    .weight(0.3f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(color = MaterialTheme.colorScheme.onPrimary)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Create Budget",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .height(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun BudgetScreenPreview() {
    val navController = rememberNavController()
    BudgetScreen(navController)
}