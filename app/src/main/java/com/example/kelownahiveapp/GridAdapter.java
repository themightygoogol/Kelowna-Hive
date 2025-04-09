package com.example.kelownahiveapp;

import android.content.Context;
import android.content.Intent;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            convertView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) (parent.getWidth() * 0.45)
            ));
        }

        ImageButton eventButton = convertView.findViewById(R.id.sticky);
        TextView title = convertView.findViewById(R.id.eventName);

        Event event = getItem(position);
        title.setText(event.getTitle());
        int randomNumber = (int)(Math.random() * 4);
        if (randomNumber == 1) {
            eventButton.setImageResource(R.drawable.light_blue_sticky_note);
        } else if (randomNumber == 2) {
            eventButton.setImageResource(R.drawable.purple_sticky_note);
        } else if (randomNumber == 3) {
            eventButton.setImageResource(R.drawable.orange_sticky_note);
        } else {
            eventButton.setImageResource(R.drawable.yellow_sticky_note);
        }

        eventButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailActivity.class);
            // Remove FLAG_ACTIVITY_NEW_TASK if using Activity context
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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