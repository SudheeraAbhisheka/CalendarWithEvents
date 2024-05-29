package com.example.calendar;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.calendar.calendar.CalendarFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        CalendarFragment calendarFragment;

        calendarFragment = new CalendarFragment();
        fm.beginTransaction().replace(R.id.fragmentContainer_MainActivity, calendarFragment).commit();

    }
}