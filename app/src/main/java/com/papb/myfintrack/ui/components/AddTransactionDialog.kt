package com.papb.myfintrack.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddTransactionDialog(
    onDismiss: () -> Unit,
    onAddTransaction: () -> Unit,
    transactionType: String,
    setTransactionType: (String) -> Unit,
    amount: String,
    setAmount: (String) -> Unit,
    description: String,
    setDescription: (String) -> Unit
) {
    val isAddButtonEnabled = transactionType.isNotEmpty() && amount.isNotEmpty()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add $transactionType", color = MaterialTheme.colorScheme.primary) },
        text = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { setTransactionType("income") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (transactionType == "income") MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    ) {
                        Text("Income")
                    }
                    Button(
                        onClick = { setTransactionType("expense") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (transactionType == "expense") MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    ) {
                        Text("Expense")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = amount,
                    onValueChange = { newAmount ->
                        if (newAmount.isEmpty() || newAmount.all { it.isDigit() || it == '.' }) {
                            setAmount(newAmount)
                        }
                    },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = description,
                    onValueChange = { setDescription(it) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onAddTransaction,
                enabled = isAddButtonEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = if (isAddButtonEnabled) MaterialTheme.colorScheme.primary else Color.Gray)
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
