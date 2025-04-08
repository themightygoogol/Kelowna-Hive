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

            // Set fixed width/height for square items
            convertView.setLayoutParams(new GridView.LayoutParams(itemWidth, itemWidth));
        }

        ImageView image = convertView.findViewById(R.id.sticky);
        TextView title = convertView.findViewById(R.id.eventName);

        Event event = getItem(position);
        title.setText(event.getTitle());

        if (event.getImageResources() != null && event.getImageResources().length > 0) {
            image.setImageResource(event.getImageResources()[0]);
        } else {
            image.setImageResource(R.drawable.light_blue_sticky_note);
        }

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EventDetailActivity.class);
            intent.putExtra("EVENT_JSON", new Gson().toJson(event));
            context.startActivity(intent);
        });

        return convertView;
    }
}


//public class GridAdapter extends BaseAdapter {
//    private Context context;
//    private List<Integer> imageResIds;
//    private List<String> categoryNames; // New: List for text labels
//    private int itemHeight;
//
//    public GridAdapter(Context context, List<Integer> imageResIds, List<String> categoryNames, int itemHeight) {
//        this.context = context;
//        this.imageResIds = imageResIds;
//        this.categoryNames = categoryNames;
//        this.itemHeight = itemHeight;
//    }
//
//    @Override
//    public int getCount() {
//        return imageResIds.size(); // Or Math.min(imageResIds.size(), 12) for 3x4 grid
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return imageResIds.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
//
//            // Set fixed height
//            convertView.setLayoutParams(new GridView.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    itemHeight
//            ));
//        }
//
//        // Get references to views
//        ImageButton imageButton = convertView.findViewById(R.id.gridImageButton);
//        TextView textView = convertView.findViewById(R.id.gridText); // Add this ID to your XML
//
//        // Set dynamic content
//        imageButton.setImageResource(imageResIds.get(position));
//        textView.setText(categoryNames.get(position)); // Set text from your list
//
//        return convertView;
//    }
//}