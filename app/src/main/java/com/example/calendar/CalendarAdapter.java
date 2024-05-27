package com.example.calendar;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<String> daysOfMonth;
    private ArrayList<CalendarEventString> eventsList;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<String> daysOfMonth, ArrayList<CalendarEventString> eventsList, OnItemListener onItemListener)
    {
        this.daysOfMonth = daysOfMonth;
        this.eventsList = eventsList;
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
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        if(daysOfMonth.get(position) != ""){
            holder.eventHolder1.setText("Event"+daysOfMonth.get(position));
            holder.eventHolder2.setText("Event"+daysOfMonth.get(position));
            holder.eventHolder3.setText("Event"+daysOfMonth.get(position));
            holder.eventHolder_more.setText("...."+daysOfMonth.get(position));
        }
//        Log.d(TAG, )
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }
}
