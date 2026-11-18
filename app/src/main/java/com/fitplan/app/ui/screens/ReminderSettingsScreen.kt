package com.fitplan.app.ui.screens

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.fitplan.app.ui.theme.FitPlanTheme
import java.util.Locale

@Composable
fun ReminderSettingsScreen(
    reminders: Boolean,
    time: String,
    goalAlerts: Boolean,
    onReminders: (Boolean) -> Unit,
    onTime: (String) -> Unit,
    onGoalAlerts: (Boolean) -> Unit,
    onClearHistory: () -> Unit
) {
    val context = LocalContext.current
    var confirmClear by rememberSaveable { mutableStateOf(false) }
    ScreenContent {
        Panel {
            SettingSwitch("Activity reminders", reminders, onReminders)
            OutlinedButton(
                onClick = {
                    TimePickerDialog(context, { _, hour, minute ->
                        onTime(String.format(Locale.US, "%02d:%02d", hour, minute))
                    }, time.substringBefore(':').toInt(), time.substringAfter(':').toInt(), true).show()
                },
                enabled = reminders
            ) { Text("Reminder time · $time") }
            SettingSwitch("Goal reached alerts", goalAlerts, onGoalAlerts)
        }
        SecondaryAction("Clear History") { confirmClear = true }
        Text("FitPlan · Group 04", style = MaterialTheme.typography.bodySmall)
    }
    if (confirmClear) {
        AlertDialog(
            onDismissRequest = { confirmClear = false },
            title = { Text("Clear history?") },
            text = { Text("Past days will be removed. Today's plan will be kept.") },
            confirmButton = {
                TextButton({ onClearHistory(); confirmClear = false }) { Text("Clear") }
            },
            dismissButton = { TextButton({ confirmClear = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun SettingSwitch(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, Modifier.weight(1f))
        Switch(checked, onChange)
    }
}

@Preview(widthDp = 360)
@Composable
private fun SettingsPreview() = FitPlanTheme {
    ReminderSettingsScreen(true, "18:00", false, {}, {}, {}, {})
}
