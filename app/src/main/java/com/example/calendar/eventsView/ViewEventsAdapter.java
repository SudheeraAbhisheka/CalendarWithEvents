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
import com.example.calendar.database.Event_dbModel;

import java.util.ArrayList;

public class ViewEventsAdapter extends RecyclerView.Adapter<ViewEventsViewHolder> {
    private ArrayList<Event> eventsList;
    private Event_dbModel database;
    public ViewEventsAdapter(ArrayList<Event> eventsList, Event_dbModel database){
        this.eventsList = eventsList;
        this.database = database;
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

        // !!! Need to fix this
        // Start and end time, set at 12AM doesn't display
        if(eventsList.get(position).getStartTimeHour() == 0
        & eventsList.get(position).getStartTimeMinute() == 0){

        }
        else{
            holder.startTime.setVisibility(View.VISIBLE);
            holder.startTime.setText(
                    String.format("Start time: %02d:%02d", eventsList.get(position).getStartTimeHour(), eventsList.get(position).getStartTimeMinute())
            );
        }

        if(eventsList.get(position).getEndTimeHour() == 0
                & eventsList.get(position).getEndTimeMinute() == 0){

        }
        else{
            holder.endTime.setVisibility(View.VISIBLE);
            holder.endTime.setText(
                    String.format("End time: %02d:%02d", eventsList.get(position).getEndTimeHour(), eventsList.get(position).getEndTimeMinute())
            );
        }

        if(!eventsList.get(position).isHoliday()){
            holder.removeIcon.setVisibility(View.VISIBLE);


            holder.removeIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    database.deleteEvent(
                            eventsList.get(position).getYear(),
                            eventsList.get(position).getMonth(),
                            eventsList.get(position).getDay(),
                            eventsList.get(position).getTitle(),
                            0);

                    eventsList.remove(position);

                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, eventsList.size());
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }
}
