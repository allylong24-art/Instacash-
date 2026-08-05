package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProofUploadDialog(
    onDismiss: () -> Unit,
    onSubmitProof: (String, String?) -> Unit
) {
    var note by remember { mutableStateOf("Completed task thoroughly according to requirements.") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("📸 Upload Completion Proof", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Provide notes or photos showing task completion for customer review.")
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Completion Notes / GPS Check-In") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSubmitProof(note, "https://picsum.photos/400/300") }) {
                Text("Submit Proof")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
