package com.example.calendar;

public class CalendarEventString {
    private String title;
    private String date;
    private String startTime;
    private String endTime;
    private String note;
    private String notifyPrior;

    public CalendarEventString(String title, String date, String startTime, String endTime, String note, String notifyPrior) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNotifyPrior() {
        return notifyPrior;
    }

    public void setNotifyPrior(String notifyPrior) {
        this.notifyPrior = notifyPrior;
    }

}
