package com.tuyoleni.smartspend.screen.budget.newiplementation

import BudgetItem
import android.os.Build.VERSION_CODES.O
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuyoleni.smartspend.FireStoreRepository
import com.tuyoleni.smartspend.components.elements.cards.SwipeToDeleteContainer
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.data.spending.spending
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@RequiresApi(O)
@Composable
fun BudgetList(budgetData: MutableList<Budget>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(items = budgetData, key = { "${it.category}_${it.created}" }) { budget ->
            SwipeToDeleteContainer(item = budget, onDelete = {
                budgetData.remove(budget)
                CoroutineScope(Dispatchers.Default).launch {

                    FireStoreRepository.deleteBudget(budget)
                }
            }) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    Modifier
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .fillParentMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(
                        budget.category,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    val spendingData = spending.filter { it.category == budget.category }
                    BudgetItem(budget = budget, spendingData = spendingData)

                    Spacer(modifier = Modifier.height(16.dp))


                    Text(
                        "Budget Created ${budget.created.toString()}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        "N$ ${budget.threshHold.toString()}",
                        style = MaterialTheme.typography.bodySmall
                    )

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
