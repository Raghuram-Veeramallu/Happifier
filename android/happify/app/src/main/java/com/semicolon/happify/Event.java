package com.semicolon.happify;

public class Event {

    private String eventName;
    private String eventLocation;
    private String eventDate;

    public Event(String eventName, String eventLocation, String eventDate) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }





}
