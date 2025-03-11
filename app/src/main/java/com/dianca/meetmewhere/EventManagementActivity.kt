package com.dianca.meetmewhere

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dianca.meetmewhere.databinding.ActivityEventManagementBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventManagementBinding
    private lateinit var db: AppDatabase
    private lateinit var eventAdapter: EventAdapter
    private var isEditMode: Boolean = false
    private var currentEventId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        // Initialize RecyclerView
        eventAdapter = EventAdapter(emptyList(), ::enableEditMode, ::deleteEvent)

        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEvents.adapter = eventAdapter

        // Observe events LiveData and update the adapter with the list
        db.eventDao().getAllEvents().observe(this, Observer { events ->
            eventAdapter.setData(events) // Update the adapter with the new list
        })

        // Add New Event Button
        binding.btnAddNewEvent.setOnClickListener {
            clearEditFields() // Clear fields to start fresh for a new event
            isEditMode = false
            currentEventId = -1 // No event to edit
        }

        // Save Changes Button
        binding.btnSaveChanges.setOnClickListener {
            saveEvent()
        }

        // Cancel Changes Button
        binding.btnCancelChanges.setOnClickListener {
            clearEditFields() // Reset fields to cancel changes
            isEditMode = false
            currentEventId = -1 // Reset current event ID
        }
    }

    private fun enableEditMode(event: EventsEntity) {
        isEditMode = true
        currentEventId = event.id
        binding.edtEventTitle.setText(event.title)
        binding.edtEventDescription.setText(event.description)
        binding.edtEventDate.setText(event.date)
        binding.edtEventTime.setText(event.time)
        binding.edtEventLocation.setText(event.location)
    }

    private fun deleteEvent(event: EventsEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            db.eventDao().deleteEvent(event) // ✅ Pass the full entity
            withContext(Dispatchers.Main) {
                Toast.makeText(this@EventManagementActivity, "Event Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun saveEvent() {
        val title = binding.edtEventTitle.text.toString().trim()
        val description = binding.edtEventDescription.text.toString().trim()
        val date = binding.edtEventDate.text.toString().trim()
        val time = binding.edtEventTime.text.toString().trim()
        val location = binding.edtEventLocation.text.toString().trim()

        if (title.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        val event = EventsEntity(
            id = currentEventId,
            title = title,
            description = description,
            date = date,
            time = time,
            location = location
        )

        CoroutineScope(Dispatchers.IO).launch {
            if (isEditMode) {
                // Update existing event
                db.eventDao().updateEvent(event)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EventManagementActivity, "Event Updated Successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Create new event
                db.eventDao().insertEvent(event)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EventManagementActivity, "Event Added Successfully", Toast.LENGTH_SHORT).show()
                }
            }
            clearEditFields() // Reset fields after saving
        }
    }

    private fun clearEditFields() {
        binding.edtEventTitle.text.clear()
        binding.edtEventDescription.text.clear()
        binding.edtEventDate.text.clear()
        binding.edtEventTime.text.clear()
        binding.edtEventLocation.text.clear()
    }
}
