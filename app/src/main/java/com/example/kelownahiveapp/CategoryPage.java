package com.example.kelownahiveapp;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowMetrics;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class CategoryPage extends AppCompatActivity {
    private GridView eventsContainer;
    private EventManager eventManager;
    private String currentCategory;
    private TextView categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_page);

        eventsContainer = findViewById(R.id.gridLayout);
        categoryName = findViewById(R.id.category_text_view);

        currentCategory = getIntent().getStringExtra("CATEGORY_NAME");
        if (currentCategory == null) {
            Toast.makeText(this, "No category specified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        eventManager = new EventManager(this);
        categoryName.setText(currentCategory);
        loadEvents();
    }

    private void loadEvents() {
        List<Event> events;
        if ("All".equals(currentCategory)) {
            events = eventManager.getAllEvents();
        } else {
            events = eventManager.getEventsByCategory(currentCategory);
        }

        // Debug logging
        for (Event event : events) {
            Log.d("CategoryPage", "Event: " + event.getTitle());
        }

        if (events.isEmpty()) {
            Toast.makeText(this, "No events found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Modern way to get window metrics
        WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
        Rect bounds = windowMetrics.getBounds();
        int screenWidth = bounds.width();

        // Calculate column width (account for 16dp padding on each side)
        int columnWidth = (screenWidth - dpToPx(32)) / 2; // 2 columns

        eventsContainer.setAdapter(new GridAdapter(this, events, columnWidth));

        eventsContainer.setOnItemClickListener((parent, view, position, id) -> {
            Event event = events.get(position);
            Toast.makeText(this, "Selected: " + event.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    // Helper method to convert dp to pixels
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }
}