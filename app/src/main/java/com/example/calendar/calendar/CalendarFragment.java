package com.example.calendar.calendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendar.Event;
import com.example.calendar.CreateEventFragment;
import com.example.calendar.Holiday;
import com.example.calendar.R;
import com.example.calendar.database.Event_dbModel;
import com.example.calendar.eventsView.ViewEventsFragment;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LongSummaryStatistics;

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
    private Event_dbModel database;
    private boolean LONG_CLICKED;
    Button previousMonthButton;
    Button nextMonthButton;
    Button buttonAddEvent;
    public CalendarFragment() {
        // Required empty public constructor
    }
    public CalendarFragment(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
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

        database = new Event_dbModel();
        database.load(getContext());

        if(database.getHolidaysInADay(2024, 01, 01).isEmpty()){
            createHolidaysTableOnce();
        }

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
        buttonAddEvent = v.findViewById(R.id.buttonAddEvent);

        selectedDate = LocalDate.now();

        setMonthView();

        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusMonths(1);
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


    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));

        CalendarAdapter calendarAdapter = new CalendarAdapter(
                database,
                daysInMonthArray(),
                YearMonth.from(selectedDate),
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
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue()-1;

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

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @Override
    public void onItemClick(int selectedDay)
    {
        if(!LONG_CLICKED){
            if(selectedDay != 0){
                selectedDate = LocalDate.of(selectedDate.getYear(), selectedDate.getMonthValue(), selectedDay);

                ArrayList<Event> eventsList =
                        database.getEventsInADay(selectedDate.getYear(), selectedDate.getMonthValue(), selectedDay);

                eventsList.addAll(
                        database.getHolidaysInADay(selectedDate.getYear(), selectedDate.getMonthValue(), selectedDay)
                );

                if(eventsList.isEmpty()){
                    Toast.makeText(getActivity(), selectedDate.toString() + " selected", Toast.LENGTH_SHORT).show();

                }
                else{
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    ViewEventsFragment viewEventsFragment = new ViewEventsFragment(selectedDate, eventsList, database);
                    fm.beginTransaction().replace(R.id.fragmentContainer_MainActivity, viewEventsFragment).commit();
                }
            }
        }

        LONG_CLICKED = false;
    }

    @Override
    public void onLongClickListener(int selectedDay) {
        if(selectedDay != 0) {
            selectedDate = LocalDate.of(selectedDate.getYear(), selectedDate.getMonthValue(), selectedDay);
            Toast.makeText(getActivity(), selectedDate.toString() + " selected", Toast.LENGTH_SHORT).show();
        }
        LONG_CLICKED = true;
    }


    private void createHolidaysTableOnce(){
        database.addHoliday(new Holiday(2024, 01, 01, "New Year's Day", "National holiday", true));
        database.addHoliday(new Holiday(2024, 01, 15, "Tamil Thai Pongal Day", "Cultural holiday", true));
        database.addHoliday(new Holiday(2024, 02, 04, "Independence Day", "National holiday", true));
        database.addHoliday(new Holiday(2024, 03, 8, "Mahashivratri", "Religious holiday", true));
        database.addHoliday(new Holiday(2024, 03, 29, "Good Friday", "Christian holiday", true));
        database.addHoliday(new Holiday(2024, 03, 31, "Easter Sunday", "Christian holiday", true));
        database.addHoliday(new Holiday(2024, 04, 10, "Bak Full Moon Poya Day", "Religious holiday", true));
        database.addHoliday(new Holiday(2024, 04, 13, "Sinhala and Tamil New Year's Eve", "Cultural holiday", true));
        database.addHoliday(new Holiday(2024, 04, 14, "Sinhala and Tamil New Year", "Cultural holiday", true));
        database.addHoliday(new Holiday(2024, 04, 21, "Good Friday", "Christian holiday", true));
        database.addHoliday(new Holiday(2024, 05, 01, "May Day", "National holiday", true));
        database.addHoliday(new Holiday(2024, 05, 23, "Vesak Full Moon Poya Day", "Religious holiday", true));
        database.addHoliday(new Holiday(2024, 05, 24, "Day after Vesak Full Moon Poya Day", "Religious holiday", true));
        database.addHoliday(new Holiday(2024, 06, 21, "Poson Full Moon Poya Day", "Religious holiday", true));
        database.addHoliday(new Holiday(2024, 07, 19, "Eid al-Adha", "Religious holiday", true));
        database.addHoliday(new Holiday(2024, 07, 20, "Esala Full Moon Poya Day", "Religious holiday", true));
        database.addHoliday(new Holiday(2024, 8, 19, "Raksha Bandhan", "Cultural holiday", true));
        database.addHoliday(new Holiday(2024, 9, 16, "Binara Full Moon Poya Day", "Religious holiday", true));
        database.addHoliday(new Holiday(2024, 10, 14, "Vap Full Moon Poya Day", "Religious holiday", true));
        database.addHoliday(new Holiday(2024, 10, 23, "Deepavali", "Cultural holiday", true));

    }
}