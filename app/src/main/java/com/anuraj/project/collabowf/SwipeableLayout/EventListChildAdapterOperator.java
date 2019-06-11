/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 10/6/19 7:34 PM
 *
 */

package com.anuraj.project.collabowf.SwipeableLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.model.AlertModel;
import com.anuraj.project.collabowf.model.EventInformation;
import com.anuraj.project.collabowf.model.Events;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;
import static com.anuraj.project.collabowf.utils.AppConstants.LOGIN_PREFERENCES;


public class EventListChildAdapterOperator extends RecyclerView.Adapter<EventListChildAdapterOperator.MyViewHolder> {

    private EventInformation eventInformation;
    public ArrayList<Events> eventsArrayList;
    private Activity activity;
    SharedPreferences pref;
    private DatabaseReference mFirebaseDatabaseAlert;
    private FirebaseDatabase mFirebaseInstance;

    public EventListChildAdapterOperator(Activity activity, ArrayList<Events> eventsArrayList) {
        this.eventsArrayList = eventsArrayList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alert_row, parent, false);
        pref = activity.getSharedPreferences(LOGIN_PREFERENCES, 0); // 0 - for private mode
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'recordmodel' node
        mFirebaseDatabaseAlert = mFirebaseInstance.getReference("alerts");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        Events events = eventsArrayList.get(position);
        holder.event_list_event_name.setText(events.getEventName());
        if(events.getEventSupervisorStatus().equalsIgnoreCase("Accepted")){
            holder.statusView.setImageResource(R.drawable.approve);
        }else if(events.getEventSupervisorStatus().equalsIgnoreCase("Rejected")){
            holder.statusView.setImageResource(R.drawable.reject);
        }


        holder.event_list_event_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("event name=",eventsArrayList.get(position).getEventName());

                if((eventsArrayList.get(position).getEventId().equalsIgnoreCase(pref.getString("employeeId", null)))){

                    if(((eventsArrayList.get(position).getEventStatus().equalsIgnoreCase("On Leave")) && eventsArrayList.get(position).getEventSupervisorStatus().equalsIgnoreCase("Accepted"))){
                        new AlertDialog.Builder(activity)
                                .setTitle("Notify All")
                                .setMessage("Do you want to notify all the teammates?")
                                .setPositiveButton("Yes, Send", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("Clicked on Send button", "");
                                        //for alert model
                                        mFirebaseDatabaseAlert = mFirebaseInstance.getReference("alerts");
                                        // Read from the database
                                        mFirebaseDatabaseAlert.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                //update the record in alert table
                                                AlertModel alertmod = new AlertModel(eventsArrayList.get(position).getEventId(), eventsArrayList.get(position).getEventOPName(), eventsArrayList.get(position).getEventStatus(),"send","Accepted",eventsArrayList.get(position).getEventDate());

                                                mFirebaseDatabaseAlert.child((eventsArrayList.get(position).getEventAlertDate())).child(eventsArrayList.get(position).getEventPrimaryKey()).setValue(alertmod);
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError error) {
                                                // Failed to read value
                                                Log.w(TAG, "Failed to read value.", error.toException());
                                            }
                                        });

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d("Cancel the sending", "");
                                    }
                                })
                                .show();

                    }

                }

            }
        });









    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_list_event_name;
        public CardView card;
        public ImageView statusView;


        public MyViewHolder(View view) {
            super(view);
            event_list_event_name = (TextView) view.findViewById(R.id.name);
            card = (CardView) view.findViewById(R.id.cardChild);
            statusView = (ImageView) view.findViewById(R.id.imageView9);

        }
    }
}
