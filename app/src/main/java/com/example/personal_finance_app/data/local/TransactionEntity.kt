package com.example.personal_finance_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val amount: Double,
    val type: String, // "income" or "expense"
    val category: String,
    val date: Long,
    val note: String
)