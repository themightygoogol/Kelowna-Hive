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
                    itemWidth
            ));
        }

        ImageButton eventButton = convertView.findViewById(R.id.sticky);
        TextView title = convertView.findViewById(R.id.eventName);

        eventButton.setVisibility(View.VISIBLE);
        eventButton.setScaleType(ImageView.ScaleType.FIT_XY);
        eventButton.setClickable(false);
        eventButton.setFocusable(false);

        Event event = getItem(position);
        title.setText(event.getTitle());

        int randomNumber = (int)(Math.random() * 4);
        switch (randomNumber) {
            case 1:
                eventButton.setImageResource(R.drawable.light_blue_sticky_note);
                break;
            case 2:
                eventButton.setImageResource(R.drawable.purple_sticky_note);
                break;
            case 3:
                eventButton.setImageResource(R.drawable.orange_sticky_note);
                break;
            default:
                eventButton.setImageResource(R.drawable.yellow_sticky_note);
        }

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra("EVENT", new Gson().toJson(event));
            context.startActivity(intent);
        });

        return convertView;
    }
}
