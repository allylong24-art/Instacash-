package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gig_tasks")
data class GigTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val category: String,
    val payAmount: Double,
    val estimatedMinutes: Int,
    val address: String,
    val distanceMiles: Double,
    val isInstantPay: Boolean = true,
    val isEscrowFunded: Boolean = true,
    val customerName: String,
    val customerRating: Float = 4.9f,
    val customerPhone: String = "(555) 019-2831",
    val requiredSkills: String = "No special tools needed",
    val startTime: String = "Immediate",
    val deadline: String = "Today",
    val isIndoor: Boolean = true,
    val status: String = "OPEN",
    val assignedWorkerName: String = "",
    val proofText: String = "",
    val proofPhotoUri: String = "",
    val checkInTimeMillis: Long = 0L,
    val checkOutTimeMillis: Long = 0L,
    val createdAtMillis: Long = System.currentTimeMillis(),
    val isRecurring: Boolean = false,
    val workerCountNeeded: Int = 1,
    val workerCountHired: Int = 0
)
