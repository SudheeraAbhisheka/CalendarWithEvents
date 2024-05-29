package com.example.calendar.eventsView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.R;

public class ViewEventsViewHolder extends RecyclerView.ViewHolder {
    public final TextView title;
    public final TextView note;
    public ViewEventsViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.viewTitle);
        note = itemView.findViewById(R.id.viewNote);
    }
}
