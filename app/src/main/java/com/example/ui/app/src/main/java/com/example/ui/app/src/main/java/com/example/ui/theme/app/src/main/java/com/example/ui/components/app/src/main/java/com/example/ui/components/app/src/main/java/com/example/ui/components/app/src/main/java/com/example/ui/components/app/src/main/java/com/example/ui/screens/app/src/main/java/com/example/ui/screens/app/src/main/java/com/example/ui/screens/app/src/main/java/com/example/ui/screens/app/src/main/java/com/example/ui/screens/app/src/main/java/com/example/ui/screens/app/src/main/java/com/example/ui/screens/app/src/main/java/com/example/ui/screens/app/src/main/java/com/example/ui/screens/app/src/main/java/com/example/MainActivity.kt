package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.ui.InstantCashViewModel
import com.example.ui.UserMode
import com.example.ui.components.AppBottomNavBar
import com.example.ui.components.AppHeaderBar
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val viewModel: InstantCashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                val currentRole by viewModel.userMode.collectAsState()
                val allTasks by viewModel.allTasks.collectAsState()
                val transactions by viewModel.transactions.collectAsState()
                val notifications by viewModel.notifications.collectAsState()
                val isCashOutLoading by viewModel.isCashOutLoading.collectAsState()

                Scaffold(
                    topBar = {
                        AppHeaderBar(
                            currentRoleName = if (currentRole == UserMode.WORKER) "Worker" else "Customer",
                            onToggleRole = { viewModel.toggleUserMode() },
                            onNotificationsClick = { navController.navigate("notifications") }
                        )
                    },
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route ?: "home"
                        AppBottomNavBar(currentRoute = currentRoute, onNavigate = { navController.navigate(it) })
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            if (currentRole == UserMode.WORKER) {
                                WorkerHomeScreen(
                                    tasks = allTasks,
                                    onSelectTask = { task ->
                                        viewModel.selectTask(task)
                                        navController.navigate("job_detail/${task.id}")
                                    }
                                )
                            } else {
                                CustomerDashboardScreen(
                                    postedTasks = allTasks,
                                    onPostNewJobClick = { navController.navigate("post_job") },
                                    onSelectTask = { task ->
                                        viewModel.selectTask(task)
                                        navController.navigate("job_detail/${task.id}")
                                    }
                                )
                            }
                        }

                        composable("post_job") {
                            PostJobScreen(
                                onPostTask = { title, desc, cat, pay, hrs, loc, onCreated ->
                                    viewModel.postNewJob(title, desc, cat, pay, hrs, loc) { task ->
                                        onCreated(task)
                                        navController.navigate("job_detail/${task.id}")
                                    }
                                },
                                onJobCreated = { }
                            )
                        }

                        composable("job_detail/{taskId}") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                            val task by viewModel.getTaskById(taskId).collectAsState(initial = null)

                            JobDetailScreen(
                                task = task,
                                onAcceptJob = { viewModel.acceptJob(it) },
                                onSubmitProof = { id, note, url -> viewModel.submitProof(id, note, url) },
                                onApprovePayout = { viewModel.approveProofAndPayout(it) },
                                onOpenChat = { navController.navigate("chat/$it") }
                            )
                        }

                        composable("wallet") {
                            WalletScreen(
                                transactions = transactions,
                                isCashOutLoading = isCashOutLoading,
                                onCashOutConfirm = { amount, type, acc, onComplete ->
                                    viewModel.performInstantCashOut(amount, type, acc, onComplete)
                                }
                            )
                        }

                        composable("chat/{taskId}") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
                            val messages by viewModel.getChatMessages(taskId).collectAsState(initial = emptyList())

                            ChatScreen(
                                messages = messages,
                                onSendMessage = { text -> viewModel.sendChatMessage(taskId, text) }
                            )
                        }

                        composable("dispute") { DisputeCenterScreen() }
                        composable("profile") { ProfileVerificationScreen() }
                        composable("notifications") { NotificationsScreen(notifications = notifications) }
                    }
                }
            }
        }
    }
}
