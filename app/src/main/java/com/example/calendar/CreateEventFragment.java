package com.example.calendar;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LocalDate selectedDate;
    private EditText editTextTitle, editTextDate, editTextNote, editTextNotifyPrior;
    private TextView textViewStartTime, textViewEndTime;
    private Button saveButton;
    private int hour = 0, minute = 0;
    private ArrayList<CalendarEventString> eventsList;
    public CreateEventFragment() {
        // Required empty public constructor
    }

    public CreateEventFragment(LocalDate selectedDate, ArrayList<CalendarEventString> eventsList) {
        this.selectedDate = selectedDate;
        this.eventsList = eventsList;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFragment newInstance(String param1, String param2) {
        CreateEventFragment fragment = new CreateEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_event, container, false);

        editTextTitle = v.findViewById(R.id.editTextTitle);
        editTextDate = v.findViewById(R.id.editTextDate);
        textViewStartTime = v.findViewById(R.id.textViewStartTime);
        textViewEndTime = v.findViewById(R.id.textViewEndTime);
        editTextNote = v.findViewById(R.id.editTextNote);
        editTextNotifyPrior = v.findViewById(R.id.editTextNotifyPrior);
        saveButton = v.findViewById(R.id.buttonSave);

        editTextDate.setText(selectedDate + "");


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        createNotificationChannel();

        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int ii) {
                hour = i;
                minute = ii;
                textViewStartTime.setText(hour+":"+minute);
            }
        }, hour, minute, false);

        textViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.show();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ReminderBroadcast.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
//
//                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
//
//
//                long timeAtButtonClick = System.currentTimeMillis();
//
//                alarmManager.set(AlarmManager.RTC_WAKEUP,
//                        timeAtButtonClick + 2*1000,
//                        pendingIntent);

                CalendarEventString calendarEventString = new CalendarEventString(
                        editTextTitle.getText().toString(),
                        editTextDate.getText().toString(),
                        textViewStartTime.getText().toString(),
                        textViewEndTime.getText().toString(),
                        editTextNote.getText().toString(),
                        editTextNotifyPrior.getText().toString()
                );

                String jsonInString = new Gson().toJson(calendarEventString);

                SharedPreferences settingsIn = getContext().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settingsIn.edit();
                editor.putString(System.currentTimeMillis()+"", jsonInString);
                editor.apply();

                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                CalendarFragment calendarFragment;

                calendarFragment = new CalendarFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer_MainActivity, calendarFragment).commit();

            }
        });

        return v;
    }

    private void createNotificationChannel(){
        CharSequence name = "LemubitReminderChannel";
        String description = "Channel for Lemubit Reminder";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}