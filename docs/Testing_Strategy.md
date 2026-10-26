# FitPlan – Testing Strategy

This document describes the testing strategy for the FitPlan application and outlines how the main components and user flows will be verified.

## 1. Unit Testing

Unit tests will be used to verify individual components of the application, especially ViewModels and business logic.

Tests will verify:
- Activity creation and validation.
- Daily plan calculations.
- UI state updates.
- Data processing and validation.
- ViewModel behavior.

JUnit will be used for unit testing.

## 2. UI Testing

Jetpack Compose UI tests will be used to verify the behavior of the main application screens and user interactions.

UI tests will cover:
- Navigation between Home, Plan, History, and Settings.
- Adding activities to a daily plan.
- Editing and removing activities.
- Interacting with buttons and input fields.
- Displaying the correct UI state.

## 3. Database Testing

Room database tests will verify that application data is stored and retrieved correctly.

Tests will cover:
- Inserting records.
- Updating records.
- Deleting records.
- Retrieving saved activities and plans.
- Relationships between Room entities.

## 4. DataStore Testing

DataStore tests will verify that lightweight user preferences are saved and retrieved correctly.

Tests will cover:
- Dark mode preference.
- Notification settings.
- Reminder time.
- Default daily activity goal.
- Onboarding completion state.

## 5. Integration Testing

Integration tests will verify that different parts of FitPlan work together correctly, including the UI, ViewModel, repository, Room database, and DataStore.

## 6. Testing Goal

The goal of the testing strategy is to ensure that FitPlan is reliable, that data is handled correctly, and that the main user flows work as expected.
## 7. Planned Test Cases – Milestone 2 Stage 2

**Responsible Member:** Kadi (kadyq2006)

**Status:** All test cases are Planned. No tests have been executed or marked as Passed.

| ID | Feature | Input / Action | Expected Result | Test Level | Status |

|---|---|---|---|---|---|

| TC01 | Input Validation | Submit empty required fields. | Validation errors are displayed. | UI | Planned |

| TC02 | Input Validation | Enter invalid calorie or time values. | Invalid inputs are rejected. | Unit | Planned |

| TC03 | Fitness Level | Select an unsupported fitness level. | Invalid selection is rejected. | Unit | Planned |

| TC04 | Plan Generation | Enter a valid goal, time, and fitness level. | A suitable daily plan is generated within the available time. | Unit | Planned |

| TC05 | Plan Generation | Enter an unreachable calorie goal. | The estimated shortfall is handled without falsely reporting goal achievement. | Unit | Planned |

| TC06 | Calorie Calculation | Calculate estimated calories for activities. | The approved calculation formula and activity rates are used. | Unit | Planned |

| TC07 | Progress Tracking | Complete activities for a selected date. | Only completed activities for that date are counted. | Unit | Planned |

| TC08 | Remaining Calories | Calculate remaining calories. | Result equals max(0, goalKcal - completedCalories). | Unit | Planned |

| TC09 | Duplicate Completion | Complete the same activity twice. | Progress is counted only once. | Integration | Planned |

| TC10 | Local Storage | Save and retrieve a daily plan. | Saved plan data is retrieved correctly. | Integration | Planned |

| TC11 | History | Reopen saved activity records. | Correct history and completion states appear. | Integration | Planned |

| TC12 | Activity Timer | Start, pause, and resume the timer. | Timer states and remaining time are handled correctly. | Unit | Planned |

| TC13 | Timer Recomposition | Trigger Compose recomposition while the timer runs. | The timer does not restart. | UI | Planned |

| TC14 | Navigation | Navigate between the eight planned screens. | Correct screens are displayed. | UI | Planned |

| TC15 | Reminder Settings | Save and reopen reminder preferences. | Settings remain saved in DataStore. | Integration | Planned |

| TC16 | Reminder Scheduling | Enable, modify, and disable reminders. | Reminder work is updated or cancelled without duplication. | Integration | Planned |

| TC17 | Notification Permission | Deny notification permission when required. | Permission denial is handled without crashing. | UI | Planned |

| TC18 | Goal Notification | Trigger the same completed-goal condition repeatedly. | The goal notification is not duplicated. | Integration | Planned |

| TC19 | Screen States | Display loading, empty, error, and success states. | Appropriate UI states and messages are displayed. | UI | Planned |

| TC20 | Weekly Statistics | View saved weekly activity records. | Correct saved dates and statistics are displayed. | Integration | Planned |

## 8. Scope Review and Pending Decisions

The existing testing strategy mentions dark mode, activity creation, record editing and deletion, and onboarding.

These features must be checked against the approved project scope before they are treated as confirmed testing requirements.

The team must also confirm:

- Accepted input values and validation boundaries.

- Estimated calorie calculation rates.

- How unreachable calorie goals are handled.

- Whether completed activities can be changed.

- How regenerating a partially completed daily plan should work.

- The approved notification and reminder behavior.

Unconfirmed requirements must not be reported as implemented features.

## 9. Test Execution and Evidence

All test cases currently have the status **Planned**.

Actual results must only be recorded after real test execution.

Future testing evidence should include:

- Test case ID.

- Execution date.

- Actual result.

- Passed or Failed status.

- Supporting screenshots or test reports, when applicable.

- Notes describing any detected defects.

The final QA review and B5 submission requirements must be checked against the instructor's rubric and the actual project evidence.

This document represents a testing strategy and plan. It does not claim that the application has been fully implemented or that any tests have passed.
