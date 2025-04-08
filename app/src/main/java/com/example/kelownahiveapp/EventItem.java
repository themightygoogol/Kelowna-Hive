package com.example.kelownahiveapp;

public class EventItem {
    private String name;
    private int backgroundResId;

    public EventItem(String name, int backgroundResId) {
        this.name = name;
        this.backgroundResId = backgroundResId;
    }

    public String getName() {
        return name;
    }

    public int getBackgroundResId() {
        return backgroundResId;
    }
}
