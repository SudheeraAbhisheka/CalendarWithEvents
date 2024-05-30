package com.example.calendar.calendar;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.Event;
import com.example.calendar.Holiday;
import com.example.calendar.R;
import com.example.calendar.database.Event_dbModel;

import java.time.YearMonth;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    private ArrayList<Event> eventsList;
    private final OnItemListener onItemListener;
    private Event_dbModel eventDatabase;
    private YearMonth yearMonth;

    public CalendarAdapter(Event_dbModel eventDatabase, ArrayList<String> daysOfMonth, YearMonth yearMonth, OnItemListener onItemListener)
    {
        this.eventDatabase = eventDatabase;
        this.daysOfMonth = daysOfMonth;
        this.yearMonth = yearMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        eventsList = new ArrayList<>();

        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        if(!daysOfMonth.get(position).equals("")){
            holder.dayOfMonth.setText(daysOfMonth.get(position));
            holder.constraintLayout.setVisibility(View.VISIBLE);

            eventsList = eventDatabase.getEventsInADay(yearMonth.getYear(), yearMonth.getMonthValue(), Integer.parseInt(daysOfMonth.get(position)));
            eventsList.addAll(
                    eventDatabase.getHolidaysInADay(yearMonth.getYear(), yearMonth.getMonthValue(), Integer.parseInt(daysOfMonth.get(position)))
            );

        }


        if(eventsList.size() >= 3){
            holder.eventHolder_more.setText("....");

            holder.eventHolder1.setVisibility(View.VISIBLE);
            holder.eventHolder2.setVisibility(View.VISIBLE);
            holder.eventHolder3.setVisibility(View.VISIBLE);

            holder.eventHolder1.setText(eventsList.get(0).getTitle());
            holder.eventHolder2.setText(eventsList.get(1).getTitle());
            holder.eventHolder3.setText(eventsList.get(2).getTitle());

        }else if(eventsList.size() == 2){
            holder.eventHolder1.setVisibility(View.VISIBLE);
            holder.eventHolder2.setVisibility(View.VISIBLE);

            holder.eventHolder1.setText(eventsList.get(0).getTitle());
            holder.eventHolder2.setText(eventsList.get(1).getTitle());

        }else if(eventsList.size() == 1){
            holder.eventHolder1.setVisibility(View.VISIBLE);

            holder.eventHolder1.setText(eventsList.get(0).getTitle());
        }
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int selectedDay);
        void onLongClickListener(int selectedDay);
    }
}
