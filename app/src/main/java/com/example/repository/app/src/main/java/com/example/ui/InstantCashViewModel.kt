package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.InstantCashDatabase
import com.example.data.entity.*
import com.example.network.HyperwalletService
import com.example.network.InstantPayoutResult
import com.example.repository.InstantCashRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class UserMode {
    WORKER,
    CUSTOMER
}

class InstantCashViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: InstantCashRepository
    val userMode = MutableStateFlow(UserMode.WORKER)

    val allTasks: StateFlow<List<GigTask>>
    val transactions: StateFlow<List<WalletTransaction>>
    val notifications: StateFlow<List<NotificationItem>>

    private val _selectedTask = MutableStateFlow<GigTask?>(null)
    val selectedTask: StateFlow<GigTask?> = _selectedTask.asStateFlow()

    private val _isCashOutLoading = MutableStateFlow(false)
    val isCashOutLoading: StateFlow<Boolean> = _isCashOutLoading.asStateFlow()

    private val _payoutMessage = MutableStateFlow<String?>(null)
    val payoutMessage: StateFlow<String?> = _payoutMessage.asStateFlow()

    init {
        val database = InstantCashDatabase.getDatabase(application)
        repository = InstantCashRepository(database.instantCashDao(), HyperwalletService())

        allTasks = repository.allTasks.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        transactions = repository.transactions.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        notifications = repository.notifications.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
    }

    fun toggleUserMode() {
        userMode.value = if (userMode.value == UserMode.WORKER) UserMode.CUSTOMER else UserMode.WORKER
    }

    fun selectTask(task: GigTask) {
        _selectedTask.value = task
    }

    fun getTaskById(taskId: String): Flow<GigTask?> {
        return allTasks.map { tasks -> tasks.find { it.id == taskId } }
    }

    fun postNewJob(
        title: String,
        description: String,
        category: TaskCategory,
        payAmount: Double,
        estimatedHours: Double,
        locationName: String,
        onSuccess: (GigTask) -> Unit
    ) {
        viewModelScope.launch {
            val created = repository.postTask(title, description, category, payAmount, estimatedHours, locationName)
            onSuccess(created)
        }
    }

    fun acceptJob(taskId: String) {
        viewModelScope.launch {
            repository.acceptTask(taskId, "usr_worker_1", "Alex Rivers")
        }
    }

    fun submitProof(taskId: String, proofNote: String, imageUrl: String?) {
        viewModelScope.launch {
            repository.submitProof(taskId, proofNote, imageUrl)
        }
    }

    fun approveProofAndPayout(taskId: String) {
        viewModelScope.launch {
            repository.approveProofAndReleasePayout(taskId)
        }
    }

    fun performInstantCashOut(
        amount: Double,
        destinationType: String,
        destinationAccount: String,
        onComplete: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            _isCashOutLoading.value = true
            val result = repository.executeInstantCashOut("usr_worker_1", amount, destinationType, destinationAccount)
            _isCashOutLoading.value = false

            if (result.isSuccess) {
                _payoutMessage.value = "Success! $${result.netAmount} sent instantly via Hyperwallet."
                onComplete(true)
            } else {
                _payoutMessage.value = result.errorMessage ?: "Cash out failed."
                onComplete(false)
            }
        }
    }

    fun getChatMessages(taskId: String): Flow<List<ChatMessage>> {
        return repository.getChatMessages(taskId)
    }

    fun sendChatMessage(taskId: String, text: String) {
        viewModelScope.launch {
            repository.sendMessage(
                taskId = taskId,
                senderId = "usr_worker_1",
                senderName = "Alex Rivers",
                text = text,
                isWorker = (userMode.value == UserMode.WORKER)
            )
        }
    }

    fun clearPayoutMessage() {
        _payoutMessage.value = null
    }
}
