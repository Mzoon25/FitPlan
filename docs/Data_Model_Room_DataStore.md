# FitPlan – Data Model (Room & DataStore)

This document defines the data model used by FitPlan, including Room entities, relationships, and DataStore preferences.

## 1. Room Entities

### UserProfile
Stores basic user information and planning preferences.

- userId: Int (Primary Key)
- name: String
- dailyGoalMinutes: Int

### Activity
Stores activities that can be added to a daily plan.

- activityId: Int (Primary Key)
- title: String
- category: String
- durationMinutes: Int
- description: String

### DailyPlan
Stores the user's daily plans.

- planId: Int (Primary Key)
- date: String
- userId: Int (Foreign Key)

### PlanActivity
Connects activities to a daily plan.

- planActivityId: Int (Primary Key)
- planId: Int (Foreign Key)
- activityId: Int (Foreign Key)
- isCompleted: Boolean

## 2. Relationships

- One UserProfile can have many DailyPlan records.
- One DailyPlan can contain many PlanActivity records.
- Each PlanActivity references one Activity.
- PlanActivity connects DailyPlan and Activity.

## 3. DataStore Preferences

DataStore is used for lightweight application settings and user preferences.

The following preferences are stored:

- Dark mode enabled/disabled.
- Notifications enabled/disabled.
- Reminder time.
- Default daily activity goal.
- First-launch/onboarding completion status.

## 4. Storage Responsibilities

**Room:** Stores structured and persistent application data such as activities, daily plans, and completion history.

**DataStore:** Stores small user preferences and application settings.

This separation keeps FitPlan's structured data organized in Room while lightweight settings are managed efficiently with DataStore.
