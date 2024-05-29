package com.example.calendar;

public class Holiday {
    private int year;
    private int month;
    private int day;
    private String title;
    private String note;

    public Holiday(int year, int month, int day, String title, String note) {
        this.year = year;
        this.month = month;
        this.day = day;
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

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }
}
