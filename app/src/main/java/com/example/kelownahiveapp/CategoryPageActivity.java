package com.example.kelownahiveapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kelownahiveapp.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryPageActivity extends AppCompatActivity implements EventAdapter.OnEventClickListener {

    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private List<EventItem> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        eventRecyclerView = findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        eventList = new ArrayList<>();
        eventList.add(new EventItem("Color Run 5K", R.drawable.note_orange));
        eventList.add(new EventItem("Senior Basketball Night", R.drawable.note_blue));
        eventList.add(new EventItem("Glow-in-the-dark Zumba", R.drawable.note_pink));
        eventList.add(new EventItem("Community Soccer Game", R.drawable.note_green));
        eventList.add(new EventItem("Archery Tryouts", R.drawable.note_purple));

        eventAdapter = new EventAdapter(eventList, this);
        eventRecyclerView.setAdapter(eventAdapter);
    }

    @Override
    public void onEventClick(String eventName) {
        Toast.makeText(this, "Clicked: " + eventName, Toast.LENGTH_SHORT).show();
    }
}
