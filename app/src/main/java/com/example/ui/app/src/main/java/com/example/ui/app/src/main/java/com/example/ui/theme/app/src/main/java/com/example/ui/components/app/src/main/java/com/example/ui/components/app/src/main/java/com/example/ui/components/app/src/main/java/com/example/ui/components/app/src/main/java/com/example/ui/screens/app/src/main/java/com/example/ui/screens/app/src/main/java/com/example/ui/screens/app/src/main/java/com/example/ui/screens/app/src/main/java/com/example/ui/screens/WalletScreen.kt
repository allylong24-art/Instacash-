package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.entity.WalletTransaction
import com.example.ui.components.CashOutDialog

@Composable
fun WalletScreen(
    transactions: List<WalletTransaction>,
    isCashOutLoading: Boolean,
    onCashOutConfirm: (Double, String, String, (Boolean) -> Unit) -> Unit
) {
    var showCashOutDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Wallet Balance", style = MaterialTheme.typography.labelLarge)
                Text("$385.00", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = { showCashOutDialog = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("⚡ Instant Cash Out (Hyperwallet)")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Transaction History", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(transactions) { txn ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(txn.title, fontWeight = FontWeight.SemiBold)
                            Text(txn.type.name, style = MaterialTheme.typography.bodySmall)
                        }
                        Text("$${"%.2f".format(txn.amount)}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    if (showCashOutDialog) {
        CashOutDialog(
            availableBalance = 385.00,
            isLoading = isCashOutLoading,
            onDismiss = { showCashOutDialog = false },
            onConfirmCashOut = { amount, destType, destAcc ->
                onCashOutConfirm(amount, destType, destAcc) { success ->
                    if (success) showCashOutDialog = false
                }
            }
        )
    }
}
