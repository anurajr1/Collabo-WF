/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 3/5/19 11:39 AM
 *
 */

package com.anuraj.project.collabowf.SwipeableLayout;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;

import java.util.List;

public class EventListParentAdapter extends RecyclerView.Adapter<EventListParentAdapter.MyViewHolder> {

    //private List<Movie> moviesList;

    private EventInformation eventInformation;
    private Activity activity;
    public List<Alert> alerts;

    public EventListParentAdapter(EventInformation eventInformation, Activity activity) {
        this.eventInformation = eventInformation;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_parent_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventDates eventDates = eventInformation.getEventsDatesList().get(position);
        holder.event_list_parent_date.setText(eventDates.getDate());

        LinearLayoutManager hs_linearLayout = new LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false);
        holder.event_recycler_view_child.setLayoutManager(hs_linearLayout);
        holder.event_recycler_view_child.setHasFixedSize(true);
        EventListChildAdapter eventListChildAdapter = new EventListChildAdapter(this.activity,eventInformation.getEventsDatesList().get(position).getEventsArrayList());
        holder.event_recycler_view_child.setAdapter(eventListChildAdapter);

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //eventListChildAdapter.alerts.remove(position);
                eventListChildAdapter.notifyItemRemoved(position);
                eventListChildAdapter.notifyItemRangeChanged(position, holder.event_recycler_view_child.getChildCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(holder.event_recycler_view_child);

        holder.event_recycler_view_child.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventInformation.getEventsDatesList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_list_parent_date;
        public RecyclerView event_recycler_view_child;

        public MyViewHolder(View view) {
            super(view);
            event_list_parent_date = (TextView) view.findViewById(R.id.event_list_parent_date);
            event_recycler_view_child = (RecyclerView)view.findViewById(R.id.event_recycler_view_child);
        }
    }
}
