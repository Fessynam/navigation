package com.tuyoleni.smartspend.components.elements.cards

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuyoleni.smartspend.data.earnings.earnings
import java.time.LocalDate

@Composable
fun TransactionCard(date: LocalDate, category: String, amount: Int, type: Int) {
    val cardColor = if (type == 1) Color(0xFFFF5D5D) else Color(0xFF63D167)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .border(
                BorderStroke(1.dp, cardColor.copy(alpha = 0.8f)),
                shape = MaterialTheme.shapes.medium
            )
            .background(color = cardColor.copy(alpha = 0.1f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),

            ) {
            Column {
                Text(
                    text = category,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(text = date.toString(), style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = amount.toString(),
                fontWeight = FontWeight.Black,
                color = cardColor.copy(alpha = 0.9f),
                fontSize = (18.sp)
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun TransactionCardPreview() {
    Column {
        earnings.forEach { earning ->
            TransactionCard(
                date = earning.date, category = earning.category, amount = earning.amount, type = 1
            )
        }
    }
}
