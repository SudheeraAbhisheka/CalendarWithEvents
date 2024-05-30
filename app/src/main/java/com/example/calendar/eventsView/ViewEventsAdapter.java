package com.example.calendar.eventsView;

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

import java.util.ArrayList;

public class ViewEventsAdapter extends RecyclerView.Adapter<ViewEventsViewHolder> {
    private ArrayList<Event> eventsList;
    public ViewEventsAdapter(ArrayList<Event> eventsList){
        this.eventsList = eventsList;
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
        holder.title.setText(eventsList.get(position).getTitle());
        holder.note.setText(eventsList.get(position).getNote());

        Log.d(TAG, eventsList.get(position).getStartTimeHour() + " " + eventsList.get(position).getEndTimeHour());


        if(eventsList.get(position).getStartTimeHour() != 0){
            holder.startTime.setVisibility(View.VISIBLE);
            holder.startTime.setText(
                    String.format("Start time: %02d:%02d", eventsList.get(position).getStartTimeHour(), eventsList.get(position).getStartTimeMinute())
            );
        }

        if(eventsList.get(position).getEndTimeHour() != 0){
            holder.endTime.setVisibility(View.VISIBLE);
            holder.endTime.setText(
                    String.format("End time: %02d:%02d", eventsList.get(position).getEndTimeHour(), eventsList.get(position).getEndTimeMinute())
            );
        }

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }
}
