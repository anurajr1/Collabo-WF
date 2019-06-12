/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 9/6/19 1:04 PM
 *
 */
package com.anuraj.project.collabowf.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.model.RecordModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ReportTodayFragment extends Fragment {
    View rootView;
    TextView todayMngTextview, todayAftTextView,todayNightTextView,todayLeaveTextView;
    ProgressDialog progressDialog;
    private DatabaseReference mFirebaseDatabaseDate;
    private FirebaseDatabase mFirebaseInstance;

    String selectedDate;
    RecordModel records;
    public int MorningCounter, AfternoonCounter,NightCounter, LeaveCounter = 0;

    public ReportTodayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_today_fragment, container, false);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        todayMngTextview = rootView.findViewById(R.id.mng_count);
        todayAftTextView = rootView.findViewById(R.id.aft_count);
        todayNightTextView = rootView.findViewById(R.id.night_count);
        todayLeaveTextView = rootView.findViewById(R.id.leave_count);

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading Data");

        progressDialog.show();
        DateFormat dateFormat = new SimpleDateFormat("MMM d,yyyy");
        Date date = new Date();
        daySelected(dateFormat.format(date));
        progressDialog.dismiss();
        return rootView;
    }

    public void daySelected(String selectedDate) {

        try {
            // Read from the database
            // get reference to 'recordmodel/date' node
            mFirebaseDatabaseDate = mFirebaseInstance.getReference("recordmodel");
            // Read from the database
            mFirebaseDatabaseDate.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(selectedDate) != null) {

                        Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();

                        for (String key : user.keySet()) {
                            if(selectedDate.equalsIgnoreCase(key)) {
                                for (DataSnapshot userSnapshot : dataSnapshot.child(key).getChildren()) {
                                    records = userSnapshot.getValue(RecordModel.class);

                                    if (records.getStatus().equalsIgnoreCase("Morning Shift")) {
                                        MorningCounter++;
                                    } else if (records.getStatus().equalsIgnoreCase("Afternoon shift")) {
                                        AfternoonCounter++;
                                    } else if (records.getStatus().equalsIgnoreCase("Night Shift")) {
                                        NightCounter++;
                                    } else if (records.getStatus().equalsIgnoreCase("On Leave")) {
                                        LeaveCounter++;
                                    }
                                    todayMngTextview.setText(String.valueOf(MorningCounter));
                                    todayAftTextView.setText(String.valueOf(AfternoonCounter));
                                    todayNightTextView.setText(String.valueOf(NightCounter));
                                    todayLeaveTextView.setText(String.valueOf(LeaveCounter));
                                }
                            }

                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }catch (Exception e){

        }
    }
}