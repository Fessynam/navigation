package com.tuyoleni.smartspend.screen.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuyoleni.smartspend.FireStoreRepository
import com.tuyoleni.smartspend.components.elements.EarningSpendingSelector
import com.tuyoleni.smartspend.components.elements.cards.TransactionCard
import com.tuyoleni.smartspend.components.navigation.top.ScreenTopAppBar
import com.tuyoleni.smartspend.components.vico.linechart.EarningSpendingChart
import com.tuyoleni.smartspend.data.calculateAccountBalance
import com.tuyoleni.smartspend.data.calculateMonthlyEarnings
import com.tuyoleni.smartspend.data.calculateMonthlySpending
import com.tuyoleni.smartspend.data.earnings.MonthlyEarning
import com.tuyoleni.smartspend.data.earnings.earnings
import com.tuyoleni.smartspend.data.spending.MonthlySpending
import com.tuyoleni.smartspend.data.spending.spending
@SuppressLint("DefaultLocale", "NewApi")
@Composable
fun HomeScreen() {
    val selectedIndex = remember { mutableIntStateOf(0) }
    Scaffold(topBar = {
        ScreenTopAppBar("Account Details")
    }) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding())
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    val accountBalance =
                        calculateAccountBalance(spendings = spending, earnings = earnings)
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Total Balance", fontSize = 14.sp)
                        Text(
                            accountBalance.toString(),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
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
                            .clip(RoundedCornerShape(14.dp))
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        var earningSpendingData by remember {
                            mutableStateOf<Pair<List<MonthlyEarning>, List<MonthlySpending>>?>(null)
                        }

                        LaunchedEffect(Unit) {
                            earningSpendingData = loadEarningSpendingData()
                        }

                        if (earningSpendingData == null) {
                            // Display a loading indicator while data is being fetched
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        } else {
                            val (earnings, spending) = earningSpendingData!!
                            if (earnings.isEmpty() || spending.isEmpty()) {
                                Text("No data available", modifier = Modifier.align(Alignment.Center))
                            } else {
                                EarningSpendingChart(earnings = earnings, spending = spending)
                            }
                        }
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

@RequiresApi(Build.VERSION_CODES.O)
suspend fun loadEarningSpendingData(): Pair<List<MonthlyEarning>, List<MonthlySpending>> {
    return try {
        val earnings = FireStoreRepository.getEarnings()
        val spending = FireStoreRepository.getSpending()
        val monthlySpending = calculateMonthlySpending(spending)
        val monthlyEarning = calculateMonthlyEarnings(earnings)
        monthlyEarning to monthlySpending
    } catch (e: Exception) {
        // Handle error fetching data
        e.printStackTrace()
        emptyList<MonthlyEarning>() to emptyList<MonthlySpending>()
    }
}

@Preview(showBackground = true, wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
