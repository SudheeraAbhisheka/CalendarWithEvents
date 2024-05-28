package com.example.calendar;

import static android.content.ContentValues.TAG;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.calendar.Event_dbSchema.EventsTable;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Event_dbCursor extends CursorWrapper {

    public Event_dbCursor(Cursor cursor) {
        super(cursor);
    }

    public CalendarEventOriginal getEvent(){
        long notifyPrior = 0;

        try{
            notifyPrior = Long.parseLong( getString(getColumnIndex(EventsTable.Columns.NOTIFY_PRIOR)));

        }catch (NumberFormatException e){

        }

        return new CalendarEventOriginal(
            getInt(getColumnIndex(EventsTable.Columns.YEAR)),
            getInt(getColumnIndex(EventsTable.Columns.MONTH)),
            getInt(getColumnIndex(EventsTable.Columns.DAY)),
            getInt(getColumnIndex(EventsTable.Columns.START_TIME_HOUR)),
            getInt(getColumnIndex(EventsTable.Columns.START_TIME_MINUTE)),
            getInt(getColumnIndex(EventsTable.Columns.END_TIME_HOUR)),
            getInt(getColumnIndex(EventsTable.Columns.END_TIME_MINUTE)),
            notifyPrior,
            getString(getColumnIndex(EventsTable.Columns.TITLE)),
            getString(getColumnIndex(EventsTable.Columns.NOTE))
        );
    }
}
