package com.example.calendar.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.calendar.Event;
import com.example.calendar.Holiday;
import com.example.calendar.database.Event_dbSchema.EventsTable;
import com.example.calendar.database.Event_dbSchema.HolidaysTable;

public class Event_dbCursor extends CursorWrapper {

    public Event_dbCursor(Cursor cursor) {
        super(cursor);
    }

    public Event getEvent(){
        long notifyPrior = 0;
        boolean isHoliday = false;

        try{
            notifyPrior = Integer.parseInt( getString(getColumnIndex(EventsTable.Columns.NOTIFY_PRIOR)));

        }catch (NumberFormatException e){

        }

        if(getInt(getColumnIndex(EventsTable.Columns.IS_HOLIDAY)) == 1){
            isHoliday = true;
        }

        return new Event(
            getInt(getColumnIndex(EventsTable.Columns.YEAR)),
            getInt(getColumnIndex(EventsTable.Columns.MONTH)),
            getInt(getColumnIndex(EventsTable.Columns.DAY)),
            getInt(getColumnIndex(EventsTable.Columns.START_TIME_HOUR)),
            getInt(getColumnIndex(EventsTable.Columns.START_TIME_MINUTE)),
            getInt(getColumnIndex(EventsTable.Columns.END_TIME_HOUR)),
            getInt(getColumnIndex(EventsTable.Columns.END_TIME_MINUTE)),
            getInt(getColumnIndex(EventsTable.Columns.NOTIFY_PRIOR)),
            getString(getColumnIndex(EventsTable.Columns.TITLE)),
            getString(getColumnIndex(EventsTable.Columns.NOTE)),
            isHoliday
        );
    }
    public Event getHoliday(){
        boolean isHoliday = false;

        if(getInt(getColumnIndex(HolidaysTable.Columns.IS_HOLIDAY)) == 1){
            isHoliday = true;
        }

        return new Event(
                getInt(getColumnIndex(HolidaysTable.Columns.YEAR)),
                getInt(getColumnIndex(HolidaysTable.Columns.MONTH)),
                getInt(getColumnIndex(HolidaysTable.Columns.DAY)),
                getString(getColumnIndex(HolidaysTable.Columns.TITLE)),
                getString(getColumnIndex(HolidaysTable.Columns.NOTE)),
                isHoliday
        );
    }
}
