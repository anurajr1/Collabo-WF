/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 26/04/19 9:18 PM
 *
 */

package com.anuraj.project.collabowf.bottom_sheet;

import android.app.Dialog;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anuraj.project.collabowf.MainActivity;
import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.model.AlertModel;
import com.anuraj.project.collabowf.model.OperatorList;
import com.anuraj.project.collabowf.model.RecordModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.anuraj.project.collabowf.utils.AppConstants.AFTERNOON_SHIFT;
import static com.anuraj.project.collabowf.utils.AppConstants.MORNING_SHIFT;
import static com.anuraj.project.collabowf.utils.AppConstants.NIGHT_SHIFT;
import static com.anuraj.project.collabowf.utils.AppConstants.ON_LEAVE;


public class BottomSheetFragment extends BottomSheetDialogFragment {
    String shift,SelectedDate,opName,opListTime,opID;
    ImageView tickMng,tickAfternoon,tickNight,tickLeave;
    private DatabaseReference mFirebaseDatabaseDate,mFirebaseDatabaseAlert;
    private FirebaseDatabase mFirebaseInstance;
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(view);
        mFirebaseInstance = FirebaseDatabase.getInstance();

        shift = getArguments().getString("shift");
        opName = getArguments().getString("opName");
        SelectedDate = getArguments().getString("selectedTime");
        opListTime = getArguments().getString("opNameListTime");
        opID = getArguments().getString("opID");

        //setting the operator name
        TextView opNameText = (TextView) view.findViewById(R.id.textView_zero);
        opNameText.setText(opName);
        //setting the selected date
        TextView opDateText = (TextView) view.findViewById(R.id.textView_zero_date);
        opDateText.setText(SelectedDate);


        tickMng = (ImageView) view.findViewById(R.id.imageView5);
        tickMng.setVisibility(View.GONE);
        tickAfternoon = (ImageView) view.findViewById(R.id.imageView6);
        tickAfternoon.setVisibility(View.GONE);
        tickNight = (ImageView) view.findViewById(R.id.imageView7);
        tickNight.setVisibility(View.GONE);
        tickLeave = (ImageView) view.findViewById(R.id.imageView8);
        tickLeave.setVisibility(View.GONE);

        if(shift.equalsIgnoreCase(MORNING_SHIFT)){
            tickMng.setVisibility(View.VISIBLE);
        }else if(shift.equalsIgnoreCase(AFTERNOON_SHIFT)){
            tickAfternoon.setVisibility(View.VISIBLE);
        }else if(shift.equalsIgnoreCase(NIGHT_SHIFT)){
            tickNight.setVisibility(View.VISIBLE);
        }else if(shift.equalsIgnoreCase(ON_LEAVE)){
            tickLeave.setVisibility(View.VISIBLE);
        }else{
            System.out.println("Do Nothing");
        }


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
//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }

        //close icon of bottom sheet
        ImageView imageViewClose = (ImageView) view.findViewById(R.id.imageView);
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to close the bottom sheet
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);

            }
        });

        //onclick on mng tick
        TextView tickMng = (TextView) view.findViewById(R.id.textView9);
        tickMng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                shiftSelected(MORNING_SHIFT);
                //to close the bottom sheet
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        //onclick on mng tick
        TextView tickAfternoon = (TextView) view.findViewById(R.id.textView10);
        tickAfternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftSelected(AFTERNOON_SHIFT);
                //to close the bottom sheet
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        //onclick on mng tick
        TextView tickNight = (TextView) view.findViewById(R.id.textView11);
        tickNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftSelected(NIGHT_SHIFT);
                //to close the bottom sheet
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        //onclick on mng tick
        TextView tickOnLeave = (TextView) view.findViewById(R.id.textView12);
        tickOnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shiftSelected(ON_LEAVE);
                //to close the bottom sheet
                ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });


    }

    public void shiftSelected(String shiftSelect) {
        //get current date
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("MMM d,yyyy");

        try {
            // Read from the database
            // get reference to 'recordmodel/date' node
            mFirebaseDatabaseDate = mFirebaseInstance.getReference("recordmodel");
            //for alert model
            mFirebaseDatabaseAlert = mFirebaseInstance.getReference("alerts");
            // Read from the database
            mFirebaseDatabaseDate.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(SelectedDate).child(opID).getValue() != null) {
                        //update in record table
                        mFirebaseDatabaseDate.child(SelectedDate).child(opID).child("status").setValue(shiftSelect);

                        //update the record in alert table
                        AlertModel alertmod = new AlertModel(opID, opName, shiftSelect,"false","false",format.format(date));
                        mFirebaseDatabaseAlert.child((SelectedDate)).child(opID).setValue(alertmod);
                        //Toast.makeText(getActivity(), "Operator "+ opName + " assigned to"+ shiftSelect + " on "+ SelectedDate, Toast.LENGTH_SHORT).show();
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
