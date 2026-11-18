package com.fitplan.app.navigation

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.fitplan.app.ui.DemoFitPlanState
import com.fitplan.app.ui.screens.*
import com.fitplan.app.ui.theme.HeaderGreen

private enum class Tab(val route: String, val label: String, val symbol: String) {
    Home(Routes.HOME, "Home", "⌂"),
    Plan(Routes.PLAN, "Plan", "☷"),
    History(Routes.HISTORY, "History", "◷"),
    Settings(Routes.SETTINGS, "Settings", "⚙")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(state: DemoFitPlanState) {
    val navController = rememberNavController()
    val entry by navController.currentBackStackEntryAsState()
    val route = entry?.destination?.route
    val tab = Tab.entries.firstOrNull { it.route == route }
    val back = navController.previousBackStackEntry != null && tab == null && route != Routes.TIMER
    val title = when (route) {
        Routes.GOAL -> "Set Your Goal"
        Routes.PLAN -> "Today's Plan"
        Routes.TIMER -> "Activity Timer"
        Routes.PROGRESS -> "Progress Details"
        Routes.HISTORY -> "History"
        Routes.WEEKLY -> "Weekly Statistics"
        Routes.SETTINGS -> "Reminder Settings"
        else -> "FitPlan Dashboard"
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    if (back) {
                        TextButton({ navController.popBackStack() }) {
                            Text("Back", color = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HeaderGreen,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            if (tab != null) {
                NavigationBar {
                    Tab.entries.forEach { item ->
                        NavigationBarItem(
                            selected = item == tab,
                            onClick = {
                                if (item != tab) when (item) {
                                    Tab.Home -> navController.navigateToHome()
                                    Tab.Plan -> navController.navigateToPlan(state.today)
                                    Tab.History -> navController.navigateToHistory()
                                    Tab.Settings -> navController.navigateToSettings()
                                }
                            },
                            icon = { Text(item.symbol, style = MaterialTheme.typography.titleLarge) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.GOAL,
            modifier = Modifier.padding(padding).imePadding()
        ) {
            composable(Routes.GOAL) {
                GoalInputScreen(state.current?.goal) { goal ->
                    val firstPlan = state.current == null
                    state.generate(goal)
                    if (firstPlan) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.GOAL) { inclusive = true }
                        }
                    }
                    navController.navigateToPlan(state.today)
                }
            }
            composable(Routes.HOME) {
                HomeScreen(
                    plan = state.current,
                    onPlan = { navController.navigateToPlan(state.today) },
                    onEditGoal = navController::navigateToGoalInput,
                    onProgress = { navController.navigateToProgress(state.today) },
                    onHistory = navController::navigateToHistory,
                    onWeekly = navController::navigateToWeeklyStats,
                    onSettings = navController::navigateToSettings
                )
            }
            composable(Routes.PLAN, arguments = listOf(navArgument("date") { type = NavType.StringType })) { destination ->
                val date = requireNotNull(destination.arguments?.getString("date"))
                PlanScreen(
                    plan = state.planFor(date),
                    onTimer = navController::navigateToTimer,
                    onRegenerate = { state.current?.goal?.let { state.generate(it, regenerate = true) } },
                    onGoal = navController::navigateToGoalInput
                )
            }
            composable(Routes.TIMER, arguments = listOf(navArgument("planItemId") { type = NavType.IntType })) { destination ->
                val id = requireNotNull(destination.arguments).getInt("planItemId")
                val item = state.current?.items?.find { it.id == id && it.elapsedSeconds == null }
                TimerScreen(
                    item = item,
                    onFinish = { seconds ->
                        state.finish(id, seconds)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
            composable(Routes.PROGRESS, arguments = listOf(navArgument("date") { type = NavType.StringType })) { destination ->
                val date = requireNotNull(destination.arguments?.getString("date"))
                ProgressDetailsScreen(state.planFor(date), date, date == state.today) {
                    navController.navigateToPlan(state.today)
                }
            }
            composable(Routes.HISTORY) {
                HistoryScreen(state.history, navController::navigateToProgress, navController::navigateToWeeklyStats) {
                    navController.navigateToPlan(state.today)
                }
            }
            composable(Routes.WEEKLY) {
                WeeklyStatsScreen(state.weekDates, state.history + listOfNotNull(state.current)) {
                    navController.navigateToPlan(state.today)
                }
            }
            composable(Routes.SETTINGS) {
                ReminderSettingsScreen(
                    reminders = state.remindersEnabled,
                    time = state.reminderTime,
                    goalAlerts = state.goalAlertsEnabled,
                    onReminders = { state.remindersEnabled = it },
                    onTime = { state.reminderTime = it },
                    onGoalAlerts = { state.goalAlertsEnabled = it },
                    onClearHistory = state::clearHistory
                )
            }
        }
    }
}
