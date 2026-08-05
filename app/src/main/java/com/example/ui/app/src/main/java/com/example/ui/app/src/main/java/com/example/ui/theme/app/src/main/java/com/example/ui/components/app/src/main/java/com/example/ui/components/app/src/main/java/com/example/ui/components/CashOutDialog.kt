package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CashOutDialog(
    availableBalance: Double,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onConfirmCashOut: (Double, String, String) -> Unit
) {
    var amountText by remember { mutableStateOf(availableBalance.toString()) }
    var destinationType by remember { mutableStateOf("DEBIT_CARD") }
    var accountEnding by remember { mutableStateOf("4829") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("⚡ Instant Cash Out via Hyperwallet", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text(
                    text = "Transfer earnings immediately to your debit card or account with guaranteed instant escrow clearance.",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Amount ($)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Payout Destination:", fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = destinationType == "DEBIT_CARD",
                        onClick = { destinationType = "DEBIT_CARD" }
                    )
                    Text("Debit Card (Ending in 4829)")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = destinationType == "PAYPAL",
                        onClick = { destinationType = "PAYPAL" }
                    )
                    Text("PayPal Account")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amt = amountText.toDoubleOrNull() ?: 0.0
                    onConfirmCashOut(amt, destinationType, accountEnding)
                },
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                } else {
                    Text("Cash Out Now")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
