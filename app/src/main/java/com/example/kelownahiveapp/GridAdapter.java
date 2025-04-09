package com.example.kelownahiveapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<Event> events;
    private int itemWidth;

    public GridAdapter(Context context, List<Event> events, int columnWidth) {
        this.context = context;
        this.events = events;
        this.itemWidth = columnWidth;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("GridAdapter", "Creating view for position: " + position);

        if (convertView == null) {
            Log.d("GridAdapter", "Inflating new view");
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        ImageButton eventButton = convertView.findViewById(R.id.sticky);
        TextView title = convertView.findViewById(R.id.eventName);

        Event event = getItem(position);
        Log.d("GridAdapter", "Event title: " + event.getTitle());

        // Set fixed dimensions
        convertView.setLayoutParams(new GridView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                itemWidth // Use the passed column width
        ));

        title.setText(event.getTitle());

        // Debug image setting
        int randomNumber = (int)(Math.random() * 4);
        Log.d("GridAdapter", "Random number: " + randomNumber);

        int resId;
        switch(randomNumber) {
            case 1: resId = R.drawable.light_blue_sticky_note; break;
            case 2: resId = R.drawable.purple_sticky_note; break;
            case 3: resId = R.drawable.orange_sticky_note; break;
            default: resId = R.drawable.yellow_sticky_note;
        }

        Log.d("GridAdapter", "Setting drawable resource: " + resId);
        eventButton.setImageResource(resId);
        eventButton.setVisibility(View.VISIBLE);

        eventButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailActivity.class);

            intent.putExtra("title", event.getTitle());
            intent.putExtra("dateTime", event.getDateTime());
            intent.putExtra("location", event.getLocation());
            intent.putExtra("description", event.getDescription());
            intent.putExtra("imageResources", event.getImageResources());

            context.startActivity(intent);
        });

        return convertView;
    }
}