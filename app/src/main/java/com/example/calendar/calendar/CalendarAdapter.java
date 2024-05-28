package com.example.calendar.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.CalendarEventOriginal;
import com.example.calendar.R;
import com.example.calendar.database.Event_dbModel;

import java.time.YearMonth;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    private ArrayList<CalendarEventOriginal> eventsList;
    private final OnItemListener onItemListener;
    private Event_dbModel eventDatabase;
    private YearMonth yearMonth;

    public CalendarAdapter(ArrayList<String> daysOfMonth, YearMonth yearMonth, ArrayList<CalendarEventOriginal> eventsList, OnItemListener onItemListener)
    {
        this.daysOfMonth = daysOfMonth;
        this.yearMonth = yearMonth;
//        this.eventsList = eventsList;
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

        eventDatabase = new Event_dbModel();
        eventsList = new ArrayList<>();
        eventDatabase.load(parent.getContext());

        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        if(daysOfMonth.get(position) == ""){
            holder.constraintLayout.setBackgroundResource(android.R.color.transparent);
//            holder.dayOfMonth.tex
        }
        else{
            holder.dayOfMonth.setText(daysOfMonth.get(position));
        }

        if(daysOfMonth.get(position) != ""){
            eventsList = eventDatabase.getEventsInADay(yearMonth.getYear(), yearMonth.getMonthValue(), Integer.parseInt(daysOfMonth.get(position)));

            try{
                holder.eventHolder1.setText(eventsList.get(0).getTitle());
                holder.eventHolder2.setText(eventsList.get(1).getTitle());
                holder.eventHolder3.setText(eventsList.get(2).getTitle());

            }catch (IndexOutOfBoundsException e){

            }

            if(eventsList.size() >= 3){
                holder.eventHolder_more.setText("....");
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, int selectedDay);
    }
}
