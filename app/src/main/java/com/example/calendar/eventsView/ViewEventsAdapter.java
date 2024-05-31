package com.example.calendar.eventsView;

import static android.content.ContentValues.TAG;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.Event;
import com.example.calendar.Holiday;
import com.example.calendar.R;
import com.example.calendar.ReminderBroadcast;
import com.example.calendar.database.Event_dbModel;

import java.util.ArrayList;

public class ViewEventsAdapter extends RecyclerView.Adapter<ViewEventsViewHolder> {
    private ArrayList<Event> eventsList;
    private Event_dbModel database;
    private Context context;
    public ViewEventsAdapter(ArrayList<Event> eventsList, Event_dbModel database){
        this.eventsList = eventsList;
        this.database = database;
    }
    @NonNull
    @Override
    public ViewEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_cell,parent,false);

        context = view.getContext();


        return new ViewEventsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewEventsViewHolder holder, int position) {
        Event thisEvent = eventsList.get(position);

        holder.title.setText(thisEvent.getTitle());
        holder.note.setText(thisEvent.getNote());

        // !!! Need to fix this
        // Start and end time, set at 12AM doesn't display
        if(thisEvent.getStartTimeHour() == 0
        & thisEvent.getStartTimeMinute() == 0){

        }
        else{
            holder.startTime.setVisibility(View.VISIBLE);
            holder.startTime.setText(
                    String.format("Start time: %02d:%02d", thisEvent.getStartTimeHour(), thisEvent.getStartTimeMinute())
            );
        }

        if(thisEvent.getEndTimeHour() == 0
                & thisEvent.getEndTimeMinute() == 0){

        }
        else{
            holder.endTime.setVisibility(View.VISIBLE);
            holder.endTime.setText(
                    String.format("End time: %02d:%02d", thisEvent.getEndTimeHour(), thisEvent.getEndTimeMinute())
            );
        }

        if(!thisEvent.isHoliday()){
            holder.removeIcon.setVisibility(View.VISIBLE);


            holder.removeIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    int pendingIntentKey = thisEvent.getPendingIntentKey();
                    createNotificationChannel();

                    Intent intent = new Intent(context, ReminderBroadcast.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast( context, pendingIntentKey, intent, PendingIntent.FLAG_MUTABLE);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);

                    Toast.makeText(context, "Notification " + thisEvent.getTitle() + " cancelled.", Toast.LENGTH_SHORT).show();

                    database.deleteEvent(
                            thisEvent.getYear(),
                            thisEvent.getMonth(),
                            thisEvent.getDay(),
                            thisEvent.getTitle(),
                            0);

                    eventsList.remove(position);

                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, eventsList.size());
                }
            });

        }


    }

    private void createNotificationChannel(){
        CharSequence name = "Notification channel";
        String description = "Calendar events notification";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("111E5", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }
}
