package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileVerificationScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("🛡️ ID Verification & Safety Badges", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Alex Rivers", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Verified Local Worker • ⭐ 4.95 Rating")
                Spacer(modifier = Modifier.height(12.dp))
                Text("✓ Government ID Verified")
                Text("✓ Criminal Background Check Passed")
                Text("✓ Hyperwallet Payment Account Connected")
            }
        }
    }
}
