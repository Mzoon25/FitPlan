# FitPlan – Data Model (Room & DataStore)

This document defines the data FitPlan stores on the device. FitPlan is calorie-based: the user enters a calorie goal, available time and fitness level.

## 1. Room entities

### DailyRecordEntity
One row per day.
- date: String (Primary Key, yyyy-MM-dd)
- goalKcal: Int
- availableMinutes: Int
- fitnessLevel: String (BEGINNER / INTERMEDIATE / ADVANCED)

### PlanItemEntity
The activities of a day's plan. A completed activity is a plan item with `isDone = true`.
- id: Int (Primary Key, auto-generated)
- date: String (Foreign Key → DailyRecordEntity.date)
- activityName: String
- type: String (Walking / Cardio / Running / Stairs)
- minutes: Int
- estimatedKcal: Int
- isDone: Boolean
- completedAt: Long? (null until the activity is finished)

## 2. Relationship
One DailyRecordEntity has many PlanItemEntity rows (one day → many plan items).
History and weekly statistics are queries over these two tables (for example, the last 7 days and the sum of `estimatedKcal` where `isDone = true`).

## 3. DataStore (Preferences) keys
- last_goal_kcal: Int
- last_fitness_level: String
- reminders_enabled: Boolean
- reminder_time: String (HH:mm)
- goal_alerts_enabled: Boolean
- first_launch_done: Boolean

## 4. Domain models
The Domain layer has its own models (Activity, PlanItem, UserGoal, FitnessLevel, DailyRecord, WeeklySummary). The repository implementations in the Data layer map the Room entities and DataStore values to these models, so the UI and Domain never see Room classes.

## 5. Storage responsibilities
- **Room:** structured data (daily records, plan items, history).
- **DataStore:** small settings and preferences.
- **ActivityCatalog:** static list of activities and kcal rates, kept in code (not in the database).

## 6. Planned saving and update flow

### Creating a daily plan
When the user generates a plan, save the day's date, calorie goal, available time and fitness level in DailyRecordEntity. Save its activities in PlanItemEntity, linked to the same date. Both operations should run in one Room transaction so a partial plan is not saved.

### Completing an activity
When an activity is completed, update its existing PlanItemEntity by setting isDone to true and recording completedAt. Repeated completion requests must not create duplicate records or count the activity twice.

### Displaying progress
Calculate completed calories by summing estimatedKcal for completed plan items belonging to the selected date. Calculate remaining calories as max(0, goalKcal - completedCalories). These values are estimates, not measured calorie burn.

### Preserving daily history
Keep each day's saved goal and activities unchanged when the user changes their default preferences on another day. History and weekly statistics read the saved Room records rather than the latest DataStore goal.

### Updating the interface
Room query results are exposed through Flow. Repository implementations map the stored entities to Domain models, and ViewModels update the screen state. Screens do not access Room or DataStore directly.
