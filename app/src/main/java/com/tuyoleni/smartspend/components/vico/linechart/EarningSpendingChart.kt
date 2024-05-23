package com.tuyoleni.smartspend.components.vico.linechart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.fullWidth
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.tuyoleni.smartspend.data.calculateMonthlyEarnings
import com.tuyoleni.smartspend.data.calculateMonthlySpending
import com.tuyoleni.smartspend.data.earnings.MonthlyEarning
import com.tuyoleni.smartspend.data.earnings.earnings
import com.tuyoleni.smartspend.data.spending.MonthlySpending
import com.tuyoleni.smartspend.data.spending.spending
@Composable
fun EarningSpendingChart(earnings: List<MonthlyEarning>, spending: List<MonthlySpending>) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.Transparent
    ) {
        val red = Color(0xFFFF5D5D)
        val green = Color(0xFF63D167)

        val earningValues = earnings.map { it.amount }
        val spendingValues = spending.map { it.amount }

        val totalAccountValue = maxOf(
            earnings.maxOfOrNull { it.amount } ?: 0f, // Get the maximum amount from the earnings list or 0 if it's empty
        )


        Box(modifier = Modifier.fillMaxSize()) {
            CartesianChartHost(
                chart = rememberCartesianChart(
                    rememberLineCartesianLayer(
                        listOf(
                            rememberLineSpec(
                                shader = DynamicShader.color(green),
                                backgroundShader = DynamicShader.verticalGradient(
                                    arrayOf(green.copy(alpha = 0.5f), green.copy(alpha = 0f)),
                                ),
                            ),
                            rememberLineSpec(
                                shader = DynamicShader.color(red),
                                backgroundShader = DynamicShader.verticalGradient(
                                    arrayOf(red.copy(alpha = 0.5f), red.copy(alpha = 0.1f)),
                                ),
                            ),
                        ),
                        axisValueOverrider = AxisValueOverrider.fixed(maxY = totalAccountValue),
                    ),
                ), model = CartesianChartModel(LineCartesianLayerModel.build {
                    series(earningValues)
                    series(spendingValues)
                }), horizontalLayout = HorizontalLayout.fullWidth()
            )
            Column(
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(text = "Earnings: $earningValues")
                Text(text = "Spending: $spendingValues")
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
suspend fun main() {
    val monthlyEarnings = calculateMonthlyEarnings(earnings)
    val monthlySpending = calculateMonthlySpending(spending)
    println(monthlyEarnings)
    println(monthlySpending)
}
