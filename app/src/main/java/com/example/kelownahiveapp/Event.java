package com.example.kelownahiveapp;

public class Event {
    private String title;
    private String dateTime;
    private String location;
    private String description;
    private int[] imageResources;
    private String category;

    public Event(String title, String dateTime, String location, String description,String category ,int[] imageResources) {
        this.title = title;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
        this.imageResources = imageResources;
    }
    public Event(String title, String dateTime, String location, String description, String category) {
        this(title, dateTime, location, description,category ,null);
    }
    public String getTitle() {
        return title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public int[] getImageResources() {
        return imageResources;
    }
}
