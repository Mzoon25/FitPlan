package com.fitplan.app.navigation

import androidx.navigation.NavHostController

object Routes {
    const val GOAL = "goal_input"
    const val HOME = "home"
    const val PLAN = "plan/{date}"
    const val TIMER = "timer/{planItemId}"
    const val PROGRESS = "progress/{date}"
    const val HISTORY = "history"
    const val WEEKLY = "weekly_stats"
    const val SETTINGS = "settings"
}

fun NavHostController.navigateToGoalInput() = navigate(Routes.GOAL) { launchSingleTop = true }

fun NavHostController.navigateToHome() = navigate(Routes.HOME) {
    popUpTo(Routes.HOME) { inclusive = false }
    launchSingleTop = true
}

fun NavHostController.navigateToPlan(date: String) = navigate("plan/$date") {
    popUpTo(Routes.HOME) { inclusive = false }
    launchSingleTop = true
}

fun NavHostController.navigateToTimer(planItemId: Int) = navigate("timer/$planItemId") {
    launchSingleTop = true
}

fun NavHostController.navigateToProgress(date: String) = navigate("progress/$date") {
    launchSingleTop = true
}

fun NavHostController.navigateToHistory() = navigate(Routes.HISTORY) {
    popUpTo(Routes.HOME) { inclusive = false }
    launchSingleTop = true
}

fun NavHostController.navigateToWeeklyStats() = navigate(Routes.WEEKLY) {
    launchSingleTop = true
}

fun NavHostController.navigateToSettings() = navigate(Routes.SETTINGS) {
    popUpTo(Routes.HOME) { inclusive = false }
    launchSingleTop = true
}
