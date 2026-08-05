package com.example.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TaskStatus {
    OPEN,
    ACCEPTED,
    IN_PROGRESS,
    PROOF_SUBMITTED,
    COMPLETED,
    DISPUTED,
    CANCELLED
}

enum class TaskCategory {
    YARD_WORK,
    DELIVERY,
    HANDYMAN,
    HOUSEKEEPING,
    TECH_SUPPORT,
    PET_CARE,
    OTHER
}

@Entity(tableName = "gig_tasks")
data class GigTask(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val category: TaskCategory,
    val payAmount: Double,
    val estimatedHours: Double,
    val locationName: String,
    val distanceMiles: Double,
    val latitude: Double,
    val longitude: Double,
    val customerId: String,
    val customerName: String,
    val workerId: String? = null,
    val workerName: String? = null,
    val status: TaskStatus = TaskStatus.OPEN,
    val isEscrowLocked: Boolean = true,
    val proofNote: String? = null,
    val proofImageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
