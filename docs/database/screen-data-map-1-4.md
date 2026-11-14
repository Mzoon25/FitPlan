A5.1 Screens 1–4

| Screen | Reads | Writes | Source |
| --- | --- | --- | --- |
| 1 Goal & Input | Saved default inputs (`targetKcal`, `availableMinutes`, `fitnessLevel`), if available. | Saves validated default inputs (`targetKcal`, `availableMinutes`, `fitnessLevel`) when the user taps **Generate My Plan**. | DataStore (Preferences) |
| 2 Home Dashboard | Today’s calorie goal (`targetKcal`) and plan items (`activityCode`, `estimatedKcal`, `isDone`). Uses these to show estimated calories burned, remaining calories, completed activity count, and the next activity. | None | Room (`DailyRecordEntity`, `PlanItemEntity`) |
| 3 Daily Plan | Today’s plan inputs (`targetKcal`, `availableMinutes`, `fitnessLevel`) and saved plan items (`activityCode`, `durationMinutes`, `estimatedKcal`, `isDone`). Uses the activity catalog when generating or regenerating the plan. | Creates today’s `DailyRecordEntity` and its `PlanItemEntity` records on first generation. When the user taps **Regenerate Plan**, replaces unfinished plan items and keeps completed items. | Room (`DailyRecordEntity`, `PlanItemEntity`) |
| 4 Activity Timer | The selected plan item’s `id`, `durationMinutes`, and `estimatedKcal`. | On **Finish**, updates the selected plan item by setting `isDone` to true and recording `completedAt`. | Room (`PlanItemEntity`) |
