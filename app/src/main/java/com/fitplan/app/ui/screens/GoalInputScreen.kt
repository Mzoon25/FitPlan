package com.fitplan.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitplan.app.ui.FitnessLevel
import com.fitplan.app.ui.Goal
import com.fitplan.app.ui.theme.FitPlanTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GoalInputScreen(savedGoal: Goal?, onGenerate: (Goal) -> Unit) {
    var kcal by rememberSaveable { mutableStateOf(savedGoal?.kcal?.toString().orEmpty()) }
    var minutes by rememberSaveable { mutableStateOf(savedGoal?.minutes?.toString().orEmpty()) }
    var level by rememberSaveable { mutableStateOf(savedGoal?.level ?: FitnessLevel.Beginner) }
    val kcalValue = kcal.toIntOrNull()?.takeIf { it in 50..2000 }
    val minutesValue = minutes.toIntOrNull()?.takeIf { it in 10..240 }
    ScreenContent {
        Text("Plan your activity for today", style = MaterialTheme.typography.titleMedium)
        GoalField(kcal, { kcal = it }, "Daily calorie goal (kcal)", "Enter 50–2000 kcal", kcalValue == null)
        GoalField(minutes, { minutes = it }, "Available time (minutes)", "Enter 10–240 minutes", minutesValue == null)
        Text("Fitness level", style = MaterialTheme.typography.titleMedium)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FitnessLevel.entries.forEach { option ->
                FilterChip(level == option, { level = option }, label = { Text(option.label) })
            }
        }
        Panel { Text("Tip: Choose a goal and an activity level that fit the time you have today.") }
        PrimaryAction("Generate My Plan", {
            if (kcalValue != null && minutesValue != null) onGenerate(Goal(kcalValue, minutesValue, level))
        }, enabled = kcalValue != null && minutesValue != null)
    }
}

@Composable
private fun GoalField(value: String, update: (String) -> Unit, label: String, hint: String, invalid: Boolean) {
    OutlinedTextField(
        value = value,
        onValueChange = update,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        supportingText = { Text(hint) },
        isError = value.isNotBlank() && invalid,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview(widthDp = 360)
@Composable
private fun GoalPreview() = FitPlanTheme { GoalInputScreen(null) {} }
