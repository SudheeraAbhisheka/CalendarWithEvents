package com.example.calendar;

public class Event {
    private int year;
    private int month;
    private int day;
    private int startTimeHour;
    private int startTimeMinute;
    private int endTimeHour;
    private int endTimeMinute;
    private int notifyPrior;
    private String title;
    private String note;

    public Event(int year, int month, int day, int startTimeHour, int startTimeMinute, int endTimeHour, int endTimeMinute, int notifyPrior, String title, String note) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.startTimeHour = startTimeHour;
        this.startTimeMinute = startTimeMinute;
        this.endTimeHour = endTimeHour;
        this.endTimeMinute = endTimeMinute;
        this.notifyPrior = notifyPrior;
        this.title = title;
        this.note = note;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getStartTimeHour() {
        return startTimeHour;
    }

    public int getStartTimeMinute() {
        return startTimeMinute;
    }

    public int getEndTimeHour() {
        return endTimeHour;
    }

    public int getEndTimeMinute() {
        return endTimeMinute;
    }

    public int getNotifyPrior() {
        return notifyPrior;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }
}
