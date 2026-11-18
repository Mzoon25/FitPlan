package com.fitplan.app.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fitplan.app.ui.DayPlan
import com.fitplan.app.ui.previewPlan
import com.fitplan.app.ui.theme.FitPlanTheme

@Composable
fun HistoryScreen(days: List<DayPlan>, onDay: (String) -> Unit, onWeekly: () -> Unit, onPlan: () -> Unit) {
    ScreenContent {
        if (days.isEmpty()) {
            Panel { Text("No past days yet") }
            PrimaryAction("Go to Today's Plan", onPlan)
        }
        days.forEach { day ->
            Panel {
                Text(day.date, style = MaterialTheme.typography.titleMedium)
                Text("${day.burned} / ${day.goal.kcal} kcal · ${day.completed.size} activities")
                if (day.remaining == 0) Text("✓ Goal reached")
                TextButton({ onDay(day.date) }) { Text("View Progress") }
            }
        }
        SecondaryAction("View Weekly Statistics", onWeekly)
    }
}

@Preview(widthDp = 360)
@Composable
private fun HistoryPreview() = FitPlanTheme { HistoryScreen(listOf(previewPlan), {}, {}, {}) }
