/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 6/6/19 11:44 AM
 *
 */
package com.anuraj.project.collabowf.fragment_operator;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import com.anuraj.project.collabowf.SwipeableLayout.EventListParentAdapterOperator;
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

import static com.anuraj.project.collabowf.utils.AppConstants.LOGIN_PREFERENCES;

public class GeneralAlertFragment extends Fragment {
    View rootView;

    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    AlertModel alertModel;
    RecyclerView event_recycler_view_parent;
    EventListParentAdapterOperator event_list_parent_adapter;
    EventInformation eventInformation = new EventInformation();
    ArrayList<EventDates> eventDatesArrayList;

    SharedPreferences pref;
    public GeneralAlertFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.generalalertfragment, container, false);
        //shared prefereance insatnce
        pref = getContext().getSharedPreferences(LOGIN_PREFERENCES, 0); // 0 - for private mode
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        setAlertsDataAdapterAndRecyclerView();
        return rootView;
    }

    private void setAlertsDataAdapterAndRecyclerView() {


        databaseReference = FirebaseDatabase.getInstance().getReference("alerts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                eventDatesArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    EventDates eventDates = new EventDates();
                    eventDates.setDate(dataSnapshot.getKey());
                    ArrayList<Events> eventsArrayList = new ArrayList<>();

                    for (DataSnapshot dataAlert : dataSnapshot.getChildren()) {
                        alertModel = dataAlert.getValue(AlertModel.class);
                        Events events = new Events();
                        if((alertModel.getStatus().equalsIgnoreCase("On Leave")) && alertModel.getSupervisorseen().equalsIgnoreCase("Accepted") && alertModel.getOperatorseen().equalsIgnoreCase("Send")) {
                            events.setEventId(alertModel.getId());
                            events.setEventDate(alertModel.getSelecteddate());
                            events.setEventAlertDate(eventDates.getDate());
                            events.setEventOPName(alertModel.getName());
                            events.setEventStatus(alertModel.getStatus());
                            events.setEventName(alertModel.getName() + " will be on Leave on " + alertModel.getSelecteddate());
                            events.setEventSupervisorStatus(alertModel.getSupervisorseen());
                        }
//                        else {
//                            events.setEventId(alertModel.getId());
//                            events.setEventDate(alertModel.getSelecteddate());
//                            events.setEventAlertDate(eventDates.getDate());
//                            events.setEventOPName(alertModel.getName());
//                            events.setEventStatus(alertModel.getStatus());
//                            events.setEventSupervisorStatus(alertModel.getSupervisorseen());
//                            events.setEventName(alertModel.getName() + " assigned to " + alertModel.getStatus() + " on " + alertModel.getSelecteddate());
//                        }

                        //to handle the null alert listing and filtering of only specific operator related alerts
                        if(events.getEventId()!=null) {
                            if (!events.getEventId().equalsIgnoreCase(pref.getString("employeeId", null))) {
                                eventsArrayList.add(events);
                            }
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
                event_list_parent_adapter = new EventListParentAdapterOperator(eventInformation,getActivity());
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