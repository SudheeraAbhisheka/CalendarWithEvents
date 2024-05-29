package com.example.calendar;

import static android.content.ContentValues.TAG;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.calendar.calendar.CalendarFragment;
import com.example.calendar.database.Event_dbModel;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FragmentManager fm = getSupportFragmentManager();
        CalendarFragment calendarFragment;

        calendarFragment = new CalendarFragment();
        fm.beginTransaction().replace(R.id.fragmentContainer_MainActivity, calendarFragment).commit();


    }
}