package com.example.kelownahiveapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private List<Event> eventList;
    private static final String EVENTS_FILE = "events.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load events from internal storage, or add mock events if none exist.
        eventList = loadEventsFromInternalStorage();
        if (eventList == null || eventList.isEmpty()) {
            eventList = new ArrayList<>();
            eventList.add(new Event("Sunday Football Watch Party",
                    "May 5th, 2025 (6 PM - 9 PM PST)",
                    "Kelowna Downtown Library",
                    "Join us for a fun football watch party at the library.",
                    "Meet up",
                    new int[]{R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground}));
            eventList.add(new Event("Art & Wine Night",
                    "June 10th, 2025 (7 PM - 10 PM PST)",
                    "Kelowna Art Gallery",
                    "Enjoy a night of art and wine with local artists.",
                    "Art",
                    new int[]{R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground}));
        }

        adapter = new EventListAdapter(this, eventList);
        recyclerView.setAdapter(adapter);
    }

    // Force a refresh every time this activity resumes.
    @Override
    protected void onResume() {
        super.onResume();
        List<Event> updatedEvents = loadEventsFromInternalStorage();
        if (updatedEvents != null) {
            eventList.clear();
            eventList.addAll(updatedEvents);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            List<Event> updatedEvents = loadEventsFromInternalStorage();
            if (updatedEvents != null) {
                eventList.clear();
                eventList.addAll(updatedEvents);
                adapter.notifyDataSetChanged();
            }
        }
    }

    // Loads events from the internal storage JSON file
    private List<Event> loadEventsFromInternalStorage() {
        List<Event> events = new ArrayList<>();
        try {
            File file = new File(getFilesDir(), EVENTS_FILE);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

                String json = sb.toString();
                Gson gson = new Gson();
                Type eventListType = new TypeToken<ArrayList<Event>>() {}.getType();
                events = gson.fromJson(json, eventListType);
            }
        } catch (Exception e) {
            Log.e("EventListActivity", "Error loading events: " + e.getMessage());
        }
        return events;
    }
}