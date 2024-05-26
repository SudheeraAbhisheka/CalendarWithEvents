package com.example.calendar;

import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarEventOriginal {
    private String title;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String note;
    private long notifyPrior;

    public CalendarEventOriginal(String title, LocalDate date, LocalTime startTime, LocalTime endTime, String note, long notifyPrior) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.note = note;
        this.notifyPrior = notifyPrior;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getNotifyPrior() {
        return notifyPrior;
    }

    public void setNotifyPrior(long notifyPrior) {
        this.notifyPrior = notifyPrior;
    }

}
