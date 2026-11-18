package com.fitplan.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitplan.app.ui.DayPlan
import com.fitplan.app.ui.previewPlan
import com.fitplan.app.ui.theme.FitPlanTheme

@Composable
fun WeeklyStatsScreen(dates: List<String>, days: List<DayPlan>, onPlan: () -> Unit) {
    val totals = days.filter { it.date in dates }.associateBy { it.date }
    ScreenContent {
        Text("Last 7 days", style = MaterialTheme.typography.titleLarge)
        if (totals.values.none { it.completed.isNotEmpty() }) {
            Panel { Text("No past days yet") }
            PrimaryAction("Go to Today's Plan", onPlan)
        } else {
            val maximum = (totals.values.maxOfOrNull { it.burned } ?: 0).coerceAtLeast(1)
            Panel {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    dates.forEach { date ->
                        val kcal = totals[date]?.burned ?: 0
                        val fraction = kcal.toFloat() / maximum
                        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("$kcal", style = MaterialTheme.typography.labelSmall)
                            Box(
                                Modifier.fillMaxWidth().height(110.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                if (kcal > 0) {
                                    Box(
                                        Modifier.fillMaxWidth().fillMaxHeight(fraction)
                                            .background(MaterialTheme.colorScheme.primary)
                                    )
                                }
                            }
                            Text(date.takeLast(2), style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
                Text("Estimated kcal per day", style = MaterialTheme.typography.bodySmall)
            }
            Panel {
                Text("Total burned · ${totals.values.sumOf { it.burned }} kcal")
                Text("Activities completed · ${totals.values.sumOf { it.completed.size }}")
                Text("Goals reached · ${totals.values.count { it.remaining == 0 }}")
            }
        }
    }
}

@Preview(widthDp = 360)
@Composable
private fun WeeklyPreview() = FitPlanTheme {
    WeeklyStatsScreen(listOf(previewPlan.date), listOf(previewPlan)) {}
}
