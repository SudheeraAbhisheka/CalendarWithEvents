package com.example.calendar.eventsView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.Event;
import com.example.calendar.Holiday;
import com.example.calendar.R;

import java.util.ArrayList;

public class ViewEventsAdapter extends RecyclerView.Adapter<ViewEventsViewHolder> {
    private ArrayList<Event> eventsList;
    private ArrayList<Holiday> holidaysList;
    public ViewEventsAdapter(ArrayList<Event> eventsList, ArrayList<Holiday> holidaysList){
        this.eventsList = eventsList;
        this.holidaysList = holidaysList;
    }
    @NonNull
    @Override
    public ViewEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_cell,parent,false);

        return new ViewEventsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewEventsViewHolder holder, int position) {
        if(position >= eventsList.size()){
            holder.title.setText(holidaysList.get(position - eventsList.size()).getTitle());
            holder.note.setText(holidaysList.get(position - eventsList.size()).getNote());
        }
        else{
            holder.title.setText(eventsList.get(position).getTitle());
            holder.note.setText(eventsList.get(position).getNote());

            String s;
            if(eventsList.get(position).getStartTimeHour() != -1){
                holder.startTime.setVisibility(View.VISIBLE);
                s = String.format("Start time: %02d:%02d", eventsList.get(position).getStartTimeHour(), eventsList.get(position).getStartTimeMinute());
                holder.startTime.setText(s);
            }

            if(eventsList.get(position).getEndTimeHour() != -1){
                holder.endTime.setVisibility(View.VISIBLE);
                s = String.format("End time: %02d:%02d", eventsList.get(position).getEndTimeHour(), eventsList.get(position).getEndTimeMinute());
                holder.endTime.setText(s);
            }
        }

    }

    @Override
    public int getItemCount() {
        return eventsList.size() + holidaysList.size();
    }
}
