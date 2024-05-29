package com.example.calendar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.calendar.Event;
import com.example.calendar.Holiday;
import com.example.calendar.database.Event_dbSchema.EventsTable;
import com.example.calendar.database.Event_dbSchema.HolidaysTable;

import java.util.ArrayList;

public class Event_dbModel {

    SQLiteDatabase dataBase;

    //this is create the database and get the link of the database to work with it

    public void load(Context theContext){
        this.dataBase = new Event_dbHelper(theContext).getWritableDatabase();
    }

    public void addEvent(Event event){
        ContentValues cv = new ContentValues();
        cv.put(EventsTable.Columns.YEAR, event.getYear());
        cv.put(EventsTable.Columns.MONTH, event.getMonth());
        cv.put(EventsTable.Columns.DAY, event.getDay());
        cv.put(EventsTable.Columns.START_TIME_HOUR, event.getStartTimeHour());
        cv.put(EventsTable.Columns.START_TIME_MINUTE, event.getStartTimeMinute());
        cv.put(EventsTable.Columns.END_TIME_HOUR, event.getEndTimeHour());
        cv.put(EventsTable.Columns.END_TIME_MINUTE, event.getEndTimeMinute());
        cv.put(EventsTable.Columns.TITLE, event.getTitle());
        cv.put(EventsTable.Columns.NOTE, event.getNote());

        dataBase.insert(EventsTable.NAME, null, cv);
    }

    public ArrayList<Event> getEvents(int year, int month){
        ArrayList<Event> eventsList = new ArrayList<>();

        Cursor cursor = dataBase.rawQuery("SELECT * FROM "+ Event_dbSchema.EventsTable.NAME +
                " WHERE " +  EventsTable.Columns.YEAR + " = ? AND " +
                EventsTable.Columns.MONTH + " = ?",
                new String[]{year + "", month + ""});

        Event_dbCursor eventDbCursor = new Event_dbCursor(cursor);

        try{
            eventDbCursor.moveToFirst();
            while(!eventDbCursor.isAfterLast()){
                eventsList.add(eventDbCursor.getEvent());
                eventDbCursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return eventsList;
    }

    public ArrayList<Event> getEventsInADay(int year, int month, int day){
        ArrayList<Event> eventsList = new ArrayList<>();

        Cursor cursor = dataBase.rawQuery("SELECT * FROM "+ Event_dbSchema.EventsTable.NAME +
                        " WHERE " +  EventsTable.Columns.YEAR + " = ? AND " +
                        EventsTable.Columns.MONTH + " = ? AND " +
                        EventsTable.Columns.DAY + " = ?",
                new String[]{year + "", month + "", day + ""});

        Event_dbCursor eventDbCursor = new Event_dbCursor(cursor);

        try{
            eventDbCursor.moveToFirst();
            while(!eventDbCursor.isAfterLast()){
                eventsList.add(eventDbCursor.getEvent());
                eventDbCursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return eventsList;
    }
    public ArrayList<Holiday> getHolidaysInADay(int year, int month, int day){
        ArrayList<Holiday> holidaysList = new ArrayList<>();

        Cursor cursor = dataBase.rawQuery("SELECT * FROM "+ Event_dbSchema.HolidaysTable.NAME +
                        " WHERE " +  HolidaysTable.Columns.YEAR + " = ? AND " +
                        HolidaysTable.Columns.MONTH + " = ? AND " +
                        HolidaysTable.Columns.DAY + " = ?",
                new String[]{year + "", month + "", day + ""});

        Event_dbCursor eventDbCursor = new Event_dbCursor(cursor);

        try{
            eventDbCursor.moveToFirst();
            while(!eventDbCursor.isAfterLast()){
                holidaysList.add(eventDbCursor.getHoliday());
                eventDbCursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return holidaysList;
    }
}
