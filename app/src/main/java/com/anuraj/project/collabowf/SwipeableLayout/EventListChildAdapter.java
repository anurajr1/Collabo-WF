/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 3/5/19 11:41 AM
 *
 */

package com.anuraj.project.collabowf.SwipeableLayout;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;

import java.util.ArrayList;

public class EventListChildAdapter extends RecyclerView.Adapter<EventListChildAdapter.MyViewHolder> {

    //private List<Movie> moviesList;

    private EventInformation eventInformation;
    private ArrayList<Events> eventsArrayList;
    private Activity activity;

    public EventListChildAdapter(Activity activity, ArrayList<Events> eventsArrayList) {
        this.eventsArrayList = eventsArrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alert_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        Events events = eventsArrayList.get(position);
        holder.event_list_event_name.setText(events.getEventName());
        holder.event_list_event_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("event name=",eventsArrayList.get(position).getEventName());
            }
        });



    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_list_event_name;


        public MyViewHolder(View view) {
            super(view);
            event_list_event_name = (TextView) view.findViewById(R.id.name);

        }
    }
}