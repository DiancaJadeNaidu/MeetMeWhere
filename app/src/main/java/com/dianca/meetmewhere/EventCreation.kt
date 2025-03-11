package com.dianca.meetmewhere

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dianca.meetmewhere.databinding.ActivityEventCreationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventCreationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventCreationBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEventCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("USERNAME") ?: "User"
        binding.txtGreeting.text = "Welcome, $username!"

        db = AppDatabase.getDatabase(this)

        binding.btnSaveEvent.setOnClickListener {
            val title = binding.edtEventTitle.text.toString().trim()
            val description = binding.edtEventDescription.text.toString().trim()
            val date = binding.edtEventDate.text.toString().trim()
            val time = binding.edtEventTime.text.toString().trim()
            val location = binding.edtEventLocation.text.toString().trim()

            if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty() && location.isNotEmpty()) {
                val event = EventsEntity(
                    title = title,
                    description = description,
                    date = date,
                    time = time,
                    location = location
                )

                CoroutineScope(Dispatchers.IO).launch {
                    db.eventDao().insertEvent(event)

                    runOnUiThread {
                        Toast.makeText(this@EventCreationActivity, "Event Saved Successfully!", Toast.LENGTH_SHORT).show()
                        clearFields()

                          }
                }
            } else {
                // Show error toast if fields are empty
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }

            }
        binding.btnGoToEventManagement.setOnClickListener {
            val intent = Intent(this, EventManagementActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clearFields() {
        binding.edtEventTitle.text.clear()
        binding.edtEventDescription.text.clear()
        binding.edtEventDate.text.clear()
        binding.edtEventTime.text.clear()
        binding.edtEventLocation.text.clear()
    }
}
