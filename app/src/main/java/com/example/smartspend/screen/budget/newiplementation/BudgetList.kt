package com.example.smartspend.screen.budget.newiplementation

import BudgetItem
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartspend.components.elements.cards.SwipeToDeleteContainer
import com.example.smartspend.data.budget.Budget
import com.example.smartspend.data.spending.spending

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetList(budgetData: MutableList<Budget>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(items = budgetData, key = { "${it.category}_${it.created}" }) { budget ->
            SwipeToDeleteContainer(item = budget, onDelete = { budgetData.remove(budget) }) {
                Column {
                    Text(budget.category, style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    val spendingData = spending.filter { it.category == budget.category }
                    BudgetItem(budget = budget, spendingData = spendingData)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(budget.created.toString())
                    Text(budget.threshHold.toString())
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
