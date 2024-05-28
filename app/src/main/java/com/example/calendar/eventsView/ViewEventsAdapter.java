package com.example.calendar.eventsView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.CalendarEventOriginal;
import com.example.calendar.R;

import java.util.ArrayList;

public class ViewEventsAdapter extends RecyclerView.Adapter<ViewEventsViewHolder> {
    private final OnItemListener onItemListener = null;
    private CalendarEventOriginal event;
    private ArrayList<CalendarEventOriginal> eventsArray;
    public ViewEventsAdapter(ArrayList<CalendarEventOriginal> eventsArray){
        this.eventsArray = eventsArray;
    }
    @NonNull
    @Override
    public ViewEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_cell,parent,false);

        return new ViewEventsViewHolder(view, onItemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewEventsViewHolder holder, int position) {
        holder.title.setText(eventsArray.get(position).getTitle());
        holder.note.setText(eventsArray.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return eventsArray.size();
    }
    public interface  OnItemListener
    {
        void onItemClick();
    }
}
