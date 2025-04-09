package com.example.kelownahiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // Retrieve event data from the intent extras
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        if (title == null) {
            title = "Untitled Event";
            Log.e("EventDetailActivity", "Title extra is null. Using default.");
        }

        String dateTime = intent.getStringExtra("dateTime");
        if (dateTime == null) {
            dateTime = "Unknown Date/Time";
            Log.e("EventDetailActivity", "DateTime extra is null. Using default.");
        }

        String location = intent.getStringExtra("location");
        if (location == null) {
            location = "Unknown Location";
            Log.e("EventDetailActivity", "Location extra is null. Using default.");
        }

        String description = intent.getStringExtra("description");
        if (description == null) {
            description = "No description available.";
            Log.e("EventDetailActivity", "Description extra is null. Using default.");
        }

        // Find UI components in your layout (make sure these IDs exist in activity_event_detail.xml)
        TextView tvEventTitle = findViewById(R.id.tvEventTitle);
        TextView tvEventDateTime = findViewById(R.id.tvEventDateTime);
        TextView tvEventLocation = findViewById(R.id.tvEventLocation);
        TextView tvEventDescription = findViewById(R.id.tvEventDescription);

        tvEventTitle.setText(title);
        tvEventDateTime.setText(dateTime);
        tvEventLocation.setText(location);
        tvEventDescription.setText(description);
    }
}
