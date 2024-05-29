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
            if(eventsList.get(position).getTitle().equals("")){
                holder.title.setText("No title");
            }else{
                holder.title.setText(eventsList.get(position).getTitle());
            }

            holder.note.setText(eventsList.get(position).getNote());
        }

    }

    @Override
    public int getItemCount() {
        return eventsList.size() + holidaysList.size();
    }
}
