
# DataStore Keys – FitPlan

FitPlan uses Preferences DataStore to store default user inputs and reminder settings.

| Key | Type | Default Value | Description |
|---|---|---|---|
| target_kcal | Int | 500 | Default daily calorie goal |
| available_minutes | Int | 45 | Default available minutes |
| fitness_level | String | Beginner | Default fitness level |
| activity_reminders_enabled | Boolean | false | Enables or disables activity reminders |
| reminder_time | String | 18:00 | Scheduled reminder time |
| goal_reached_alerts_enabled | Boolean | false | Enables or disables goal-reached alerts |
| is_first_launch | Boolean | true | Indicates whether the app is being launched for the first time |

## Notes

- DataStore stores default inputs and reminder preferences.
- Daily plans and historical records are stored in Room.
- Changing default preferences does not modify previous daily records.
- Default values were proposed and approved by the team. The implementation should follow these values.
