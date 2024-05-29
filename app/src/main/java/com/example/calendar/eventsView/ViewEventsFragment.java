package com.example.calendar.eventsView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.calendar.Event;
import com.example.calendar.Holiday;
import com.example.calendar.R;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewEventsFragment extends Fragment implements ViewEventsAdapter.OnItemListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Event> eventsList;
    ArrayList<Holiday> holidaysList;
    TextView dateTextView;
    LocalDate selectedDate;

    public ViewEventsFragment() {
        // Required empty public constructor
    }

    public ViewEventsFragment(LocalDate selectedDate, ArrayList<Event> eventsList, ArrayList<Holiday> holidaysList){
        this.selectedDate = selectedDate;
        this.eventsList = eventsList;
        this.holidaysList = holidaysList;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewEventsFragment newInstance(String param1, String param2) {
        ViewEventsFragment fragment = new ViewEventsFragment();
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
        View v = inflater.inflate(R.layout.fragment_view_events, container, false);

        dateTextView = v.findViewById(R.id.textViewDate);
        dateTextView.setText(selectedDate.toString());

        RecyclerView rv = v.findViewById(R.id.ViewEventsRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ViewEventsAdapter viewEventsAdapter = new ViewEventsAdapter(eventsList, holidaysList);
        rv.setAdapter(viewEventsAdapter);

        return v;
    }

    @Override
    public void onItemClick() {

    }
}