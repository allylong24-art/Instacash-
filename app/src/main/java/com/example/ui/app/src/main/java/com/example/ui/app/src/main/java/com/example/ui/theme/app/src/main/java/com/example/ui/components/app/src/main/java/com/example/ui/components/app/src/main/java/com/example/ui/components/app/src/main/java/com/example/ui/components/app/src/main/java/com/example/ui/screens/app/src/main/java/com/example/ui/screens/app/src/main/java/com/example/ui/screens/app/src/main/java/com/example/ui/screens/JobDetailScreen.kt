package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.entity.GigTask
import com.example.data.entity.TaskStatus
import com.example.ui.components.MapSimulator
import com.example.ui.components.ProofUploadDialog

@Composable
fun JobDetailScreen(
    task: GigTask?,
    onAcceptJob: (String) -> Unit,
    onSubmitProof: (String, String, String?) -> Unit,
    onApprovePayout: (String) -> Unit,
    onOpenChat: (String) -> Unit
) {
    if (task == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Task not found.")
        }
        return
    }

    var showProofDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(task.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Pay: $${"%.2f".format(task.payAmount)} • Status: ${task.status.name}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(12.dp))

        MapSimulator(locationName = task.locationName)
        Spacer(modifier = Modifier.height(16.dp))

        Text(task.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(20.dp))

        if (task.status == TaskStatus.OPEN) {
            Button(onClick = { onAcceptJob(task.id) }, modifier = Modifier.fillMaxWidth()) {
                Text("Accept Gig & Lock Escrow")
            }
        } else if (task.status == TaskStatus.ACCEPTED || task.status == TaskStatus.IN_PROGRESS) {
            Button(onClick = { showProofDialog = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Upload Completion Proof")
            }
        } else if (task.status == TaskStatus.PROOF_SUBMITTED) {
            Button(onClick = { onApprovePayout(task.id) }, modifier = Modifier.fillMaxWidth()) {
                Text("Approve Proof & Release Instant Payout ⚡")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(onClick = { onOpenChat(task.id) }, modifier = Modifier.fillMaxWidth()) {
            Text("Open In-App Direct Chat")
        }
    }

    if (showProofDialog) {
        ProofUploadDialog(
            onDismiss = { showProofDialog = false },
            onSubmitProof = { note, url ->
                onSubmitProof(task.id, note, url)
                showProofDialog = false
            }
        )
    }
}
