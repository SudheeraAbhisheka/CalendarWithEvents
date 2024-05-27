package com.example.calendar;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        CalendarFragment calendarFragment;

        calendarFragment = new CalendarFragment();
        fm.beginTransaction().replace(R.id.fragmentContainer_MainActivity, calendarFragment).commit();

    }

    @Override
    protected void onStop() {
        super.onStop();

        new NotificationService();

    }
}