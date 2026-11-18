package com.fitplan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitplan.app.ui.DayPlan
import com.fitplan.app.ui.previewPlan
import com.fitplan.app.ui.theme.FitPlanTheme
import kotlinx.coroutines.delay

@Composable
fun PlanScreen(plan: DayPlan?, onTimer: (Int) -> Unit, onRegenerate: () -> Unit, onGoal: () -> Unit) {
    var loading by remember { mutableStateOf(false) }
    LaunchedEffect(loading) {
        if (loading) {
            delay(250)
            onRegenerate()
            loading = false
        }
    }
    ScreenContent {
        if (loading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
            repeat(2) {
                Panel {
                    Text("Preparing activity…", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(16.dp))
                }
            }
        } else if (plan == null) {
            Panel { Text("No plan for today yet.") }
            PrimaryAction("Set Your Goal", onGoal)
        } else {
            Text("${plan.completed.size} of ${plan.items.size} activities completed")
            Text("${plan.items.sumOf { it.minutes }} min · ${plan.items.sumOf { it.kcal }} estimated kcal")
            if (plan.remaining == 0) Text("Calorie goal reached")
            plan.items.forEach { item ->
                Panel {
                    Text(item.name, style = MaterialTheme.typography.titleMedium)
                    Text("${item.minutes} min · ${item.kcal} kcal")
                    if (item.elapsedSeconds == null) {
                        TextButton({ onTimer(item.id) }) { Text("Start Activity") }
                    } else {
                        Text("✓ Completed", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
            Panel { Text("Progress: ${plan.burned} / ${plan.goal.kcal} kcal") }
            plan.items.firstOrNull { it.elapsedSeconds == null }?.let { next ->
                PrimaryAction("Start Next Activity", { onTimer(next.id) })
            }
            SecondaryAction("Regenerate Plan") { loading = true }
        }
    }
}

@Preview(widthDp = 360)
@Composable
private fun PlanPreview() = FitPlanTheme { PlanScreen(previewPlan, {}, {}, {}) }
