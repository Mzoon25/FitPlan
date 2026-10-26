# FitPlan - Navigation Plan

## 1. Purpose
This document describes the planned navigation between FitPlan's eight screens. Navigation will use Navigation Compose.

## 2. Screen destinations
| Screen | Purpose | Navigation arguments |
|---|---|---|
| Goal & Input | Enter calorie goal, available time and fitness level | None |
| Home Dashboard | Show today's summary and access app features | None |
| Daily Plan | Display the saved activities for a selected day | date |
| Activity Timer | Run the timer for a selected plan item | planItemId |
| Progress Details | Show completed and remaining estimated calories | date |
| History | Browse saved daily records | None |
| Weekly Statistics | Show a summary of saved weekly activity | None |
| Reminder Settings | Manage reminder preferences | None |

## 3. Planned navigation flow
- On first launch, open Goal & Input.
- On later launches, open Home Dashboard after loading saved preferences.
- After valid input and successful plan generation and saving, open Daily Plan.
- Home Dashboard provides access to Daily Plan, Progress Details, History, Weekly Statistics and Reminder Settings.
- Selecting an activity in Daily Plan opens Activity Timer using its planItemId.
- After successful activity completion, return to Daily Plan and display updated progress.
- Selecting a saved day in History opens Progress Details for that date.

## 4. Navigation and architecture
A NavHost will define the screen destinations. Screen composables receive navigation callbacks, and the navigation host uses NavController to change destinations.

ViewModels manage screen state and call Domain use cases. They do not hold a NavController. Screens do not access Room or DataStore directly.

Only small identifiers, such as date and planItemId, are passed between destinations. The destination ViewModel loads the required data through the repository interfaces.

## 5. Back navigation and data
Back navigation returns to the previous destination without deleting saved plans or history.

Navigation alone must not mark an activity as completed. Completion must be saved successfully before leaving the timer through the completion action.

Repeated navigation actions should avoid adding duplicate copies of the same destination to the back stack.

## 6. Planned verification
- Confirm all eight screens are reachable.
- Check Back navigation from each destination.
- Confirm a selected activity opens the correct timer.
- Confirm a selected history date shows that day's records.
- Confirm invalid input keeps the user on Goal & Input with an error.
- Confirm completion updates the existing activity without double-counting.
