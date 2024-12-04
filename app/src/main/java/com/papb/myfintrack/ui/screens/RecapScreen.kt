package com.papb.myfintrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.papb.myfintrack.data.enums.Period
import com.papb.myfintrack.data.repository.FinanceRepository
import com.papb.myfintrack.ui.components.InfoCard
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecapScreen(financeRepository: FinanceRepository) {
    var totalExpenses by remember { mutableStateOf(0.0) }
    var totalIncomes by remember { mutableStateOf(0.0) }
    var period by remember { mutableStateOf(Period.DAILY) }
    var totalBalance by remember { mutableStateOf(0.0) }
    var periodDetail by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(period) {
        scope.launch {
            when (period) {
                Period.DAILY -> {
                    totalExpenses = financeRepository.getTotalDailyExpenses()
                    totalIncomes = financeRepository.getTotalDailyIncomes()
                    val currentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                        Date()
                    )
                    periodDetail = currentDate
                }
                Period.WEEKLY -> {
                    totalExpenses = financeRepository.getTotalWeeklyExpenses()
                    totalIncomes = financeRepository.getTotalWeeklyIncomes()
                    val currentWeek = SimpleDateFormat("W", Locale.getDefault()).format(Date()) // Get week number
                    val currentMonth = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())
                    periodDetail = "Week $currentWeek, $currentMonth"
                }
                Period.MONTHLY -> {
                    totalExpenses = financeRepository.getTotalMonthlyExpenses()
                    totalIncomes = financeRepository.getTotalMonthlyIncomes()
                    val currentMonth = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(Date())
                    periodDetail = currentMonth
                }
            }
            totalBalance = totalIncomes - totalExpenses
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Recap", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        var expanded by remember { mutableStateOf(false) }
        val periods = Period.entries.map { it.name.lowercase() }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = period.name.lowercase(),
                onValueChange = {},
                label = { Text("Select Period") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown menu"
                    )
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth().menuAnchor(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                periods.forEach { periodOption ->
                    DropdownMenuItem(
                        onClick = {
                            period = Period.valueOf(periodOption.uppercase())
                            expanded = false
                        },
                        text = { Text(periodOption) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = periodDetail,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoCard(
                    label = "Total Incomes",
                    value = totalIncomes,
                    iconColor = Color.Green,
                    icon = Icons.Filled.KeyboardArrowDown,
                    modifier = Modifier.weight(1f)
                )

                InfoCard(
                    label = "Total Expenses",
                    value = totalExpenses,
                    iconColor = Color.Red,
                    icon = Icons.Filled.KeyboardArrowUp,
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
    }
}