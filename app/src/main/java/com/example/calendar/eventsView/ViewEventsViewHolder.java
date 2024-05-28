package com.example.calendar.eventsView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.R;

public class ViewEventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView title;
    public final TextView note;
    private final ViewEventsAdapter.OnItemListener onItemListener;
    public ViewEventsViewHolder(@NonNull View itemView,  ViewEventsAdapter.OnItemListener onItemListener) {
        super(itemView);

        title = itemView.findViewById(R.id.viewTitle);
        note = itemView.findViewById(R.id.viewNote);

        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick();
    }
}
