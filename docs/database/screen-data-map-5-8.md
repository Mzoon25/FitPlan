# Screen Data Mapping (Screens 5–8)

This document maps out the data read and written across screens 5 to 8 in the FitPlan application, along with their respective sources.

## Screen Data Mapping Table

| Screen | Reads | Writes | Source |
| :--- | :--- | :--- | :--- |
| **5 – Progress Details** | Calories burned, remaining calories, active time, completed activities | None | Room: `DailyRecordEntity`, `CompletedActivityEntity` |
| **6 – History** | Past daily goals, completed activities, and calories burned | None | Room: `DailyRecordEntity`, `CompletedActivityEntity` |
| **7 – Weekly Statistics** | Last 7 days’ activity data, total calories burned, and goals reached | None | Room: `DailyRecordEntity`, `CompletedActivityEntity` |
| **8 – Reminder Settings** | Current reminder settings and saved preferences | Reminder toggle, reminder time, goal alert settings, clear history action | DataStore: Preferences; Room: History data |

## Notes

* All data layers strictly follow the reactive Flow/StateFlow structure from the architecture.
* Settings and preferences persist safely through DataStore without cluttering the local Room database.
