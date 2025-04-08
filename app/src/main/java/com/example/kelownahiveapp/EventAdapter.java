package com.example.kelownahiveapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kelownahiveapp.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<EventItem> events;
    private OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEventClick(String eventName);
    }

    public EventAdapter(List<EventItem> events, OnEventClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventItem event = events.get(position);
        holder.eventTitle.setText(event.getName());
        holder.bgImage.setImageResource(event.getBackgroundResId());
        holder.itemView.setOnClickListener(v -> listener.onEventClick(event.getName()));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle;
        ImageView bgImage;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            bgImage = itemView.findViewById(R.id.eventNote);
            eventTitle = itemView.findViewById(R.id.eventTitle);
        }
    }
}
