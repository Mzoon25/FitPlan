# FitPlan – Room Database Plan

## A4.1 Entities

| Entity class | Table | Purpose |
|---|---|---|
| DailyRecordEntity | DailyRecord | Stores each day's calorie goal, available time, and fitness level as a historical snapshot. |
| PlanItemEntity | PlanItem | Stores planned activities, estimated calories, completion status, and elapsed time for each day. |

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
| completedAt | Int? | Nullable; active seconds at completion according to current A3.2 |

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

No custom TypeConverters are required for the current A3.2 schema. localDate is stored as a String, completedAt as a nullable Int representing elapsed seconds, and isDone as a Boolean supported by Room. If localDate or other complex types are introduced later, appropriate TypeConverters will be added.

## Pending schema consistency review

The A1 ER diagram contains both completedAt and elapsedSeconds, while the current A3.2 table contains only completedAt. The team must confirm the final field names and types before merging the Pull Request. If elapsedSeconds is adopted as a separate field, A3.2, A4, and this document must be updated consistently.
