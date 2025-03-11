package com.dianca.meetmewhere

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface EventDAO {
    // Insert a new event
    @Insert
    suspend fun insertEvent(event: EventsEntity)

    // Get all events ordered by ID (descending)
    @Query("SELECT * FROM events ORDER BY id DESC")
    fun getAllEvents(): LiveData<List<EventsEntity>>

    // Delete a specific event by event entity
    @Delete
    suspend fun deleteEvent(event: EventsEntity)

    // Update an existing event
    @Update
    suspend fun updateEvent(event: EventsEntity)
}
