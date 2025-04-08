package com.example.kelownahiveapp;

import android.os.Bundle;
import android.util.DisplayMetrics;
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
        eventManager = new EventManager(this);
        categoryName.setText(currentCategory);
        eventManager.getEventsByCategory(currentCategory);
    }
    private void loadEvents() {
        List<Event> events;
        if ("All".equals(currentCategory)) {
            events = eventManager.getAllEvents();
        } else {
            events = eventManager.getEventsByCategory(currentCategory);
        }
            DisplayMetrics metrics = new DisplayMetrics();
            int columnWidth = metrics.widthPixels / 3;
            int height = metrics.heightPixels;

            eventsContainer.setAdapter(new GridAdapter(this, events, columnWidth));
    }
}