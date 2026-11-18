package com.fitplan.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

enum class FitnessLevel(val label: String) {
    Beginner("Beginner"), Intermediate("Intermediate"), Advanced("Advanced")
}

data class Goal(val kcal: Int, val minutes: Int, val level: FitnessLevel)

data class PlanItem(
    val id: Int,
    val name: String,
    val minutes: Int,
    val kcal: Int,
    val elapsedSeconds: Int? = null
)

data class DayPlan(val date: String, val goal: Goal, val items: List<PlanItem>) {
    val completed get() = items.filter { it.elapsedSeconds != null }
    val burned get() = completed.sumOf { it.kcal }
    val remaining get() = (goal.kcal - burned).coerceAtLeast(0)
    val fraction get() = (burned.toFloat() / goal.kcal).coerceIn(0f, 1f)
    val activeSeconds get() = completed.sumOf { it.elapsedSeconds ?: 0 }
}

private fun dateString(daysAgo: Int = 0): String {
    val day = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -daysAgo) }
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(day.time)
}

private val sampleGoal = Goal(200, 40, FitnessLevel.Intermediate)
internal val previewPlan = DayPlan(
    dateString(),
    sampleGoal,
    listOf(PlanItem(1, "Brisk Walk", 15, 68, 900), PlanItem(2, "Jog", 15, 120))
)

class DemoFitPlanState : ViewModel() {
    val today = dateString()
    val weekDates = (6 downTo 0).map(::dateString)
    var current by mutableStateOf<DayPlan?>(null)
        private set
    var history by mutableStateOf(
        listOf(
            DayPlan(dateString(1), sampleGoal, listOf(PlanItem(101, "Jog", 25, 200, 1500))),
            DayPlan(dateString(3), sampleGoal, listOf(PlanItem(102, "Brisk Walk", 30, 135, 1800)))
        )
    )
        private set
    var remindersEnabled by mutableStateOf(false)
    var reminderTime by mutableStateOf("18:00")
    var goalAlertsEnabled by mutableStateOf(false)
    private var nextItemId = 1

    fun planFor(date: String): DayPlan? = if (date == today) current else history.find { it.date == date }

    fun generate(goal: Goal, regenerate: Boolean = false) {
        val completed = current?.completed.orEmpty()
        val existing = current?.items.orEmpty()
        val available = (goal.minutes - completed.sumOf { it.minutes }).coerceAtLeast(0)
        val activities = when (goal.level) {
            FitnessLevel.Beginner -> listOf("Brisk Walk" to 4.5, "Stair Climb" to 7.0)
            FitnessLevel.Intermediate -> listOf("Brisk Walk" to 4.5, "Jog" to 8.0, "Stair Climb" to 7.0)
            FitnessLevel.Advanced -> listOf("Cardio Circuit" to 9.0, "Jog" to 8.0, "Stair Climb" to 7.0)
        }
        val previousFirst = existing.firstOrNull { it.elapsedSeconds == null }?.name
        val start = if (regenerate) (activities.indexOfFirst { it.first == previousFirst } + 1) % activities.size else 0
        var minutesLeft = available
        var caloriesLeft = (goal.kcal - completed.sumOf { it.kcal }).coerceAtLeast(0)
        val items = mutableListOf<PlanItem>()
        for (offset in activities.indices) {
            if (minutesLeft == 0 || caloriesLeft == 0) break
            val (name, rate) = activities[(start + offset) % activities.size]
            val needed = kotlin.math.ceil(caloriesLeft / rate).toInt()
            val limit = if (offset == activities.lastIndex) minutesLeft else minOf(minutesLeft, 15)
            val minutes = minOf(needed, limit)
            val kcal = (minutes * rate).roundToInt()
            items += PlanItem(nextItemId++, name, minutes, kcal)
            minutesLeft -= minutes
            caloriesLeft = (caloriesLeft - kcal).coerceAtLeast(0)
        }
        current = DayPlan(today, goal, completed + items)
    }

    fun finish(itemId: Int, elapsedSeconds: Int) {
        val plan = current ?: return
        current = plan.copy(items = plan.items.map { item ->
            if (item.id == itemId && item.elapsedSeconds == null) {
                item.copy(elapsedSeconds = elapsedSeconds.coerceIn(0, item.minutes * 60))
            } else {
                item
            }
        })
    }

    fun clearHistory() {
        history = emptyList()
    }
}
