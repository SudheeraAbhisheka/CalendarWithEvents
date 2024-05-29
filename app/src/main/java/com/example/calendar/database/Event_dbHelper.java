package com.example.calendar.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.calendar.database.Event_dbSchema.EventsTable;

public class Event_dbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "calendar_events.db";
    public Event_dbHelper(Context theContext) {
        super(theContext, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+EventsTable.NAME+"("+
                EventsTable.Columns.YEAR + " INTEGER, "+
                EventsTable.Columns.MONTH + " INTEGER, "+
                EventsTable.Columns.DAY + " INTEGER, "+
                EventsTable.Columns.START_TIME_HOUR + " INTEGER, "+
                EventsTable.Columns.START_TIME_MINUTE + " INTEGER, "+
                EventsTable.Columns.END_TIME_HOUR + " INTEGER, "+
                EventsTable.Columns.END_TIME_MINUTE + " INTEGER, "+
                EventsTable.Columns.NOTIFY_PRIOR + " INTEGER, "+
                EventsTable.Columns.TITLE + " Text, "+
                EventsTable.Columns.NOTE+ " Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
