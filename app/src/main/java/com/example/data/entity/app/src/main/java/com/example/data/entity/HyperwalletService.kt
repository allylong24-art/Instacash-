package com.example.network

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID

data class HyperwalletPayee(
    val userToken: String,
    val clientUserId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val status: String,
    val programToken: String
)

data class HyperwalletPaymentResponse(
    val isSuccess: Boolean,
    val paymentToken: String,
    val clientPaymentId: String,
    val amount: Double,
    val currency: String = "USD",
    val status: String,
    val destinationToken: String,
    val transferMethodType: String,
    val createdOn: String,
    val estimatedArrivalSeconds: Int = 30,
    val errorMessage: String? = null
)

class HyperwalletService {
    suspend fun getOrCreatePayee(clientUserId: String, email: String, firstName: String, lastName: String): HyperwalletPayee = withContext(Dispatchers.IO) {
        delay(200)
        HyperwalletPayee(
            userToken = "usr-${clientUserId.take(8)}-hw",
            clientUserId = clientUserId,
            firstName = firstName,
            lastName = lastName,
            email = email,
            status = "ACTIVATED",
            programToken = "prg-883a9120-hw-program"
        )
    }

    suspend fun executeInstantPayout(userToken: String, amount: Double, destinationMethod: String): HyperwalletPaymentResponse = withContext(Dispatchers.IO) {
        delay(320)
        val clientPaymentId = "PAY-${UUID.randomUUID().toString().take(10).uppercase()}"
        val paymentToken = "pmt-${UUID.randomUUID().toString().take(8).uppercase()}-HW"

        HyperwalletPaymentResponse(
            isSuccess = true,
            paymentToken = paymentToken,
            clientPaymentId = clientPaymentId,
            amount = amount,
            currency = "USD",
            status = "COMPLETED",
            destinationToken = "trm-instant-dest-hw",
            transferMethodType = destinationMethod,
            createdOn = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", java.util.Locale.US).format(java.util.Date())
        )
    }
}
