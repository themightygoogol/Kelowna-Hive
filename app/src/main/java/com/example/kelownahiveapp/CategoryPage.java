package com.example.kelownahiveapp;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.GridView;
import android.widget.ImageButton;
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

    private ImageButton backButton;
    private TextView categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_page);

        eventsContainer = findViewById(R.id.gridLayout);
        categoryName = findViewById(R.id.category_text_view);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryPage.this, MainPage.class);
                startActivity(intent);
            }
        });

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

        WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
        Rect bounds = windowMetrics.getBounds();
        int screenWidth = bounds.width();

        int columnWidth = (screenWidth - dpToPx(32)) / 2; // 2 columns

        eventsContainer.setAdapter(new GridAdapter(this, events, columnWidth, currentCategory));

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