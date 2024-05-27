package com.example.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

public class CalendarEventOriginal {
    private int year;
    private int month;
    private int day;
    private LocalTime startTime;
    private LocalTime endTime;
    private long notifyPrior;
    private String title;
    private String note;

    public CalendarEventOriginal(int year, int month, int day, LocalTime startTime, LocalTime endTime, long notifyPrior, String title, String note) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notifyPrior = notifyPrior;
        this.title = title;
        this.note = note;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public long getNotifyPrior() {
        return notifyPrior;
    }

    public void setNotifyPrior(long notifyPrior) {
        this.notifyPrior = notifyPrior;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

