/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 11/5/19 4:22 PM
 *
 */

package com.anuraj.project.collabowf.bottom_sheet;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.model.AlertModel;
import com.anuraj.project.collabowf.model.RecordModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class BottomSheetMonthView extends BottomSheetDialogFragment {
    String SelectedDate,opName,opID;
    private DatabaseReference mFirebaseDatabaseDate,mFirebaseDatabaseAlert;
    private FirebaseDatabase mFirebaseInstance;
    String selectedDate;
    RecordModel records;
    public int MorningCounter, AfternoonCounter,NightCounter, LeaveCounter = 0;

    TextView mngTextCounter,afteTextCounter,nighTextCounter,onLeaveCounter;
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_month_bottom_sheet, null);
        dialog.setContentView(view);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        selectedDate = getArguments().getString("selectedDate");

        TextView date = view.findViewById(R.id.todayDate);
        date.setText(selectedDate);

        daySelected(selectedDate);

        mngTextCounter = view.findViewById(R.id.textView5);
        afteTextCounter = view.findViewById(R.id.textView6);
        nighTextCounter = view.findViewById(R.id.textView7);
        onLeaveCounter = view.findViewById(R.id.textView8);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        final CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }


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

                    if (dataSnapshot.child(selectedDate)!= null) {
                        for (DataSnapshot userSnapshot : dataSnapshot.child(selectedDate).getChildren()) {
                            records = userSnapshot.getValue(RecordModel.class);
                            if(records.getStatus().equalsIgnoreCase("Morning Shift")){
                                MorningCounter++;
                            }else if(records.getStatus().equalsIgnoreCase("Afternoon shift")){
                                AfternoonCounter++;
                            }else if(records.getStatus().equalsIgnoreCase("Night Shift")){
                                NightCounter++;
                            }else if(records.getStatus().equalsIgnoreCase("On Leave")){
                                LeaveCounter++;
                            }
                        }
                    }
                    mngTextCounter.setText(String.valueOf(MorningCounter));
                    afteTextCounter.setText(String.valueOf(AfternoonCounter));
                    nighTextCounter.setText(String.valueOf(NightCounter));
                    onLeaveCounter.setText(String.valueOf(LeaveCounter));
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
