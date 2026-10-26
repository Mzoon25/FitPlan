# FitPlan - Timer and Reminders Plan
## 1. Purpose
This document describes the planned activity timer, reminder scheduling and notifications. These are
design decisions for Stage 2, not completed implementation or test results.
## 2. Responsibilities
- TimerViewModel manages the selected activity and timer screen state.
- Domain use cases handle activity completion through PlanRepository.
- SettingsViewModel manages reminder preferences through SettingsRepository.
- ReminderScheduler defines scheduling and cancellation operations.
- The Data layer implements reminders with WorkManager.
- A reminder Worker checks current preferences before posting a notification.
- Screens display state and send user actions. They do not access Room, DataStore or WorkManager
directly.
## 3. Timer state and behavior
The timer screen shows the selected activity, planned duration, remaining time, running or paused
status, and completion-saving status.
Start begins the timer. Pause freezes the remaining time. Resume continues from the paused value.
Remaining time is calculated from a monotonic timing reference and accumulated elapsed time.
Recomposition must not restart the timer or create additional timer jobs.
Reaching zero shows that the planned duration has ended. Reaching zero or navigating alone must not
create completion records.
## 4. State restoration
TimerViewModel preserves state across configuration changes. Small restoration values may be stored
in SavedStateHandle for system-initiated process recreation. A ViewModel alone does not survive
process death.
Proposed behavior: restore an interrupted session as paused and ask the user to resume. Do not
silently mark it completed. Durable plans and completion records remain in Room.
Timer timing does not use WorkManager. WorkManager is reserved for deferrable reminders.
## 5. Completing an activity
On confirmed completion, TimerViewModel calls the completion use case with planItemId. The repository
updates the existing PlanItemEntity: isDone becomes true and completedAt is recorded.
Repeated requests must not count the activity twice. Disable the completion action while saving. A
failed save shows an error and allows retry. A successful save allows navigation back to Daily Plan
with updated progress.
## 6. Reminder preferences
Store reminders_enabled, reminder_time and goal_alerts_enabled in DataStore.
Scheduling uses the latest preferences. Disabling reminders cancels pending work. Changing the time
replaces the previous schedule.
## 7. WorkManager scheduling
Use uniquely named reminder work to avoid duplicate schedules.
Schedule one-time work for the next selected local time. After execution, schedule the next daily
reminder only if reminders remain enabled.
The Worker rechecks preferences before posting. Recalculate scheduling when relevant time or time-
zone changes are detected.
WorkManager can be delayed by system conditions. The selected time is approximate, not an exact
alarm.
## 8. Notifications
Create a notification channel for reminders. Request notification permission on Android versions that
require it when the user enables notifications.
If permission is denied, explain that notifications cannot appear and keep core app features usable.
A daily reminder opens Home Dashboard. Keep its content brief and do not claim calories were
measured.
If goal alerts are enabled, notify only after saved completion data indicates that the daily goal is
reached. Avoid repeating the same goal alert for the same date; persist an alert marker so reopening
the app does not repeat it.
## 9. Planned verification
- Start, pause and resume preserve the correct remaining time.
- Recomposition and configuration changes do not restart the timer.
- An interrupted session follows the agreed restoration behavior.
- Repeated completion requests do not double-count progress.
- Failed saves show an error and allow retry.
- Changing reminder time replaces the previous schedule.
- Disabling reminders cancels pending work.
- Denied permission does not block core features.
- Reminder notifications open the intended screen.
- Goal alerts are not repeated for the same date.
