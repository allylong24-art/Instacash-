package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeaderBar(
    currentRoleName: String,
    onToggleRole: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("InstantCash", fontWeight = FontWeight.Bold)
            }
        },
        actions = {
            FilterChip(
                selected = true,
                onClick = onToggleRole,
                label = { Text(currentRoleName, fontWeight = FontWeight.SemiBold) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Switch Role",
                        modifier = Modifier.size(18.dp)
                    )
                },
                modifier = Modifier.padding(end = 8.dp)
            )
            IconButton(onClick = onNotificationsClick) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun AppBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { onNavigate("home") },
            icon = { Icon(Icons.Default.HomeWork, contentDescription = null) },
            label = { Text("Gigs") }
        )
        NavigationBarItem(
            selected = currentRoute == "wallet",
            onClick = { onNavigate("wallet") },
            icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = null) },
            label = { Text("Wallet") }
        )
        NavigationBarItem(
            selected = currentRoute == "post_job",
            onClick = { onNavigate("post_job") },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = null) },
            label = { Text("Post Job") }
        )
        NavigationBarItem(
            selected = currentRoute == "dispute",
            onClick = { onNavigate("dispute") },
            icon = { Icon(Icons.Default.Gavel, contentDescription = null) },
            label = { Text("Disputes") }
        )
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = { onNavigate("profile") },
            icon = { Icon(Icons.Default.VerifiedUser, contentDescription = null) },
            label = { Text("Profile") }
        )
    }
}
