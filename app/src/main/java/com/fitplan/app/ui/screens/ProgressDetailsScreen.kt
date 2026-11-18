package com.fitplan.app.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fitplan.app.ui.DayPlan
import com.fitplan.app.ui.previewPlan
import com.fitplan.app.ui.theme.FitPlanTheme

@Composable
fun ProgressDetailsScreen(plan: DayPlan?, date: String, today: Boolean, onPlan: () -> Unit) {
    ScreenContent {
        Text(date, style = MaterialTheme.typography.titleMedium)
        if (plan == null) {
            Panel { Text("No progress for this date yet.") }
        } else {
            Panel {
                Text("${plan.burned} / ${plan.goal.kcal} kcal")
                LinearProgressIndicator(progress = { plan.fraction }, modifier = Modifier.fillMaxWidth())
                Text("${plan.remaining} kcal remaining")
                Text("Active time · ${formatTime(plan.activeSeconds)}")
            }
            Text("Completed activities", style = MaterialTheme.typography.titleMedium)
            if (plan.completed.isEmpty()) {
                Panel { Text("No completed activities yet.") }
            }
            plan.completed.forEach { item ->
                Panel { Text("✓ ${item.name} · ${item.kcal} kcal") }
            }
        }
        if (today) PrimaryAction("Back to Plan", onPlan)
    }
}

@Preview(widthDp = 360)
@Composable
private fun ProgressPreview() = FitPlanTheme {
    ProgressDetailsScreen(previewPlan, previewPlan.date, true) {}
}
