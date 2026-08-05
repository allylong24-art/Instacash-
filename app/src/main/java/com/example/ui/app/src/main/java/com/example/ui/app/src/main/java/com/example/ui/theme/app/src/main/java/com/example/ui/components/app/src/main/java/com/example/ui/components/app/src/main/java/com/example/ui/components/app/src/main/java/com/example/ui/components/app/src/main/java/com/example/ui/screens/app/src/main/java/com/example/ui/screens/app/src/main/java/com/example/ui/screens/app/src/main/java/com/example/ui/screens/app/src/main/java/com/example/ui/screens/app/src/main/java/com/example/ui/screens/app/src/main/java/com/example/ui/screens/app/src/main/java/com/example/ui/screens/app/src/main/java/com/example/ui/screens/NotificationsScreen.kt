package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.entity.NotificationItem

@Composable
fun NotificationsScreen(notifications: List<NotificationItem>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("🔔 Activity Notifications", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(notifications) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(item.title, fontWeight = FontWeight.Bold)
                        Text(item.message)
                    }
                }
            }
        }
    }
}
