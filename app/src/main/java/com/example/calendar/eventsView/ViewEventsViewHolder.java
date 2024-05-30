package com.example.calendar.eventsView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.R;

public class ViewEventsViewHolder extends RecyclerView.ViewHolder {
    public final TextView title;
    public final TextView note;
    public final TextView startTime;
    public final TextView endTime;
    public final ImageView removeIcon;
    public ViewEventsViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.viewTitle);
        note = itemView.findViewById(R.id.viewNote);
        startTime = itemView.findViewById(R.id.textViewStartTimeHolder);
        endTime = itemView.findViewById(R.id.textViewEndTimeHolder);
        removeIcon = itemView.findViewById(R.id.imageViewRemove);
    }
}
