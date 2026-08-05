package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.entity.GigTask
import com.example.ui.components.TaskCard

@Composable
fun WorkerHomeScreen(
    tasks: List<GigTask>,
    onSelectTask: (GigTask) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("⚡ Available Local Tasks", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Instant escrow payouts upon completion proof verification.", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(tasks) { task ->
                TaskCard(task = task, onClick = { onSelectTask(task) })
            }
        }
    }
}
