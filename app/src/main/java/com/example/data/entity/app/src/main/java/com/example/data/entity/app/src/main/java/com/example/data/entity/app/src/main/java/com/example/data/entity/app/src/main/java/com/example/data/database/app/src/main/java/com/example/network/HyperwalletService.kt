package com.example.network

import kotlinx.coroutines.delay

data class InstantPayoutRequest(
    val userToken: String,
    val amount: Double,
    val destinationType: String, // "DEBIT_CARD" or "PAYPAL" or "BANK_ACCOUNT"
    val destinationAccountEnding: String
)

data class InstantPayoutResult(
    val isSuccess: Boolean,
    val transactionId: String,
    val feeAmount: Double,
    val netAmount: Double,
    val errorMessage: String? = null
)

class HyperwalletService {

    suspend fun initiateInstantPayout(request: InstantPayoutRequest): InstantPayoutResult {
        // Simulate Hyperwallet API execution latency
        delay(1200)

        if (request.amount <= 0) {
            return InstantPayoutResult(
                isSuccess = false,
                transactionId = "",
                feeAmount = 0.0,
                netAmount = 0.0,
                errorMessage = "Invalid cash out amount."
            )
        }

        val fee = if (request.destinationType == "DEBIT_CARD") 1.99 else 0.50
        val net = request.amount - fee

        return InstantPayoutResult(
            isSuccess = true,
            transactionId = "HW-TXN-" + (100000..999999).random(),
            feeAmount = fee,
            netAmount = net
        )
    }
}
