package com.tuyoleni.smartspend.components.vico.linechart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
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
import com.tuyoleni.smartspend.data.earnings.Earnings
import com.tuyoleni.smartspend.data.spending.Spending
import kotlin.math.max

@Composable
fun EarningSpendingChart(earnings: List<Earnings>, spending: List<Spending>) {
    Surface(
        shape = RoundedCornerShape(14.dp), color = Color.Transparent
    ) {
        val red = Color(0xFFFF5D5D)
        val green = Color(0xFF63D167)

        val earningValues = earnings.map { it.amount }
        val spendingValues = spending.map { it.amount }
        val maxSpendingValue = spendingValues.maxOrNull()?.toFloat() ?: 0f

        val maxEarningValue = earningValues.maxOrNull()?.toFloat() ?: 0f


        val maxY = max(maxEarningValue.toFloat(), maxSpendingValue)


        if (earningValues.isNotEmpty() || spendingValues.isNotEmpty()) {
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
                            axisValueOverrider = AxisValueOverrider.fixed(maxY = maxY),
                        ),
                    ), model = CartesianChartModel(LineCartesianLayerModel.build {
                        series(earningValues)
                        series(spendingValues)
                    }), horizontalLayout = HorizontalLayout.fullWidth()
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Recent Earnings: ${earningValues[earningValues.lastIndex]}",
                        fontSize = 10.sp,
                        lineHeight = 16.sp

                    )

                    Text(
                        text = "Recent Spending: ${spendingValues[spendingValues.lastIndex]}",
                        fontSize = 10.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No data available", color = Color.Gray)
            }
        }
    }
}
