# FitPlan – Domain Logic Plan

## 1. Purpose

This document describes the planned Domain layer logic for FitPlan, including daily plan generation, calorie estimation, activity completion, and progress calculation.

The Domain layer contains the application's business rules. It is designed to remain independent of Android UI components, Room, and DataStore.

## 2. Domain Layer Responsibilities

The Domain layer is responsible for:

- Generating a daily activity plan from the user's calorie goal, available time, and fitness level.
- Estimating calories for each planned activity.
- Handling activity completion through a dedicated use case.
- Calculating daily completed and remaining calories.
- Defining repository interfaces used to access saved plans and activity records.

The UI layer communicates with Domain use cases through ViewModels. The Data layer implements the repository interfaces and handles local persistence.

## 3. Planned Use Cases

### 3.1 GeneratePlanUseCase

**Purpose:** Generate a daily activity plan using the user's inputs.

**Inputs:**
- Daily calorie goal (`goalKcal`)
- Available time (`availableMinutes`)
- Fitness level (`fitnessLevel`): BEGINNER, INTERMEDIATE, or ADVANCED

**Planned behavior:**
1. Validate the supplied inputs before generating a plan.
2. Use the activity catalog and fitness level to identify suitable activities.
3. Allocate activity durations without exceeding the available time.
4. Estimate calories for each selected activity.
5. Create plan items and save the daily record and plan through the appropriate repository interface.

The exact input limits, activity-selection rules, and behavior when the calorie goal cannot be reached within the available time must be agreed upon by the team. The application should not silently claim that an unreachable goal has been achieved.

### 3.2 CalculateCaloriesUseCase

**Purpose:** Estimate calories for a planned activity.

The planned calculation is:

`estimatedKcal = rate(activity, fitnessLevel) × minutes`

The rate represents the estimated calories burned per minute for the selected activity and fitness level. The activity catalog provides the rates used by the calculation.

These values are estimates, not measurements of actual energy expenditure. The rate source, units, and rounding policy should be documented and kept consistent throughout the application.

### 3.3 CompleteActivityUseCase

**Purpose:** Record that a planned activity has been completed.

**Planned behavior:**
1. Identify the existing plan item.
2. Mark the item as completed.
3. Record its completion time.
4. Persist the update through the appropriate repository interface.

Completing an activity repeatedly must not create duplicate completion records or count its calories more than once. The completion operation should be designed to avoid duplicate updates.

### 3.4 GetProgressUseCase

**Purpose:** Calculate progress for a selected date.

Let `completedCalories` be the sum of `estimatedKcal` for completed plan items on that date.

`completedCalories = sum of estimatedKcal for completed items`

The remaining calories are calculated as:

`remainingCalories = max(0, goalKcal - completedCalories)`

Progress must be calculated from the saved record and completed activities for the selected date, rather than using the latest preferences for a different day.

## 4. Example Calculation

The following values are hypothetical and are used only to illustrate the calculation.

Assume:
- Daily calorie goal: 300 kcal
- Completed walking activity: 20 minutes
- Illustrative walking rate: 4 kcal per minute

Estimated calories for walking:

`20 × 4 = 80 kcal`

Remaining calories:

`max(0, 300 - 80) = 220 kcal`

The progress summary would show 80 estimated completed calories and 220 remaining calories. The illustrative rate is not an agreed production value and must be replaced with the team's approved activity-catalog rate.

## 5. Repository Interfaces and Data Flow

The planned data flow is:

`ViewModel → Domain Use Case → Repository Interface → Data Implementation → Local Storage`

- ViewModels receive user actions and expose UI state.
- Domain use cases apply business rules and call repository interfaces.
- Repository implementations in the Data layer map domain models to and from Room entities or DataStore preferences as appropriate.
- Room stores daily records, planned activities, and completion history.
- DataStore stores lightweight preferences such as the last goal, fitness level, and reminder settings.

The Domain layer must not depend directly on Compose screens, Android UI classes, Room entities, or DataStore implementation details.

## 6. Validation and Edge Cases

The Domain logic should account for the following cases:

- Invalid or missing user inputs must be rejected before plan generation.
- Activity durations must not exceed the available time.
- An activity completion must refer to an existing plan item.
- Repeated completion must not double-count estimated calories.
- Remaining calories must never be negative.
- A day with no completed activities should report zero completed calories.
- A day with no saved plan should be handled explicitly rather than treated as a completed plan.
- If the goal cannot be reached within the available time, the application should communicate the limitation instead of inventing additional activity time.

Exact numeric validation bounds and the user-facing behavior for unreachable goals require team agreement.

## 7. Daily Plan Regeneration Decision

The team must decide what happens when a user generates a new plan for a date that already contains completed activities.

The implementation should preserve accurate history and must not silently delete or duplicate completed activity records. Any replacement policy should be agreed upon before implementation.

## 8. Planned Verification

The following cases should be verified when the Domain logic is implemented:

- Valid inputs produce a plan that respects the available time.
- Invalid inputs are rejected.
- Calorie estimates use the approved activity rates and units.
- Completing an activity updates saved progress.
- Repeating a completion does not double-count calories.
- Remaining calories never fall below zero.
- Progress and history use the correct selected date.
- Regenerating a plan follows the team's agreed policy.

This document describes the planned behavior. It does not claim that the use cases or tests have already been implemented.
