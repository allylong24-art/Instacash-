package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.entity.GigTask
import com.example.data.entity.TaskCategory

@Composable
fun PostJobScreen(
    onPostTask: (String, String, TaskCategory, Double, Double, String, (GigTask) -> Unit) -> Unit,
    onJobCreated: (GigTask) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var payAmountText by remember { mutableStateOf("50.00") }
    var hoursText by remember { mutableStateOf("1.5") }
    var locationName by remember { mutableStateOf("Downtown, 1.5 mi away") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("📝 Post a Local Task", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Task Title") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Task Description") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = payAmountText, onValueChange = { payAmountText = it }, label = { Text("Escrow Pay Amount ($)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = hoursText, onValueChange = { hoursText = it }, label = { Text("Estimated Hours") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = locationName, onValueChange = { locationName = it }, label = { Text("Location Name") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                val pay = payAmountText.toDoubleOrNull() ?: 50.0
                val hrs = hoursText.toDoubleOrNull() ?: 1.0
                onPostTask(title, description, TaskCategory.YARD_WORK, pay, hrs, locationName, onJobCreated)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lock Escrow & Post Job")
        }
    }
}
