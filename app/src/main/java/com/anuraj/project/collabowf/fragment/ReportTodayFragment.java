/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 9/6/19 1:04 PM
 *
 */
package com.anuraj.project.collabowf.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.model.RecordModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private PieChart chart;

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
        TextView today_date_text = rootView.findViewById(R.id.material_date_text);

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading Data");

        progressDialog.show();

        chart = rootView.findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        //chart.setOnChartValueSelectedListener(ReportTodayFragment.this);


        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
        //chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(12f);


        DateFormat dateFormat = new SimpleDateFormat("MMM d,yyyy");
        Date date = new Date();
        daySelected(dateFormat.format(date));

        today_date_text.setText(dateFormat.format(date));
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
                        MorningCounter = 0;
                        AfternoonCounter = 0;
                        NightCounter = 0;

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

                                    ArrayList<PieEntry> values = new ArrayList<>();

                                    values.add(new PieEntry(MorningCounter, "Morning"));
                                    values.add(new PieEntry(AfternoonCounter, "Afternoon"));
                                    values.add(new PieEntry(NightCounter, "Night"));
                                    values.add(new PieEntry(LeaveCounter, "Leave"));

                                    setData(values);
                                   // setData(3,4);
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

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Today's Report\nPercentage of Availability");
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
}