package com.papb.myfintrack.data.models

object TransactionFactory {

    fun createExpense(amount: Double, description: String, date: String, category: String): Expense {
        return Expense(amount = amount, description = description, date = date, category = category)
    }

    fun createIncome(amount: Double, description: String, date: String, source: String): Income {
        return Income(amount = amount, description = description, date = date, source = source)
    }
}
