package com.papb.myfintrack.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

abstract class Transaction(
    open val amount: Double,
    open val description: String,
    open val date: String
)

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    override val amount: Double,
    override val description: String,
    override val date: String,
    val category: String
) : Transaction(amount, description, date)

@Entity(tableName = "incomes")
data class Income(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    override val amount: Double,
    override val description: String,
    override val date: String,
    val source: String
) : Transaction(amount, description, date)
