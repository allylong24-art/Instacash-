package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_transactions")
data class WalletTransaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val amount: Double,
    val type: String,
    val method: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "INSTANT_COMPLETED",
    val relatedJobId: Long = 0L,
    val hyperwalletPaymentToken: String = "pmt-hw-instant"
)
