package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey val id: Long = 1L,
    val name: String = "Alex Rivera",
    val role: String = "WORKER",
    val isIdVerified: Boolean = true,
    val idType: String = "State Driver's License",
    val rating: Float = 4.95f,
    val completedJobsCount: Int = 18,
    val walletBalance: Double = 145.50,
    val earningStreakDays: Int = 4,
    val badgePoints: Int = 320,
    val phone: String = "(555) 234-5678",
    val email: String = "alex.rivera@instantcash.app",
    val payoutMethod: String = "Debit Card",
    val hyperwalletPayeeToken: String = "usr-883a9120-hw",
    val hyperwalletStatus: String = "ACTIVATED"
)
