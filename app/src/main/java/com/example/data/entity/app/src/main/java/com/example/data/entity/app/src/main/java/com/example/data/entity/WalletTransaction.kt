package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TransactionType {
    EARNED_TASK,
    INSTANT_CASHOUT,
    ESCROW_HOLD,
    REFUND,
    BONUS
}

@Entity(tableName = "wallet_transactions")
data class WalletTransaction(
    @PrimaryKey val id: String,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val timestamp: Long = System.currentTimeMillis(),
    val referenceId: String? = null,
    val payoutDestination: String? = null
)
