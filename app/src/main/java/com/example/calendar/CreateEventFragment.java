package com.example.calendar;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.calendar.calendar.CalendarFragment;
import com.example.calendar.database.Event_dbModel;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private EditText editTextTitle, editTextNote;
    private TextView selectDateTextView;
    private Spinner spinnerNotify;
    private TextView textViewStartTime, textViewEndTime;
    private Button saveButton;
    private int startEventHour = -1, startEventMinute = 0,
    endEventHour = -1, endEventMinute = 0;
    private int notifyPrior = 0;
    private ImageView notifyDateTimeImageView;
    private TextView textViewNotificationDateTime;
    private int notifyYear, notifyMonth, notifyDay, notifyHour, notifyMinute;
    private boolean chooseFromDropDown;
    private ImageView backButton;
    private int createEventYear;
    private int createEventMonth;
    private int createEventDay;
    private LocalDate selectedDate;
    public CreateEventFragment() {
        // Required empty public constructor
    }
    public CreateEventFragment(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
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
        selectDateTextView = v.findViewById(R.id.textViewSelectDate);
        textViewStartTime = v.findViewById(R.id.textViewStartTime);
        textViewEndTime = v.findViewById(R.id.textViewEndTime);
        editTextNote = v.findViewById(R.id.editTextNote);
        spinnerNotify = v.findViewById(R.id.spinnerNotify);
        saveButton = v.findViewById(R.id.buttonSave);
        notifyDateTimeImageView = v.findViewById(R.id.ImageViewDateTime);
        textViewNotificationDateTime = v.findViewById(R.id.textViewSelectedNotificationDateTime);
        backButton = v.findViewById(R.id.backButton_fragment_create_event);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
        createNotificationChannel();

        selectDateTextView.setText(selectedDate.toString());

        createEventYear = selectedDate.getYear();
        createEventMonth = selectedDate.getMonthValue();
        createEventDay = selectedDate.getDayOfMonth();
        selectDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        createEventYear = year;
                        createEventMonth = month+1;
                        createEventDay = day;

                        selectDateTextView.setText(LocalDate.of(createEventYear, createEventMonth, createEventDay).toString());
                    }
                }, createEventYear, createEventMonth-1, createEventDay);
                datePickerDialog.setTitle("Select the notification time");

                datePickerDialog.show();
            }
        });

        TimePickerDialog startTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int ii) {
                startEventHour = i;
                startEventMinute = ii;
                textViewStartTime.setText(String.format("%02d:%02d", startEventHour, startEventMinute));
                spinnerNotify.setVisibility(View.VISIBLE);
            }
        }, 0, 0, false);
        startTimePicker.setTitle("Select start time");

        TimePickerDialog endTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int ii) {
                endEventHour = i;
                endEventMinute = ii;
                textViewEndTime.setText(String.format("%02d:%02d", endEventHour, endEventMinute));
            }
        }, 0, 0, false);
        endTimePicker.setTitle("Select end time");

        textViewStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimePicker.show();
            }
        });
        textViewEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimePicker.show();
            }
        });

        ArrayList<String> a = new ArrayList<>();
        a.add("Notification time");
        a.add("15 minutes before");
        a.add("1 hour before");
        a.add("1 day before");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, a);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNotify.setAdapter(arrayAdapter);


        spinnerNotify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    chooseFromDropDown = false;
                    notifyPrior = 0;
                    notifyDateTimeImageView.setVisibility(View.VISIBLE);
                }else if(i == 1){
                    chooseFromDropDown = true;
                    notifyPrior = 15;
                    notifyDateTimeImageView.setVisibility(View.GONE);
                }else if(i == 2){
                    chooseFromDropDown = true;
                    notifyPrior = 60;
                    notifyDateTimeImageView.setVisibility(View.GONE);
                }else if(i == 3){
                    chooseFromDropDown = true;
                    notifyPrior = 60 * 24;
                    notifyDateTimeImageView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        notifyYear = createEventYear;
        notifyMonth = createEventMonth;
        notifyDay = createEventDay;

        notifyDateTimeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        notifyYear = year;
                        notifyMonth = month+1;
                        notifyDay = day;

                        notificationTime();
                    }
                }, notifyYear, notifyMonth-1, notifyDay);

                datePickerDialog.show();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title;

                if(editTextTitle.getText().toString().equals("")){
                    title = "No title";
                }else{
                    title = editTextTitle.getText().toString();
                }

                Intent intent = new Intent(getActivity(), ReminderBroadcast.class);
//                intent.setAction("com.example.calendar.ACTION_SHOW_NOTIFICATION");
                intent.putExtra("title", title);
                intent.putExtra("note", editTextNote.getText().toString());

                PendingIntent pendingIntent = PendingIntent.getBroadcast( getContext(), 0, intent, PendingIntent.FLAG_MUTABLE);

                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);


                if(chooseFromDropDown){
                    try{
                        LocalDateTime localDateTime = LocalDateTime.of(
                                createEventYear,
                                createEventMonth,
                                createEventDay,
                                startEventHour,
                                startEventMinute,
                                0);
                        LocalDateTime newDateTime = localDateTime.minusSeconds(notifyPrior * 60);
                        ZonedDateTime zdt = newDateTime.atZone(ZoneId.of("Asia/Colombo"));
                        long notifyTimeMillis = zdt.toInstant().toEpochMilli();

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                                notifyTimeMillis,
                                pendingIntent);


                    }catch(DateTimeException e){

                    }
                }
                else{
                    try{
                        LocalDateTime localDateTime = LocalDateTime.of(notifyYear, notifyMonth, notifyDay, notifyHour, notifyMinute, 0);
                        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Colombo"));
                        long startTime = zonedDateTime.toInstant().toEpochMilli();

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);

                    }catch(DateTimeException e){

                    }
                }

                Event_dbModel dbModel = new Event_dbModel();
                dbModel.load(getContext());
                dbModel.addEvent(new Event(
                        createEventYear,
                        createEventMonth,
                        createEventDay,
                        startEventHour,
                        startEventMinute,
                        endEventHour,
                        endEventMinute,
                        notifyPrior,
                        title,
                        editTextNote.getText().toString()
                ));


                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                CalendarFragment calendarFragment = new CalendarFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer_MainActivity, calendarFragment).commit();

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CalendarFragment calendarFragment = new CalendarFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer_MainActivity, calendarFragment).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CalendarFragment calendarFragment = new CalendarFragment();
                fm.beginTransaction().replace(R.id.fragmentContainer_MainActivity, calendarFragment).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        return v;
    }

    private void notificationTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                notifyHour = hour;
                notifyMinute = minute;

                textViewNotificationDateTime.setText(
                        String.format("%02d:%02d %02d/%02d/%d", notifyHour, notifyMinute, notifyDay, notifyMonth + 1, notifyYear)
                );
            }
        }, startEventHour, startEventMinute, false);

        timePickerDialog.show();
    }


    private void createNotificationChannel(){
        CharSequence name = "Notification channel";
        String description = "Calendar events notification";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("111E2", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}