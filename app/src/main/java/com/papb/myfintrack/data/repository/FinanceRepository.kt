package com.papb.myfintrack.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.papb.myfintrack.data.dao.FinanceDao
import com.papb.myfintrack.data.models.Expense
import com.papb.myfintrack.data.models.Income
import java.text.SimpleDateFormat
import java.util.*

class FinanceRepository(private val financeDao: FinanceDao) {

    private val observers = mutableListOf<Observer>()

    val allExpenses: LiveData<List<Expense>> = financeDao.getAllExpenses()
    val allIncomes: LiveData<List<Income>> = financeDao.getAllIncomes()

    suspend fun addExpense(expense: Expense) {
        financeDao.insertExpense(expense)
        notifyObservers()
    }

    suspend fun addIncome(income: Income) {
        financeDao.insertIncome(income)
        notifyObservers()
    }

    suspend fun getTotalExpenses(): Double {
        return financeDao.getTotalExpenses()
    }

    suspend fun getTotalIncomes(): Double {
        return financeDao.getTotalIncomes()
    }

    suspend fun getTotalMonthlyExpenses(): Double {
        val currentMonth = getCurrentMonth()
        return financeDao.getTotalExpensesForMonth(currentMonth)
    }

    suspend fun getTotalMonthlyIncomes(): Double {
        val currentMonth = getCurrentMonth()
        return financeDao.getTotalIncomesForMonth(currentMonth)
    }

    suspend fun getTotalDailyExpenses(): Double {
        val currentDate = getCurrentDate()
        return financeDao.getTotalExpensesForDate(currentDate)
    }

    suspend fun getTotalDailyIncomes(): Double {
        val currentDate = getCurrentDate()
        return financeDao.getTotalIncomesForDate(currentDate)
    }

    suspend fun getTotalWeeklyExpenses(): Double {
        val currentWeek = getCurrentWeek()
        Log.d("FinanceRepository", "Current Week: $currentWeek")
        return financeDao.getTotalExpensesForWeek(currentWeek)
    }

    suspend fun getTotalWeeklyIncomes(): Double {
        val currentWeek = getCurrentWeek()
        return financeDao.getTotalIncomesForWeek(currentWeek)
    }

    private fun getCurrentMonth(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun getCurrentWeek(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val dateFormat = SimpleDateFormat("yyyy-'W'ww", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    private suspend fun notifyObservers() {
        val totalBalance = getTotalIncomes() - getTotalExpenses()
        for (observer in observers) {
            observer.update(totalBalance)
        }
    }
}
