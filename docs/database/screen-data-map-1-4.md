# Screen-to-Data Mapping (Screens 5–8)

| Screen | Displayed Data / User Action | Entity / Storage | Key / Column | Read / Write |
| :--- | :--- | :--- | :--- | :--- |
| **5. Progress Details** | Daily target calories, remaining calories, completion percentage, and completed items count | `DailyRecordEntity`, `PlanItemEntity` | `DailyRecordEntity.targetKcal`, `PlanItemEntity.estimatedKcal`, `PlanItemEntity.isDone` | Read |
| **6. History** | Past dates, daily completion status, total completed calories, and completed activity logs with timestamps | `DailyRecordEntity`, `PlanItemEntity` | `DailyRecordEntity.localDate`, `PlanItemEntity.activityCode`, `PlanItemEntity.estimatedKcal`, `PlanItemEntity.isDone`, `PlanItemEntity.completedAt` | Read |
| **7. Weekly Statistics** | 7-day total calories burned, total active time, daily averages, and active days streak | `DailyRecordEntity`, `PlanItemEntity` | `DailyRecordEntity.localDate`, `PlanItemEntity.estimatedKcal`, `PlanItemEntity.elapsedSeconds`, `PlanItemEntity.isDone` | Read |
| **8. Reminder Settings** | Reminder enabled toggle, preferred reminder time, and selected notification days | `DataStore` (`Preferences`) | `reminder_enabled`, `reminder_time`, `reminder_days` | Read / Write |
