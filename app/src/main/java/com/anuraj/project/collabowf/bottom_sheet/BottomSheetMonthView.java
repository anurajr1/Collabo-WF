/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 11/5/19 4:22 PM
 *
 */

package com.anuraj.project.collabowf.bottom_sheet;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.model.RecordModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class BottomSheetMonthView extends BottomSheetDialogFragment{
    String SelectedDate,opName,opID;
    private DatabaseReference mFirebaseDatabaseDate,mFirebaseDatabaseAlert;
    private FirebaseDatabase mFirebaseInstance;
    String selectedDate;
    RecordModel records;
    public int MorningCounter, AfternoonCounter,NightCounter, LeaveCounter = 0;
    //AnyChartView anyChartView;
    TextView mngTextCounter,afteTextCounter,nighTextCounter,onLeaveCounter;

    private PieChart chart;

    protected final String[] parties = new String[] {
            "Morning", "Afternoon", "Night", "Leave"
    };


    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_month_bottom_sheet, null);
        dialog.setContentView(view);
        mFirebaseInstance = FirebaseDatabase.getInstance();



        //settings for the half pie chart
        chart = view.findViewById(R.id.chart1);
        chart.setBackgroundColor(Color.WHITE);

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);

        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);

        chart.setMaxAngle(180f); // HALF CHART
        chart.setRotationAngle(180f);
        chart.setCenterTextOffset(0, -20);

        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
     //   chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(12f);

        selectedDate = getArguments().getString("selectedDate");

        TextView date = view.findViewById(R.id.todayDate);
        date.setText(selectedDate);

        daySelected(selectedDate);
        //anyChartView = (AnyChartView) view.findViewById(R.id.any_chart_view);
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

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Today's Availability Percentage");
        return s;
    }

    private void setData(ArrayList<PieEntry> value) {

        PieDataSet dataSet = new PieDataSet(value, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(Color.parseColor("#FF8888"),Color.parseColor("#FABD64"),Color.parseColor("#ABE2AB"),Color.parseColor("#5E696E"));
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
     //   data.setValueTypeface(tfLight);
        chart.setData(data);

        chart.invalidate();
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

                    ArrayList<PieEntry> values = new ArrayList<>();

                    values.add(new PieEntry(MorningCounter, "Morning"));
                    values.add(new PieEntry(AfternoonCounter, "Afternoon"));
                    values.add(new PieEntry(NightCounter, "Night"));
                    values.add(new PieEntry(LeaveCounter, "Leave"));

                    setData(values);

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
