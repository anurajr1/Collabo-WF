/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 3/5/19 11:25 AM
 *
 */

package com.anuraj.project.collabowf.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.SwipeableLayout.AlertDataAdapter;
import com.anuraj.project.collabowf.SwipeableLayout.EventDates;
import com.anuraj.project.collabowf.SwipeableLayout.EventInformation;
import com.anuraj.project.collabowf.SwipeableLayout.EventListParentAdapter;
import com.anuraj.project.collabowf.SwipeableLayout.Events;
import com.anuraj.project.collabowf.SwipeableLayout.SwipeController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AlertFragment extends Fragment {
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;
    private AlertDataAdapter mAdapter;
    SwipeController swipeController = null;
    View rootView;

    RecyclerView event_recycler_view_parent, child;
    EventListParentAdapter event_list_parent_adapter;

    EventInformation eventInformation;

    public AlertFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.alertfragment, container, false);

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading Data");

        progressDialog.show();

        setPlayersDataAdapter();
        setupRecyclerView();

        return rootView;
    }


    private void setPlayersDataAdapter() {

        ArrayList<EventDates> eventDatesArrayList;
        eventInformation = new EventInformation();


        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

        try {
            eventDatesArrayList = new ArrayList<>();
            for (int indexDates=0;indexDates<5;indexDates++){
                EventDates eventDates = new EventDates();
                String date = "12/10/19";
                eventDates.setDate(date);
                ArrayList<Events> eventsArrayList = new ArrayList<>();
                for (int indexEvents=0;indexEvents<4;indexEvents++){
                    Events events = new Events();
                    events.setEventId("1");
                    events.setEventName("anuraj");
                    eventsArrayList.add(events);
                }
                eventDates.setEventsArrayList(eventsArrayList);
                eventDatesArrayList.add(eventDates);
            }
            eventInformation.setEventsDatesList(eventDatesArrayList);
            Log.d("message",eventInformation.toString());
        }catch (Exception e){

        }
    }

    private void setupRecyclerView() {
//        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.setAdapter(mAdapter);
//
//        swipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onRightClicked(int position) {
//                mAdapter.alerts.remove(position);
//                mAdapter.notifyItemRemoved(position);
//                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
//            }
//        });
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(recyclerView);
//
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                swipeController.onDraw(c);
//            }
//        });


        //parent recyclerview
        event_recycler_view_parent = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        //child = (RecyclerView) rootView.findViewById(R.id.event_recycler_view_child);

        event_list_parent_adapter = new EventListParentAdapter(eventInformation,getActivity());
        event_recycler_view_parent.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        event_recycler_view_parent.setLayoutManager(mLayoutManager);
        event_recycler_view_parent.setItemAnimator(new DefaultItemAnimator());
        event_recycler_view_parent.setAdapter(event_list_parent_adapter);

    }
}
