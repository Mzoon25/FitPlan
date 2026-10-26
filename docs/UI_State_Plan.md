# FitPlan - UI and State Plan

## 1. Purpose
This document describes the planned Compose screens, their state owners and how user actions update the interface.

## 2. Screens and state ownership
| Screen | ViewModel | Main UI state |
|---|---|---|
| Goal & Input | GoalViewModel | Calorie goal, available minutes, fitness level, validation errors and saving status |
| Home Dashboard | ProgressViewModel | Today's goal, completed calories, remaining calories and loading status |
| Daily Plan | PlanViewModel | Selected date, activity list, completion status and errors |
| Activity Timer | TimerViewModel | Selected activity, remaining time, running or paused status and completion-saving status |
| Progress Details | ProgressViewModel | Selected date, goal, completed calories and remaining calories |
| History | HistoryViewModel | Saved daily records, loading status and empty state |
| Weekly Statistics | HistoryViewModel | Selected week, weekly totals and empty state |
| Reminder Settings | SettingsViewModel | Reminder preferences, selected time and saving status |

ViewModel instances are scoped to their screen or navigation graph. Reusing a ViewModel class does not automatically mean sharing the same instance.

## 3. Unidirectional data flow
Each screen receives its UiState and sends user actions to its ViewModel. The ViewModel calls Domain use cases and observes repository data. It updates UiState, and Compose displays the latest state. Screens do not read Room or DataStore directly.

For example, Goal & Input sends input changes to GoalViewModel. When the user selects Generate, the ViewModel validates the values and shows field errors if needed. For valid input, it starts plan generation and saving while the UI displays a busy state. After a successful save, the UI handles navigation through its navigation callback. A failure keeps the user on the screen with an error and a retry action.

## 4. Choosing where state lives
- remember: temporary UI details that only need to survive recomposition.
- rememberSaveable: small UI values, such as an expanded section, that should survive supported state restoration.
- ViewModel: screen state and ongoing screen logic across configuration changes.
- SavedStateHandle: small values needed to reconstruct ViewModel state after system-initiated process recreation.
- Room: saved plans, completed activities and daily history.
- DataStore: saved preferences and reminder settings.

Each value has one clear state owner. The same input should not be managed independently in both the composable and the ViewModel.

## 5. Loading, empty and error states
- Loading: show progress while reading or saving data and prevent duplicate submissions.
- Empty: explain when no daily plan or history exists and show the relevant next action.
- Error: explain the failed action and offer retry where appropriate.
- Success: show the saved plan or updated progress from the latest stored data.

## 6. Timer and completion
Recomposition must not restart the timer. Remaining time should be calculated from a timing reference and accumulated paused time, rather than relying only on decrementing a counter.

A ViewModel alone does not preserve a timer after process death. The implementation must define which timing details are saved and how an interrupted session is restored.

Completing an activity sends one action to TimerViewModel. While saving, the completion button is disabled. A failed save shows an error; a successful save allows navigation back to the plan.

## 7. Planned UI verification
- Check each screen against the agreed wireframes.
- Check loading, empty, error and success states.
- Confirm input errors are shown beside the relevant fields.
- Confirm configuration changes do not reset ViewModel state.
- Confirm repeated taps do not generate or complete an activity twice.
- Confirm saved progress and history remain available after reopening the app.
