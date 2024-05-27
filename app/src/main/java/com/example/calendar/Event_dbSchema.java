package com.example.calendar;

public class Event_dbSchema {
    public static class EventsTable{
        public static final String NAME = "event";
        public static class Columns{
            public static final String YEAR = "year";
            public static final String MONTH = "month";
            public static final String DAY = "day";
            public static final String START_TIME = "startTime";
            public static final String END_TIME = "endTime";
            public static final String NOTIFY_PRIOR = "notifyPrior";
            public static final String TITLE = "title";
            public static final String NOTE = "note";

        }
    }
}
