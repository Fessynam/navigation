package com.tuyoleni.smartspend.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuyoleni.smartspend.components.elements.EarningSpendingSelector
import com.tuyoleni.smartspend.components.elements.cards.TransactionCard
import com.tuyoleni.smartspend.components.navigation.top.ScreenTopAppBar
import com.tuyoleni.smartspend.components.vico.linechart.EarningSpendingChart
import com.tuyoleni.smartspend.data.calculateAccountBalance
import com.tuyoleni.smartspend.data.earnings.earnings
import com.tuyoleni.smartspend.data.spending.spending
import kotlinx.coroutines.launch

@SuppressLint("DefaultLocale", "NewApi")
@Composable
fun HomeScreen() {
    val selectedIndex = remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            isLoading = false
        }
    }

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
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        } else {
                            EarningSpendingChart(earnings = earnings, spending = spending)
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
                            spending.forEach { spend ->
                                TransactionCard(
                                    date = spend.date,
                                    category = spend.category,
                                    amount = spend.amount,
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
    HomeScreen()
}
