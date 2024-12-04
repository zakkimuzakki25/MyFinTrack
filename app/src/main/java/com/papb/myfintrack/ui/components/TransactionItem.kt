package com.papb.myfintrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.papb.myfintrack.data.models.Expense
import com.papb.myfintrack.data.models.Income
import com.papb.myfintrack.data.models.Transaction
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TransactionItem(transaction: Transaction) {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    val amountText = when (transaction) {
        is Expense -> "-${formatter.format(transaction.amount)}"
        is Income -> "+${formatter.format(transaction.amount)}"
        else -> "0.0"
    }

    val amountColor = when (transaction) {
        is Expense -> Color.Red
        is Income -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurface
    }

    Card(modifier = Modifier.fillMaxWidth().padding(8.dp).background(Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = amountText,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = amountColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = transaction.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(transaction.date)
                ?.let {
                    SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(it)
                }
            if (formattedDate != null) {
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
