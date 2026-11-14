

# FitPlan – Room Database Plan

## A4.1 Entities

| Entity class | Table | Purpose |
|---|---|---|
| DailyRecordEntity | DailyRecord | Stores each day's calorie goal, available time, and fitness level as a historical snapshot. |
| PlanItemEntity | PlanItem | Stores planned activities, estimated calories, completion status, completion timestamp, and actual elapsed activity time for each day. |

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
- Deleting a DailyRecord automatically deletes its related PlanItems.
- ActivityCatalog is a static list, not a Room table.
- Settings are stored in DataStore, not Room.
- completedAt and elapsedSeconds are null before activity completion.

## A4.2 DAOs and Queries

| DAO | Method | Query / purpose |
|---|---|---|
| DailyRecordDao | insertDayWithItems() | Inserts a DailyRecord and its PlanItems together in one Room transaction to maintain data consistency. |
| PlanItemDao | getPlanForDay() | Returns the selected day's PlanItems as a Flow for automatic UI updates. |
| PlanItemDao | markItemDone() | Updates isDone, completedAt, and elapsedSeconds only if the activity is not already completed. |
| DailyRecordDao | getLast7Days() | Retrieves daily records for today and the previous six calendar days, ordered by localDate, for History and Weekly Statistics. |
| PlanItemDao | getBurnedKcalPerDay() | Calculates SUM(estimatedKcal) for completed activities only, using isDone = true. |
| DailyRecordDao | clearPastDays() | Deletes records before today's date while preserving today's record and its PlanItems. Related PlanItems are deleted through CASCADE. |

### Query and Transaction Notes

- insertDayWithItems() uses a Room transaction to ensure that the DailyRecord and its associated PlanItems are saved consistently.
- getPlanForDay() uses Flow to provide reactive updates when the stored plan changes.
- markItemDone() prevents duplicate completion by updating only activities where isDone is false.
- When an activity is completed, isDone becomes true, completedAt stores the completion timestamp, and elapsedSeconds stores the actual active duration excluding pauses.
- getLast7Days() uses localDate to retrieve records within the seven-day calendar range, including today.
- getBurnedKcalPerDay() includes only completed activities in the total estimated calories burned.
- clearPastDays() preserves the current day's record and removes earlier records, with related PlanItems deleted through ON DELETE CASCADE.

## A4.3 Database Class and TypeConverters

### Database class

FitPlanDatabase extends RoomDatabase and includes DailyRecordEntity and PlanItemEntity.

It provides access to DailyRecordDao and PlanItemDao for managing daily records and their associated plan items.

The database uses Room transactions to save DailyRecord and PlanItem records consistently.

The relationship between DailyRecordEntity and PlanItemEntity is one-to-many.

The foreign key in PlanItemEntity references DailyRecord.id and uses ON DELETE CASCADE to automatically delete related PlanItems when their parent DailyRecord is removed.

### TypeConverters

No custom TypeConverters are required for the current database schema.

- localDate is stored as a String representing the calendar date.
- completedAt is stored as a nullable Long representing the activity completion timestamp.
- elapsedSeconds is stored as a nullable Int representing actual elapsed activity time in seconds, excluding pauses.
- isDone is stored as a Boolean indicating whether the activity has been completed.
- estimatedKcal is stored as a Double representing estimated calories burned.

Room supports these primitive and nullable data types without requiring custom TypeConverters.

If complex data types such as LocalDate or Date are introduced in future implementations, appropriate TypeConverters will be added.

## Schema Consistency Review

The Room Database Plan follows the updated A3.1 and A3.2 database design.

### Entity and Field Consistency

- DailyRecordEntity maps to the DailyRecord table.
- PlanItemEntity maps to the PlanItem table.
- DailyRecord contains five fields: id, localDate, targetKcal, availableMinutes, and fitnessLevel.
- PlanItem contains eight fields: id, dailyRecordId, activityCode, durationMinutes, estimatedKcal, isDone, completedAt, and elapsedSeconds.
- id and dailyRecordId use Long.
- estimatedKcal uses Double.
- completedAt uses nullable Long to store the activity completion timestamp.
- elapsedSeconds uses nullable Int to store actual active seconds excluding pauses.
- completedAt and elapsedSeconds remain null before activity completion.
- ActivityCatalog remains a static list rather than a Room table.
- Settings and preferences are stored in DataStore.

### Relationship and Query Consistency

- DailyRecord and PlanItem have a one-to-many relationship.
- PlanItem.dailyRecordId references DailyRecord.id.
- The relationship uses ON DELETE CASCADE.
- Daily records and their PlanItems are inserted together using a Room transaction.
- markItemDone() updates isDone, completedAt, and elapsedSeconds while preventing duplicate completion.
- getPlanForDay() uses Flow for reactive updates.
- getLast7Days() retrieves records for the current seven-day calendar period, including today.
- getBurnedKcalPerDay() calculates estimated calories for completed activities only.
- clearPastDays() removes previous days while preserving today's record and related PlanItems.

### Final Integration Check

The updated Room Database Plan is aligned with the clarified A3.1 and A3.2 design, including the separation of completion timestamps and actual elapsed activity time.

Before final merging, the team should confirm that A1, A3.2, A4, the ER Diagram, the Relational Schema, and the actual Room implementation use consistent field names, data types, relationships, and query behavior.
