package com.fitplan.app.ui.screens

import android.os.SystemClock
import androidx.activity.compose.BackHandler
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import com.fitplan.app.ui.PlanItem
import com.fitplan.app.ui.theme.FitPlanTheme
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(item: PlanItem?, onFinish: (Int) -> Unit, onCancel: () -> Unit) {
    if (item == null) {
        ScreenContent {
            Text("This activity is no longer available.")
            PrimaryAction("Back to Plan", onCancel)
        }
        return
    }
    val totalMillis = item.minutes * 60_000L
    var runningSince by rememberSaveable(item.id) { mutableLongStateOf(SystemClock.elapsedRealtime()) }
    var accumulated by rememberSaveable(item.id) { mutableLongStateOf(0L) }
    var now by remember { mutableLongStateOf(SystemClock.elapsedRealtime()) }
    var confirmCancel by rememberSaveable { mutableStateOf(false) }
    val elapsed = (accumulated + if (runningSince != 0L) (now - runningSince).coerceAtLeast(0L) else 0L)
        .coerceAtMost(totalMillis)
    LaunchedEffect(runningSince) {
        if (runningSince != 0L) {
            while (now - runningSince + accumulated < totalMillis) {
                now = SystemClock.elapsedRealtime()
                delay(200)
            }
        }
    }
    BackHandler { confirmCancel = true }
    ScreenContent {
        Text(item.name, style = MaterialTheme.typography.headlineSmall)
        ProgressRing(
            (totalMillis - elapsed).toFloat() / totalMillis,
            formatTime(((totalMillis - elapsed + 999) / 1000).toInt()),
            "remaining"
        )
        Panel { Text("Estimated burn · ${item.kcal} kcal") }
        if (elapsed < totalMillis) {
            SecondaryAction(if (runningSince == 0L) "Resume" else "Pause") {
                now = SystemClock.elapsedRealtime()
                if (runningSince == 0L) {
                    runningSince = now
                } else {
                    accumulated = (accumulated + now - runningSince).coerceIn(0L, totalMillis)
                    runningSince = 0L
                }
            }
        }
        PrimaryAction("Finish Activity", {
            val activeMillis = accumulated + if (runningSince == 0L) 0L else SystemClock.elapsedRealtime() - runningSince
            onFinish((activeMillis.coerceIn(0L, totalMillis) / 1000).toInt())
        })
        SecondaryAction("Cancel Activity") { confirmCancel = true }
    }
    if (confirmCancel) {
        AlertDialog(
            onDismissRequest = { confirmCancel = false },
            title = { Text("Cancel activity?") },
            text = { Text("The activity will remain incomplete.") },
            confirmButton = { TextButton(onCancel) { Text("Cancel Activity") } },
            dismissButton = { TextButton({ confirmCancel = false }) { Text("Keep Going") } }
        )
    }
}

@Preview(widthDp = 360)
@Composable
private fun TimerPreview() = FitPlanTheme {
    TimerScreen(PlanItem(1, "Brisk Walk", 15, 68), {}, {})
}
