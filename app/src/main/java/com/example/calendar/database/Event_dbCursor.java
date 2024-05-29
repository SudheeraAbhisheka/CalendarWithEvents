package com.example.calendar.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.calendar.CalendarEventOriginal;
import com.example.calendar.Holiday;
import com.example.calendar.database.Event_dbSchema.EventsTable;

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
    public Holiday getHoliday(){
        return new Holiday(
                getInt(getColumnIndex(Event_dbSchema.HolidaysTable.Columns.YEAR)),
                getInt(getColumnIndex(Event_dbSchema.HolidaysTable.Columns.MONTH)),
                getInt(getColumnIndex(Event_dbSchema.HolidaysTable.Columns.DAY)),
                getString(getColumnIndex(Event_dbSchema.HolidaysTable.Columns.TITLE)),
                getString(getColumnIndex(Event_dbSchema.HolidaysTable.Columns.NOTE))
        );
    }
}
