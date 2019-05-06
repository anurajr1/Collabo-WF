/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 6/5/19 11:25 AM
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
import com.anuraj.project.collabowf.SwipeableLayout.EventListParentAdapter;
import com.anuraj.project.collabowf.model.AlertModel;
import com.anuraj.project.collabowf.model.EventDates;
import com.anuraj.project.collabowf.model.EventInformation;
import com.anuraj.project.collabowf.model.Events;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AlertFragment extends Fragment {

    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    View rootView;
    AlertModel alertModel;
    RecyclerView event_recycler_view_parent;
    EventListParentAdapter event_list_parent_adapter;
    EventInformation eventInformation = new EventInformation();;

    public AlertFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.alertfragment, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        setAlertsDataAdapterAndRecyclerView();
        return rootView;
    }

    private void setAlertsDataAdapterAndRecyclerView() {
        ArrayList<EventDates> eventDatesArrayList;
        eventDatesArrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("alerts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    EventDates eventDates = new EventDates();
                    eventDates.setDate(dataSnapshot.getKey());
                    ArrayList<Events> eventsArrayList = new ArrayList<>();

                    for (DataSnapshot dataAlert : dataSnapshot.getChildren()) {
                        alertModel = dataAlert.getValue(AlertModel.class);
                        Events events = new Events();
                        if((alertModel.getStatus().equalsIgnoreCase("On Leave")) && (alertModel.getSupervisorseen().equalsIgnoreCase("false"))) {
                            events.setEventId(alertModel.getId());
                            events.setEventName(alertModel.getName() + " requested for Leave");
                        }else if(alertModel.getSupervisorseen().equalsIgnoreCase("false")){
                            events.setEventId(alertModel.getId());
                            events.setEventName(alertModel.getName() + " assigned to " + alertModel.getStatus());
                        }
                        //to handle the null alert listing
                        if(events.getEventId()!=null){
                            eventsArrayList.add(events);
                        }
                }
                    if(!eventsArrayList.isEmpty()){
                        eventDates.setEventsArrayList(eventsArrayList);
                        eventDatesArrayList.add(eventDates);
                    }
                }
                eventInformation.setEventsDatesList(eventDatesArrayList);
                Log.d("Alert",eventInformation.toString());
                //parent recyclerview
                event_recycler_view_parent = (RecyclerView) rootView.findViewById(R.id.recyclerView);
                event_list_parent_adapter = new EventListParentAdapter(eventInformation,getActivity());
                event_recycler_view_parent.setHasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                event_recycler_view_parent.setLayoutManager(mLayoutManager);
                event_recycler_view_parent.setItemAnimator(new DefaultItemAnimator());
                event_recycler_view_parent.setAdapter(event_list_parent_adapter);
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });
    }
}
