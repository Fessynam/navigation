import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.decoration.rememberHorizontalLine
import com.patrykandpatrick.vico.compose.cartesian.fullWidth
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.tuyoleni.smartspend.components.vico.DimmedGray
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.data.calculateAccountBalance
import com.tuyoleni.smartspend.data.earnings.Earnings
import com.tuyoleni.smartspend.data.earnings.earnings
import com.tuyoleni.smartspend.data.spending.Spending
import com.tuyoleni.smartspend.data.spending.spending
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetChart(
    spendingData: List<Spending>,
    earningsData: List<Earnings>,
    date: LocalDate,
    threshHold: Int,
    category: String,
) {
    val filteredSpendingData = spendingData.filter { it.date >= date }
    val filteredEarningsData = earningsData.filter { it.date >= date }

    val spendingValues = filteredSpendingData.map { it.amount }
    //val dates = filteredSpendingData.map { it.date.toString() }
    val green = Color(0xFF69F0AE)
    val red = Color(0xFFFF5252)
    //val accountBalance =
        calculateAccountBalance(filteredSpendingData, filteredEarningsData).toFloat()

    val maxSpendingValue = spendingValues.maxOrNull()?.toFloat() ?: 0f
    val maxY = maxOf(threshHold.toFloat() + (maxSpendingValue / 5f))

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
            bottomAxis = rememberBottomAxis(),
            startAxis = rememberStartAxis(),
            decorations = listOf(
                rememberHorizontalLine(
                    y = { threshHold.toFloat() },
                    line = rememberLineComponent(
                        color = Color.DimmedGray.copy(alpha = 0.5f), thickness = 5.dp
                    ),
                    labelComponent = rememberTextComponent(
                        padding = Dimensions.of(horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    ),
                ),
            ),
        ), model = CartesianChartModel(LineCartesianLayerModel.build {
            series(spendingValues)
        }), horizontalLayout = HorizontalLayout.fullWidth()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BudgetChartPreview() {
    val budget =
        Budget(threshHold = 10000, created = LocalDate.now().minusDays(5), category = "Food")

    BudgetChart(
        spendingData = spending,
        earningsData = earnings,
        date = LocalDate.now().minusDays(10),
        threshHold = budget.threshHold,
        category = budget.category
    )
}
