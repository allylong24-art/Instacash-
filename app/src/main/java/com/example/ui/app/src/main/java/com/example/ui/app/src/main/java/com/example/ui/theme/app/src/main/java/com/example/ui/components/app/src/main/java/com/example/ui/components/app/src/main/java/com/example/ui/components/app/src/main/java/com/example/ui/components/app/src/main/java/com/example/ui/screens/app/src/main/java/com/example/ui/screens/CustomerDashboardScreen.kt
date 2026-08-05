package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.entity.GigTask
import com.example.ui.components.TaskCard

@Composable
fun CustomerDashboardScreen(
    postedTasks: List<GigTask>,
    onPostNewJobClick: () -> Unit,
    onSelectTask: (GigTask) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("💼 Your Posted Jobs", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Escrow holds active until you approve proof.", style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = onPostNewJobClick) {
                Text("+ Post Job")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(postedTasks) { task ->
                TaskCard(task = task, onClick = { onSelectTask(task) })
            }
        }
    }
}
