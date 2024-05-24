import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tuyoleni.smartspend.data.budget.Budget
import com.tuyoleni.smartspend.data.spending.Spending

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BudgetItem(budget: Budget, spendingData: List<Spending>) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .fillMaxSize()
            .height(180.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        if (spendingData.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            return
        }else{
            BudgetChart(
                spendingData = spendingData,
                date = budget.created.minusDays(1),
                threshHold = budget.threshHold,
            )
        }
    }
}