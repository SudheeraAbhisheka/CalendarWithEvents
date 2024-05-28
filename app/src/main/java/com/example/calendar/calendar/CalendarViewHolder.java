package com.example.calendar.calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public final TextView dayOfMonth;
    public final TextView eventHolder1, eventHolder2, eventHolder3, eventHolder_more;
    public final ConstraintLayout constraintLayout;

    private final CalendarAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener)
    {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        eventHolder1 = itemView.findViewById(R.id.textViewEvent1);
        eventHolder2 = itemView.findViewById(R.id.textViewEvent2);
        eventHolder3 = itemView.findViewById(R.id.textViewEvent3);
        eventHolder_more = itemView.findViewById(R.id.textViewEventHolderMore);
        constraintLayout = itemView.findViewById(R.id.calendarCellConstraintLayout);

        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        int i = 0;
        try{
            i = Integer.parseInt(dayOfMonth.getText().toString());

        }catch (NumberFormatException e){

        }
        onItemListener.onItemClick(getAdapterPosition(), i);
    }
}
