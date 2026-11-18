package com.fitplan.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fitplan.app.ui.DayPlan
import com.fitplan.app.ui.previewPlan
import com.fitplan.app.ui.theme.FitPlanTheme

@Composable
fun HomeScreen(
    plan: DayPlan?,
    onPlan: () -> Unit,
    onEditGoal: () -> Unit,
    onProgress: () -> Unit,
    onHistory: () -> Unit,
    onWeekly: () -> Unit,
    onSettings: () -> Unit
) {
    ScreenContent {
        Text("Today's progress", style = MaterialTheme.typography.headlineSmall)
        if (plan == null) {
            Panel { Text("Set a daily goal to get started.") }
        } else {
            Text(plan.date, style = MaterialTheme.typography.bodyMedium)
            Box(Modifier.clickable(onClickLabel = "View Progress Details", onClick = onProgress)) {
                ProgressRing(plan.fraction, "${plan.burned}", "of ${plan.goal.kcal} kcal")
            }
            Panel {
                Text("${plan.remaining} kcal remaining")
                Text("${plan.completed.size} of ${plan.items.size} activities completed")
            }
            plan.items.firstOrNull { it.elapsedSeconds == null }?.let {
                Panel {
                    Text("Next activity", style = MaterialTheme.typography.titleMedium)
                    Text("${it.name} · ${it.minutes} min · ${it.kcal} kcal")
                }
            }
            PrimaryAction("View Today's Plan", onPlan)
            SecondaryAction("View Progress Details", onProgress)
        }
        SecondaryAction("Edit Goal / New Plan", onEditGoal)
        TextButton(onHistory, Modifier.fillMaxWidth()) { Text("History") }
        TextButton(onWeekly, Modifier.fillMaxWidth()) { Text("Weekly Statistics") }
        TextButton(onSettings, Modifier.fillMaxWidth()) { Text("Reminder Settings") }
    }
}

@Preview(widthDp = 360)
@Composable
private fun HomePreview() = FitPlanTheme { HomeScreen(previewPlan, {}, {}, {}, {}, {}, {}) }
