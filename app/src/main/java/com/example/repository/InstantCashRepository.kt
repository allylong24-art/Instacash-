package com.example.repository

import com.example.data.dao.InstantCashDao
import com.example.data.entity.*
import com.example.network.HyperwalletService
import com.example.network.InstantPayoutRequest
import com.example.network.InstantPayoutResult
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class InstantCashRepository(
    private val dao: InstantCashDao,
    private val hyperwalletService: HyperwalletService
) {

    val allTasks: Flow<List<GigTask>> = dao.getAllTasks()
    val openTasks: Flow<List<GigTask>> = dao.getOpenTasks()
    val transactions: Flow<List<WalletTransaction>> = dao.getAllTransactions()
    val notifications: Flow<List<NotificationItem>> = dao.getNotifications()

    fun getUserProfile(userId: String): Flow<UserProfile?> = dao.getUserProfile(userId)
    fun getChatMessages(taskId: String): Flow<List<ChatMessage>> = dao.getChatMessagesForTask(taskId)

    suspend fun seedInitialDataIfEmpty() {
        // Seed default profile if missing
        dao.insertUserProfile(
            UserProfile(
                id = "usr_worker_1",
                fullName = "Alex Rivers",
                email = "alex.rivers@instantcash.app",
                role = UserRole.WORKER,
                ratingScore = 4.95f,
                completedJobsCount = 24,
                walletBalance = 385.00,
                pendingEscrowBalance = 110.00
            )
        )

        // Seed initial local tasks
        val mockTasks = listOf(
            GigTask(
                id = "task_1",
                title = "Front Lawn Mowing & Edging",
                description = "Mow front yard, edge along driveway, and blow away clippings. Equipment can be provided if needed.",
                category = TaskCategory.YARD_WORK,
                payAmount = 65.00,
                estimatedHours = 1.5,
                locationName = "Oakridge Park, 2.1 mi away",
                distanceMiles = 2.1,
                latitude = 37.7749,
                longitude = -122.4194,
                customerId = "cust_101",
                customerName = "Sarah Jenkins",
                status = TaskStatus.OPEN
            ),
            GigTask(
                id = "task_2",
                title = "Express Package Delivery to Post Office",
                description = "Pick up 3 pre-labeled shipping boxes from porch and drop off at UPS store before 4 PM.",
                category = TaskCategory.DELIVERY,
                payAmount = 35.00,
                estimatedHours = 0.75,
                locationName = "Downtown Commerce, 1.2 mi away",
                distanceMiles = 1.2,
                latitude = 37.7833,
                longitude = -122.4167,
                customerId = "cust_102",
                customerName = "Marcus Brody",
                status = TaskStatus.OPEN
            ),
            GigTask(
                id = "task_3",
                title = "Assemble IKEA Dining Table & 4 Chairs",
                description = "Help assemble newly delivered IKEA EKEDALEN table set in kitchen. Tools provided.",
                category = TaskCategory.HANDYMAN,
                payAmount = 90.00,
                estimatedHours = 2.0,
                locationName = "Westside Heights, 3.8 mi away",
                distanceMiles = 3.8,
                latitude = 37.7690,
                longitude = -122.4480,
                customerId = "cust_103",
                customerName = "Elena Vance",
                status = TaskStatus.OPEN
            )
        )

        dao.insertTasks(mockTasks)

        // Seed initial transactions
        dao.insertTransaction(
            WalletTransaction(
                id = "txn_101",
                title = "Front Lawn Mowing Payout",
                amount = 65.00,
                type = TransactionType.EARNED_TASK,
                timestamp = System.currentTimeMillis() - 86400000
            )
        )
    }

    suspend fun postTask(
        title: String,
        description: String,
        category: TaskCategory,
        payAmount: Double,
        estimatedHours: Double,
        locationName: String
    ): GigTask {
        val newTask = GigTask(
            id = "task_" + UUID.randomUUID().toString().take(8),
            title = title,
            description = description,
            category = category,
            payAmount = payAmount,
            estimatedHours = estimatedHours,
            locationName = locationName,
            distanceMiles = 0.8,
            latitude = 37.7749,
            longitude = -122.4194,
            customerId = "usr_worker_1",
            customerName = "Alex Rivers",
            status = TaskStatus.OPEN,
            isEscrowLocked = true
        )

        dao.insertTask(newTask)
        dao.insertNotification(
            NotificationItem(
                id = UUID.randomUUID().toString(),
                title = "Gig Posted & Escrow Locked",
                message = "Your task '$title' ($${payAmount}) is active. Payment held safely in Stripe escrow.",
                actionRoute = "job_detail/${newTask.id}"
            )
        )
        return newTask
    }

    suspend fun acceptTask(taskId: String, workerId: String, workerName: String) {
        val task = dao.getTaskById(taskId) ?: return
        val updated = task.copy(
            workerId = workerId,
            workerName = workerName,
            status = TaskStatus.ACCEPTED,
            isEscrowLocked = true
        )
        dao.updateTask(updated)
        dao.insertNotification(
            NotificationItem(
                id = UUID.randomUUID().toString(),
                title = "Gig Accepted!",
                message = "You accepted '${task.title}'. Funds ($${task.payAmount}) are locked in escrow.",
                actionRoute = "job_detail/${task.id}"
            )
        )
    }

    suspend fun submitProof(taskId: String, proofNote: String, proofImageUrl: String?) {
        val task = dao.getTaskById(taskId) ?: return
        val updated = task.copy(
            status = TaskStatus.PROOF_SUBMITTED,
            proofNote = proofNote,
            proofImageUrl = proofImageUrl
        )
        dao.updateTask(updated)
        dao.insertNotification(
            NotificationItem(
                id = UUID.randomUUID().toString(),
                title = "Proof Submitted",
                message = "Proof of work sent for '${task.title}'. Awaiting customer approval.",
                actionRoute = "job_detail/${task.id}"
            )
        )
    }

    suspend fun approveProofAndReleasePayout(taskId: String) {
        val task = dao.getTaskById(taskId) ?: return
        val updated = task.copy(
            status = TaskStatus.COMPLETED,
            isEscrowLocked = false
        )
        dao.updateTask(updated)

        // Record payout transaction
        dao.insertTransaction(
            WalletTransaction(
                id = "txn_" + UUID.randomUUID().toString().take(8),
                title = "Payout Released: ${task.title}",
                amount = task.payAmount,
                type = TransactionType.EARNED_TASK,
                timestamp = System.currentTimeMillis()
            )
        )

        dao.insertNotification(
            NotificationItem(
                id = UUID.randomUUID().toString(),
                title = "Instant Payment Released! ⚡",
                message = "$${task.payAmount} has been released from escrow into your wallet balance.",
                actionRoute = "wallet"
            )
        )
    }

    suspend fun executeInstantCashOut(
        userId: String,
        amount: Double,
        destinationType: String,
        destinationAccount: String
    ): InstantPayoutResult {
        val result = hyperwalletService.initiateInstantPayout(
            InstantPayoutRequest(
                userToken = "HW-TOKEN-9912",
                amount = amount,
                destinationType = destinationType,
                destinationAccountEnding = destinationAccount
            )
        )

        if (result.isSuccess) {
            dao.insertTransaction(
                WalletTransaction(
                    id = result.transactionId,
                    title = "Instant Cash Out ($destinationType)",
                    amount = -amount,
                    type = TransactionType.INSTANT_CASHOUT,
                    payoutDestination = "$destinationType ending in $destinationAccount"
                )
            )

            dao.insertNotification(
                NotificationItem(
                    id = UUID.randomUUID().toString(),
                    title = "Instant Payout Sent! 💸",
                    message = "$${result.netAmount} successfully transferred to $destinationType ending in $destinationAccount via Hyperwallet.",
                    actionRoute = "wallet"
                )
            )
        }

        return result
    }

    suspend fun sendMessage(taskId: String, senderId: String, senderName: String, text: String, isWorker: Boolean) {
        val message = ChatMessage(
            id = UUID.randomUUID().toString(),
            taskId = taskId,
            senderId = senderId,
            senderName = senderName,
            messageText = text,
            isFromWorker = isWorker
        )
        dao.insertChatMessage(message)
    }
}
