package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.calendar.Event_dbSchema.EventsTable;

import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;

public class Event_dbModel {

    SQLiteDatabase dataBase;

    //this is create the database and get the link of the database to work with it

    public void load(Context theContext){
        this.dataBase = new Event_dbHelper(theContext).getWritableDatabase();
    }

    public void addEvent(CalendarEventOriginal event){
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

    public ArrayList<CalendarEventOriginal> getEvents(int year, int month){
        ArrayList<CalendarEventOriginal> eventsList = new ArrayList<>();

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
}
