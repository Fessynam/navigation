package com.example.navigation.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.navigation.components.elements.EarningSpendingSelector
import com.example.navigation.components.elements.cards.TransactionCard
import com.example.navigation.components.vico.linechart.EarningSpendingChart
import com.example.navigation.data.calculateMonthlyEarnings
import com.example.navigation.data.calculateMonthlySpending
import com.example.navigation.data.earnings.earnings
import com.example.navigation.data.spending.spending

@SuppressLint("DefaultLocale", "NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val selectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    "Account Details", fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            }, Modifier.background(Color.Transparent)
        )
    }) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
//            val backgroundBrush = when (selectedIndex.intValue) {
//                0 -> Brush.radialGradient(
//                    colors = listOf(Color.Green.copy(alpha = 0.1f), Color.Transparent)
//                )
//
//                else -> Brush.radialGradient(
//                    colors = listOf(Color.Red.copy(alpha = 0.1f), Color.Transparent)
//                )
//            }
//            Box(
//                modifier = Modifier
//                    .size(400.dp)
//                    .background(backgroundBrush)
//                    .align(Alignment.BottomStart)
//            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding())
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    val amount = 0.00
                    val formattedAmount = "N$ %.2f".format(amount)

                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text("Total Balance", fontSize = 14.sp)
                        Text(formattedAmount, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        EarningSpendingSelector(selectedIndex)
                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                item {
                    Text("Earning and Spending", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(14.dp))
                    ) {
                        //TODO: Implement the data visualization
                        val monthlyEarnings = calculateMonthlyEarnings(earnings)
                        val monthlySpending = calculateMonthlySpending(spending)
                        EarningSpendingChart(earnings = monthlyEarnings, spending = monthlySpending)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    val title = if (selectedIndex.intValue == 0) "Earnings" else "Spending"

                    Text("Recent $title", fontSize = 14.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    Column {
                        if (selectedIndex.intValue == 0) {
                            earnings.forEach { earning ->
                                TransactionCard(
                                    date = earning.date,
                                    category = earning.category,
                                    amount = earning.amount,
                                    type = 0
                                )
                            }
                        } else {
                            spending.forEach { spending ->
                                TransactionCard(
                                    date = spending.date,
                                    category = spending.category,
                                    amount = spending.amount,
                                    type = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen()
}
