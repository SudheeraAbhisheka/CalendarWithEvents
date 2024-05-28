package com.example.calendar;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private ArrayList<CalendarEventOriginal> eventsList;
    private Event_dbModel eventDatabase;
    Button previousMonthButton;
    Button nextMonthButton;
    TextView selectedTextTextView;
    Button buttonAddEvent;
    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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

        eventDatabase = new Event_dbModel();
        eventsList = new ArrayList<>();

//        CalendarEventString calendarEventString;
//        CalendarEventOriginal cEvent;
//
//        SharedPreferences settings = getContext().getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
//
//        Map<String, ?> allEntries = settings.getAll();
//
//        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
////            Gson gson = new Gson();
////            calendarEventString = gson.fromJson(entry.getValue().toString(), CalendarEventString.class);
////            cEvent = calendarEventOriginal(calendarEventString);
////            YearMonth yearMonth = YearMonth.from(cEvent.getDate());
//
////            eventsList.add(calendarEventString);
//        }

        eventDatabase = new Event_dbModel();
        eventDatabase.load(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarRecyclerView = v.findViewById(R.id.calendarRecyclerView);
        monthYearText = v.findViewById(R.id.monthYearTV);
        previousMonthButton = v.findViewById(R.id.ButtonPreviousMonth);
        nextMonthButton = v.findViewById(R.id.ButtonNextMonth);
        selectedTextTextView = v.findViewById(R.id.textViewSelectedDate);
        buttonAddEvent = v.findViewById(R.id.buttonAddEvent);

        selectedDate = LocalDate.now();
        selectedTextTextView.setText(selectedDate.toString());
        setMonthView();

        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusMonths(1);
                selectedTextTextView.setText(selectedDate.toString());
                setMonthView();
            }
        });

        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusMonths(1);
                selectedTextTextView.setText(selectedDate.toString());
                setMonthView();
            }
        });

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CreateEventFragment createEventFragment;

                createEventFragment = new CreateEventFragment(selectedDate);
                fm.beginTransaction().replace(R.id.fragmentContainer_MainActivity, createEventFragment).commit();
            }
        });

        return v;
    }

//    LocalTime startTime = LocalTime.of(startEventHour, startEventMinute);
//    LocalTime endTime = LocalTime.of(endEventHour, endEventMinute);

//    private CalendarEventOriginal calendarEventOriginal(CalendarEventString s){
//        LocalTime startTime = null, endTime = null;
//        long notifyPrior = 0;
//        LocalDate date;
//
//        date = LocalDate.parse(s.getDate());
//
//        if(s.getStartTime() != "")
//            startTime = LocalTime.parse(s.getStartTime());
//        if(s.getEndTime() != "")
//            endTime = LocalTime.parse(s.getEndTime());
//        if(s.getNotifyPrior() != "")
//            notifyPrior = Long.valueOf(s.getNotifyPrior());
//
//        return new CalendarEventOriginal(s.getTitle(), date, startTime, endTime, s.getNote(), notifyPrior);
//    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
//        ArrayList<CalendarEventOriginal> eventsInMonth = eventsInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(
                daysInMonthArray(),
                eventsInMonthArray(eventDatabase.getEvents(selectedDate.getYear(), selectedDate.getMonthValue())),
                this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray()
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectedDate);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }
    private ArrayList<CalendarEventOriginal> eventsInMonthArray(ArrayList<CalendarEventOriginal> eventsArray)
    {
        ArrayList<CalendarEventOriginal> eventsInMonthArray = new ArrayList<>();
        ArrayList<CalendarEventOriginal> eventsInDayArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectedDate);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                eventsInMonthArray.add(null);
            }
            else
            {
                for(CalendarEventOriginal c : eventsArray){
                    if(c.getDay() == i - dayOfWeek){
                        eventsInMonthArray.add(c);
                    }
                }
            }
        }
        return  eventsInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @Override
    public void onItemClick(int position, int selectedDay)
    {
        if(selectedDay != 0){
            selectedDate = LocalDate.of(selectedDate.getYear(), selectedDate.getMonthValue(), selectedDay);
            selectedTextTextView.setText(selectedDate.toString());
        }
    }
}