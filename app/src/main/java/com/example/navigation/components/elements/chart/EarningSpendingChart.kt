package com.example.navigation.components.elements.chart


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.navigation.data.transections.Transaction
import com.example.navigation.data.transections.groupByMonth
import com.example.navigation.data.transections.transactions
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.common.shader.DynamicShader



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EarningSpendingChart(transactions: List<Transaction>) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.Black,
    ) {
        val yellow = Color(0xFFFFAA4A)
        val pink = Color(0xFFFF4AAA)

        val monthlyData = transactions.groupByMonth()

        val totalAccountValue = monthlyData.maxOfOrNull { it.earnings + it.spending } ?: 0f

        val months = monthlyData.map { it.month }

        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    listOf(
                        rememberLineSpec(
                            shader = DynamicShader.color(yellow),
                            backgroundShader = DynamicShader.verticalGradient(
                                arrayOf(yellow.copy(alpha = 0.5f), yellow.copy(alpha = 0f)),
                            ),
                        ),
                        rememberLineSpec(
                            shader = DynamicShader.color(pink),
                            backgroundShader = DynamicShader.verticalGradient(
                                arrayOf(pink.copy(alpha = 0.5f), pink.copy(alpha = 0f)),
                            ),
                        ),
                    ),
                    axisValueOverrider = AxisValueOverrider.fixed(maxY = totalAccountValue),
                ),
            ),
            model = CartesianChartModel(LineCartesianLayerModel.build {
                series(monthlyData.map { it.earnings})
                series(monthlyData.map { it.spending})
            }),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview("Monthly Transaction Line Chart", widthDp = 200, showBackground = false,
    showSystemUi = false
)
@Composable
fun PreviewMonthlyTransactionLineChart() {
    EarningSpendingChart(transactions = transactions)
}

