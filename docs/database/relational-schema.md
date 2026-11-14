
# FitPlan – Relational Schema and Data Dictionary

## A3.1 DailyRecord

| Column | Type | Key | Null? | Constraints / Description |
|---|---|---|---|---|
| id | Long | PK | No | Auto-generated unique ID |
| localDate | String | -- | No | UNIQUE |
| targetKcal | Int | -- | No | Daily calorie goal (50–2000) |
| availableMinutes | Int | -- | No | Available time (10–240 minutes) |
| fitnessLevel | String | -- | No | Beginner, Intermediate, or Advanced |

## A3.2 PlanItem

| Column | Type | Key | Null? | Constraints / Description |
|---|---|---|---|---|
| id | Long | PK | No | Auto-generated unique ID |
| dailyRecordId | Long | FK | No | References DailyRecord.id; ON DELETE CASCADE |
| activityCode | String | -- | No | Matches ActivityCatalog code |
| durationMinutes | Int | -- | No | Activity duration in minutes; greater than 0 |
| estimatedKcal | Double | -- | No | Estimated calories burned; non-negative |
| isDone | Boolean | -- | No | Default false; true when completed |
| completedAt | Long | -- | Yes | Completion timestamp; null until completed |
| elapsedSeconds | Int | -- | Yes | Actual active seconds, excluding pauses; null until completed |

## Relationships and Constraints

- One DailyRecord can have many PlanItems (1:N).
- PlanItem.dailyRecordId references DailyRecord.id.
- ON DELETE CASCADE removes related PlanItems when a DailyRecord is deleted.
- DailyRecord.localDate must be unique.
- PlanItem.activityCode matches the code in the static ActivityCatalog (logical lookup, not a foreign key).
- completedAt and elapsedSeconds are nullable until the activity is completed.
- Settings are stored separately in DataStore and have no database foreign-key relationship.
  
