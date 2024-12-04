package com.papb.myfintrack.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.papb.myfintrack.data.models.Expense
import com.papb.myfintrack.data.models.Income

@Dao
interface FinanceDao {

    @Insert
    suspend fun insertExpense(expense: Expense)

    @Insert
    suspend fun insertIncome(income: Income)

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM incomes")
    fun getAllIncomes(): LiveData<List<Income>>

    @Query("SELECT SUM(amount) FROM expenses WHERE strftime('%Y-%m', date) = :month")
    suspend fun getTotalExpensesForMonth(month: String): Double

    @Query("SELECT SUM(amount) FROM incomes WHERE strftime('%Y-%m', date) = :month")
    suspend fun getTotalIncomesForMonth(month: String): Double

    @Query("SELECT SUM(amount) FROM expenses WHERE strftime('%Y-%m-%d', date) = :date")
    suspend fun getTotalExpensesForDate(date: String): Double

    @Query("SELECT SUM(amount) FROM incomes WHERE strftime('%Y-%m-%d', date) = :date")
    suspend fun getTotalIncomesForDate(date: String): Double

    @Query("SELECT SUM(amount) FROM expenses WHERE strftime('%Y-W%W', date) = :week")
    suspend fun getTotalExpensesForWeek(week: String): Double

    @Query("SELECT SUM(amount) FROM incomes WHERE strftime('%Y-W%W', date) = :week")
    suspend fun getTotalIncomesForWeek(week: String): Double

    @Query("SELECT SUM(amount) FROM expenses")
    suspend fun getTotalExpenses(): Double

    @Query("SELECT SUM(amount) FROM incomes")
    suspend fun getTotalIncomes(): Double
}
