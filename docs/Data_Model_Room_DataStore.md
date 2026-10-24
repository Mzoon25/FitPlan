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
