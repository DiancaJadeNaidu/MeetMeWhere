# MeetMeWhere App

MeetMeWhere is an Android application designed to help users create, view, edit, and delete personal events easily. The app features a simple, user-friendly interface to manage event details like title, description, date, time, and location.

# Features
Create Events: Add new events with details including title, description, date, time, and location.

View Events: See a list of all saved events displayed in a RecyclerView.

Edit Events: Modify existing event details directly from the event list.

Delete Events: Remove events you no longer need.

Persistent Storage: All events are saved locally using Room database for persistent data storage.

# Installation
Clone the repository
Open the project in Android Studio.

Build and run the app on an Android device or emulator with API level 21+.

# Usage
Add New Event: Navigate to the Event Creation screen, fill in all event details, and tap Save Event.

View and Manage Events: Click the button to go to the Event Management screen to see all events.

Edit an Event: Tap on an event in the list to populate the fields for editing. Modify details and tap Save Changes.

Delete an Event: Use the delete button next to an event to remove it from the database.

# Technical Details
Developed in Kotlin for Android.

Uses Room for local database management.

Implements RecyclerView for displaying event lists.

Utilizes Kotlin Coroutines for asynchronous database operations.

MVVM architecture with LiveData to observe database changes.

# Future Improvements
Add event reminders/notifications.

Integrate with device calendar.

Support for recurring events.

User authentication and multi-user support.

Improve UI/UX with Material Design components.

---

© 2025 Dianca Jade Naidu. All rights reserved.

