# FitPlan – Jetpack Components & Libraries

This document describes the main Jetpack components and Android libraries used in the FitPlan application and provides a justification for each choice.

## 1. Jetpack Compose + Material 3
**Purpose:** Build the user interface and application screens.

**Justification:** Jetpack Compose provides a modern declarative approach for Android UI development. Material 3 provides consistent and reusable UI components that help FitPlan maintain a clean and user-friendly design.

## 2. Navigation Compose
**Purpose:** Manage navigation between screens such as Home, Plan, History, Settings, and secondary screens.

**Justification:** Navigation Compose integrates directly with Jetpack Compose and provides a structured way to manage screen transitions and navigation routes.

## 3. ViewModel
**Purpose:** Manage UI state and screen-related data.

**Justification:** ViewModel keeps UI data separate from the UI layer and preserves state during configuration changes, improving maintainability.

## 4. Kotlin Coroutines, Flow, and StateFlow
**Purpose:** Handle asynchronous operations and reactive data updates.

**Justification:** These tools allow FitPlan to perform background operations efficiently and automatically update the UI when application data changes.

## 5. Room
**Purpose:** Store structured local data such as plans and activity history.

**Justification:** Room provides a reliable abstraction over SQLite and supports organized, persistent local storage.

## 6. DataStore
**Purpose:** Store lightweight preferences and application settings.

**Justification:** DataStore provides a modern and asynchronous solution for storing settings and small preference values.

## 7. WorkManager
**Purpose:** Schedule reliable background tasks.

**Justification:** WorkManager is suitable for background work that should continue reliably even if the application is closed or restarted.

## 8. Compose Canvas
**Purpose:** Draw custom visual elements when needed.

**Justification:** Compose Canvas allows custom graphics to be created directly within the Compose UI without requiring separate custom View implementations.
