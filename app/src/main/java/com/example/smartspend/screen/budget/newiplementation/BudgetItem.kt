import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.smartspend.data.budget.Budget
import com.example.smartspend.data.earnings.earnings
import com.example.smartspend.data.spending.Spending

@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun BudgetItem(budget: Budget, spendingData: List<Spending>) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        BudgetChart(
            spendingData = spendingData,
            earningsData = earnings,
            date = budget.created.minusDays(100),
            threshHold = budget.threshHold,
            category = budget.category
        )
    }
}
