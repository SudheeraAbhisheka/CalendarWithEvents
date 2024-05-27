package com.example.calendar;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.calendar.Event_dbSchema.EventsTable;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Event_dbCursor extends CursorWrapper {

    public Event_dbCursor(Cursor cursor) {
        super(cursor);
    }

    public CalendarEventOriginal getEvent(){

        LocalTime startTime = null;
        LocalTime endTime = null;
        long notifyPrior = 0;
        try{
            startTime = LocalTime.parse(getString(getColumnIndex(EventsTable.Columns.START_TIME)));
            endTime = LocalTime.parse(getString(getColumnIndex(EventsTable.Columns.END_TIME)));
        }catch (DateTimeParseException e){

        }
        try{
            notifyPrior = Long.parseLong( getString(getColumnIndex(EventsTable.Columns.NOTIFY_PRIOR)));

        }catch (NumberFormatException e){

        }

        return new CalendarEventOriginal(
            getInt(getColumnIndex(EventsTable.Columns.YEAR)),
            getInt(getColumnIndex(EventsTable.Columns.MONTH)),
            getInt(getColumnIndex(EventsTable.Columns.DAY)),
            startTime,
            endTime,
            notifyPrior,
            getString(getColumnIndex(EventsTable.Columns.TITLE)),
            getString(getColumnIndex(EventsTable.Columns.NOTE))
        );
    }
}
