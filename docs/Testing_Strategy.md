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
