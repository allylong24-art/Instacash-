package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class UserRole {
    WORKER,
    CUSTOMER
}

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val id: String,
    val fullName: String,
    val email: String,
    val role: UserRole,
    val ratingScore: Float = 4.9f,
    val completedJobsCount: Int = 18,
    val isIdVerified: Boolean = true,
    val isBackgroundChecked: Boolean = true,
    val walletBalance: Double = 245.50,
    val pendingEscrowBalance: Double = 120.00,
    val hyperwalletToken: String? = "HW-ACC-883921"
)
