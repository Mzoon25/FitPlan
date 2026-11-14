# Screen Data Mapping (Screens 5–8)

This document maps out the data read and written across screens 5 to 8 in the FitPlan application, along with their respective sources.

## Screen Data Mapping Table

| Screen | Reads | Writes | Source |
| :--- | :--- | :--- | :--- |
| **5 – Progress Details** | Today’s calorie goal (`targetKcal`), estimated calories burned and remaining (`estimatedKcal`), active time (`elapsedSeconds`), and completed activities (`isDone`) | None | Room: `DailyRecordEntity`, `PlanItemEntity` |
| **6 – History** | Past daily goals (`localDate`, `targetKcal`), completed activities (`activityCode`, `isDone`), completion timestamps (`completedAt`), and calories burned (`estimatedKcal`) | None | Room: `DailyRecordEntity`, `PlanItemEntity` |
| **7 – Weekly Statistics** | Last 7 days’ activity data (`localDate`), total calories burned (`estimatedKcal`), active time (`elapsedSeconds`), and completed goals (`isDone`, `targetKcal`) | None | Room: `DailyRecordEntity`, `PlanItemEntity` |
| **8 – Reminder Settings** | Current reminder settings and saved preferences | Reminder toggle, reminder time, goal alert settings, clear history action | DataStore: Preferences; Room: `DailyRecordEntity`, `PlanItemEntity` |

## Notes

* All data layers strictly follow the reactive Flow/StateFlow structure from the architecture.
* Completion status and activity metrics are tracked directly within `PlanItemEntity` via `isDone`, `completedAt`, and `elapsedSeconds`.
* Settings and preferences persist safely through DataStore without cluttering the local Room database.
