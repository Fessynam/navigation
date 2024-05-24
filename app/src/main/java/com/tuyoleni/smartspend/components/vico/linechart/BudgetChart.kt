import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.decoration.rememberHorizontalLine
import com.patrykandpatrick.vico.compose.cartesian.fullWidth
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.data.AxisValueOverrider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.Dimensions
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.data.spending.Spending
import com.tuyoleni.smartspend.data.spending.spending
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetChart(
    spendingData: List<Spending>,
    date: LocalDate,
    threshHold: Int,
) {
    val filteredSpendingData = spendingData.filter { it.date >= date }
    val spendingValues = filteredSpendingData.map { it.amount }
    val chartColor = MaterialTheme.colorScheme.primary
    val maxSpendingValue = spendingValues.maxOrNull()?.toFloat() ?: 0f
    val maxY = maxOf(threshHold.toFloat(), maxSpendingValue) + (maxSpendingValue / 5f)
    val scrollState = rememberScrollState()

    CartesianChartHost(
        modifier = Modifier.fillMaxWidth(), chart = rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider = ColumnCartesianLayer.ColumnProvider.series(
                    listOf(
                        rememberLineComponent(Color.Blue, 2.dp, margins = Dimensions.Empty),
                    )
                ),
                axisValueOverrider = AxisValueOverrider.fixed(maxY = maxY),
                spacing = 1.dp,
                innerSpacing = 0.dp,
            ),
            decorations = listOf(
                rememberHorizontalLine(
                    y = { threshHold.toFloat() }, line = rememberLineComponent(
                        color = Color.Gray.copy(alpha = 0.5f), thickness = 5.dp
                    ), labelComponent = rememberTextComponent(
                        padding = Dimensions.of(horizontal = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            ),
        ), model = CartesianChartModel(ColumnCartesianLayerModel.build {
            series(spendingValues)
        })
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BudgetChartPreview() {
    val budget = Budget(
        user = "Simeon", threshHold = 10000, created = LocalDate.now(), category = "Food"
    )

    BudgetChart(
        spendingData = spending,
        date = LocalDate.now(),
        threshHold = budget.threshHold,
    )
}
