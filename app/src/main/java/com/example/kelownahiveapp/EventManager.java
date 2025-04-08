package com.example.kelownahiveapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static final String EVENTS_FILE = "events.json";
    private List<Event> allEvents;

    public EventManager(Context context) {
        loadEvents(context);
    }

    private void loadEvents(Context context) {
        try {
            File file = new File(context.getFilesDir(), EVENTS_FILE);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                fis.close();

                String json = sb.toString();
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Event>>(){}.getType();
                allEvents = gson.fromJson(json, listType);
            } else {
                allEvents = new ArrayList<>(); //file doesn't exist
            }
        } catch (Exception e) {
            e.printStackTrace();
            allEvents = new ArrayList<>();
        }
    }

    public List<Event> getEventsByCategory(String category) {
        List<Event> filtered = new ArrayList<>();
        if (allEvents == null) return filtered;

        for (Event event : allEvents) {
            if (event.getCategory() != null &&
                    event.getCategory().equalsIgnoreCase(category)) {
                filtered.add(event);
            }
        }
        return filtered;
    }

    public List<Event> getAllEvents() {
        return allEvents;
    }
}