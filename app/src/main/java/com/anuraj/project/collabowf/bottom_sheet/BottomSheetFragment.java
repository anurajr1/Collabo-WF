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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anuraj.project.collabowf.MainActivity;
import com.anuraj.project.collabowf.R;

import static com.anuraj.project.collabowf.utils.AppConstants.AFTERNOON_SHIFT;
import static com.anuraj.project.collabowf.utils.AppConstants.MORNING_SHIFT;
import static com.anuraj.project.collabowf.utils.AppConstants.NIGHT_SHIFT;
import static com.anuraj.project.collabowf.utils.AppConstants.ON_LEAVE;


public class BottomSheetFragment extends BottomSheetDialogFragment {
    MainActivity main;
    String shift;
    ImageView tickMng,tickAfternoon,tickNight,tickLeave;
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(view);
        main = new MainActivity();

        shift = getArguments().getString("shift");

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

    public void shiftSelected(String shiftSelect){

    }

}
