package com.papb.myfintrack.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.papb.myfintrack.data.repository.FinanceRepository
import com.papb.myfintrack.data.models.TransactionFactory
import com.papb.myfintrack.data.repository.Observer
import com.papb.myfintrack.ui.components.AddTransactionDialog
import com.papb.myfintrack.ui.components.InfoCard
import com.papb.myfintrack.ui.components.TransactionItem
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MainScreen(financeRepository: FinanceRepository) {
    var balance by remember { mutableStateOf(0.0) }

    LaunchedEffect(financeRepository) {
        financeRepository.addObserver(object : Observer {
            override fun update(newBalance: Double) {
                balance = newBalance
            }
        })
    }

    val expenses by financeRepository.allExpenses.observeAsState(emptyList())
    val incomes by financeRepository.allIncomes.observeAsState(emptyList())

    val totalExpenses = expenses.sumOf { it.amount }
    val totalIncomes = incomes.sumOf { it.amount }
    val totalBalance = totalIncomes - totalExpenses

    val allTransactions = (expenses + incomes).sortedByDescending { it.date }

    var showAddTransactionDialog by remember { mutableStateOf(false) }
    var transactionType by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val context = LocalContext.current
    val toastMessage = remember { mutableStateOf("") }

    suspend fun addTransaction() {
        val parsedAmount = amount.toDoubleOrNull() ?: 0.0
        if (transactionType == "income" && parsedAmount > 0) {
            val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val source = "Unknown"
            val income = TransactionFactory.createIncome(amount = parsedAmount, description = description, date = currentDate, source = source)
            financeRepository.addIncome(income)
            toastMessage.value = "Income added"
        } else if (transactionType == "expense" && parsedAmount > 0) {
            val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val category = "Unknown"
            val expense = TransactionFactory.createExpense(amount = parsedAmount, description = description, date = currentDate, category = category)
            financeRepository.addExpense(expense)
            toastMessage.value = "Expense added"
        } else {
            toastMessage.value = "Please enter a valid amount"
        }
        showAddTransactionDialog = false
    }

    LaunchedEffect(toastMessage.value) {
        if (toastMessage.value.isNotEmpty()) {
            Toast.makeText(context, toastMessage.value, Toast.LENGTH_SHORT).show()
        }
    }

    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Dashboard", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InfoCard(
                        icon = Icons.Filled.KeyboardArrowDown,
                        label = "Total Incomes",
                        value = totalIncomes,
                        iconColor = Color.Green,
                        modifier = Modifier.weight(1f)
                    )
                    InfoCard(
                        icon = Icons.Filled.KeyboardArrowUp,
                        label = "Total Expenses",
                        value = totalExpenses,
                        iconColor = Color.Red,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                InfoCard(
                    label = "Balance",
                    value = totalBalance,
                    iconColor = MaterialTheme.colorScheme.secondary,
                    icon = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Transaction History", style = MaterialTheme.typography.headlineSmall, color = Color.Black, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(allTransactions) { transaction ->
                    TransactionItem(transaction)
                }
            }
        }

        FloatingActionButton(
            onClick = { showAddTransactionDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add Transaction")
        }
    }

    if (showAddTransactionDialog) {
        AddTransactionDialog(
            onDismiss = { showAddTransactionDialog = false },
            onAddTransaction = {
                scope.launch {
                    addTransaction()
                }
            },
            transactionType = transactionType,
            setTransactionType = { transactionType = it },
            amount = amount,
            setAmount = { amount = it },
            description = description,
            setDescription = { description = it }
        )
    }
}
