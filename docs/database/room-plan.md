
# FitPlan – Room Database Plan

## A4.1 Entities

| Entity class | Table | Purpose |
|---|---|---|
| DailyRecordEntity | DailyRecord | Stores each day's calorie goal, available time, and fitness level as a historical snapshot. |
| PlanItemEntity | PlanItem | Stores planned activities, estimated calories, completion status, completion timestamp, and actual elapsed time for each day. |

### DailyRecordEntity fields

| Column | Type | Constraints |
|---|---|---|
| id | Long | Primary key, auto-generated |
| localDate | String | Non-null, unique |
| targetKcal | Int | Non-null, 50–2000 |
| availableMinutes | Int | Non-null, 10–240 |
| fitnessLevel | String | Non-null; Beginner, Intermediate, Advanced |

### PlanItemEntity fields

| Column | Type | Constraints |
|---|---|---|
| id | Long | Primary key, auto-generated |
| dailyRecordId | Long | Non-null foreign key to DailyRecord.id |
| activityCode | String | Non-null; matches static ActivityCatalog code |
| durationMinutes | Int | Non-null, greater than zero |
| estimatedKcal | Double | Non-null, non-negative |
| isDone | Boolean | Non-null, default false |
| completedAt | Long? | Nullable; activity completion timestamp |
| elapsedSeconds | Int? | Nullable; actual elapsed activity time in seconds, excluding pauses |

### Relationships

- DailyRecord has a one-to-many relationship with PlanItem.
- PlanItem.dailyRecordId references DailyRecord.id.
- The foreign key uses ON DELETE CASCADE.
- ActivityCatalog is a static list, not a Room table.
- Settings are stored in DataStore, not Room.

## A4.2 DAOs and Queries

| DAO | Method | Query / purpose |
|---|---|---|
| DailyRecordDao | insertDayWithItems() | Inserts a DailyRecord and its PlanItems in one Room transaction. |
| PlanItemDao | getPlanForDay() | Returns the selected day's PlanItems as a Flow for automatic UI updates. |
| PlanItemDao | markItemDone() | Updates isDone and completedAt only if the activity is not already completed. |
| DailyRecordDao | getLast7Days() | Retrieves the last seven daily records ordered by localDate for History and Weekly Statistics. |
| PlanItemDao | getBurnedKcalPerDay() | Calculates SUM(estimatedKcal) for completed activities only. |
| DailyRecordDao | clearPastDays() | Deletes records before today's date. Related PlanItems are deleted through CASCADE. |

## A4.3 Database Class and TypeConverters

### Database class

FitPlanDatabase extends RoomDatabase and includes DailyRecordEntity and PlanItemEntity. It provides access to DailyRecordDao and PlanItemDao. The database uses Room transactions to save daily records with their plan items consistently.

The foreign key in PlanItemEntity uses ON DELETE CASCADE to automatically delete related activities when a daily record is removed.

### TypeConverters

No custom TypeConverters are required for the updated schema. localDate is stored as a String, completedAt as a nullable Long representing the activity completion timestamp, elapsedSeconds as a nullable Int representing actual elapsed activity time in seconds excluding pauses, and isDone as a Boolean supported by Room. If localDate or other complex types are introduced later, appropriate TypeConverters will be added.

## Schema Consistency Review

The updated PlanItem schema includes both completedAt and elapsedSeconds, consistent with the team's clarified design.

- completedAt is a nullable Long representing the activity completion timestamp.
- elapsedSeconds is a nullable Int representing the actual elapsed activity time in seconds, excluding pauses.
- The relationship between DailyRecordEntity and PlanItemEntity uses ON DELETE CASCADE.

The team should verify that A1, A3.2, A4, and the implementation use the same field names and data types before final approval and merging.
