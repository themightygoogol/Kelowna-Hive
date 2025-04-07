package com.example.kelownahiveapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventListAdapter adapter;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        // Add sample events (replace with real data later)
        eventList.add(new Event("Sunday Football Watch Party",
                "May 5th, 2025 (6 PM - 9 PM PST)",
                "Kelowna Downtown Library",
                "Join us for a fun football watch party at the library.",
                new int[]{R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground}));
        eventList.add(new Event("Art & Wine Night",
                "June 10th, 2025 (7 PM - 10 PM PST)",
                "Kelowna Art Gallery",
                "Enjoy a night of art and wine with local artists.",
                new int[]{R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground}));
        // Add more events as needed

        adapter = new EventListAdapter(this, eventList);
        recyclerView.setAdapter(adapter);
    }
}
