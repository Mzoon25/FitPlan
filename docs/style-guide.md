# FitPlan - UI/UX Style Guide

This style guide documents the design system, typography, color palette, components, and states for the **FitPlan** Android application (CSC 402 Milestone 2). All designs follow a clean Material 3 style optimized for a $360 \times 720$ dp phone layout.

---

## 1. Color Palette
* **Primary / Brand Green:** Used for the top app bar, primary buttons, active states, progress rings, and chart bars.
* **Background Color:** Clean light surface (`#F5F5F5` / white) for cards, list items, and general screens.
* **Card Surface:** White or very light gray containers with subtle borders.
* **Text Colors:** Dark neutral charcoal/black for primary headers and readable gray for secondary descriptions and timestamps.

---

## 2. Typography (Material 3 Scale)
* **Screen Titles / Headers:** Bold, prominent headers (e.g., "Set Your Goal", "Today's Plan", "History").
* **Body Text & Labels:** Clean sans-serif typography ensuring high readability across input fields, card items, and button labels.

---

## 3. Layout & Spacing
* **Standard Resolution / Viewport:** Designed for $360 \times 720$ dp mobile layouts.
* **Top App Bar:** Features a dark green header background with screen titles and action icons (such as back arrows on secondary screens).
* **Bottom Navigation Bar:** Persistent across main tabs (`Home`, `Plan`, `History`, `Settings`).

---

## 4. UI Components

### 4.1 Buttons
* **Primary Button:** Solid dark green background with white bold text (e.g., *Generate My Plan*, *Start Next Activity*, *Back to Plan*).
* **Secondary / Outlined Button:** White background with a dark green border and text (e.g., *Edit Goal / New Plan*, *Regenerate Plan*, *Cancel Activity*, *Clear History*).

### 4.2 Input Fields & Chips
* **Text Input Fields:** Outlined text boxes with rounded corners for user data entry (e.g., Daily calorie goal, available time).
* **Selection Chips:** Pill-shaped chips for selecting options such as fitness levels (*Beginner*, *Intermediate*, *Advanced*), with active states highlighted in green.

### 4.3 Cards & Containers
* Rounded container cards used for activity items, history logs, reminders, and summary metrics.
* Checkboxes integrated into activity cards to track completion status.

---

## 5. UI States (Loading, Empty, Error, Success)
* **Success State:** Displays completed activity checkmarks, updated calorie progress rings, and successfully saved local records.
* **Loading State:** Smooth placeholder transitions while reading from Room database or evaluating plan generation use cases.
* **Empty State / Initial Launch:** Handles first-launch scenarios (directing user to *Goal & Input* when no saved goal exists) and empty history logs.
* **Error / Edge States:** Validates user inputs (calorie and time fields) and handles missing permission prompts (e.g., `POST_NOTIFICATIONS` for Android 13+ reminders).
