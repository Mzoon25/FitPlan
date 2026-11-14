A5.2 Screens 5–8

Screen	Reads	Writes	Source
5 Progress Details	Today’s calorie goal (targetKcal), estimated calories burned and remaining (estimatedKcal), active time (elapsedSeconds), and completed activities (isDone).	None	Room (DailyRecordEntity, PlanItemEntity)
6 History	Past daily goals (localDate, targetKcal), completed activities (activityCode, isDone), active time (elapsedSeconds), completion timestamp (completedAt), and calories burned (estimatedKcal).	None	Room (DailyRecordEntity, PlanItemEntity)
7 Weekly Statistics	Last 7 days’ activity data (localDate), total calories burned (estimatedKcal), active time (elapsedSeconds), completion timestamps (completedAt), and completed goals (isDone, targetKcal).	None	Room (DailyRecordEntity, PlanItemEntity)
8 Reminder Settings	Current reminder settings (activity_reminders_enabled, reminder_time, goal_reached_alerts_enabled).	Updates reminder toggle (activity_reminders_enabled), reminder time (reminder_time), and goal alert settings (goal_reached_alerts_enabled). Supports Clear History action.	DataStore (Preferences); Room (DailyRecordEntity, PlanItemEntity)
