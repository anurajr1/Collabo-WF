/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 19/3/19 2:01 PM
 *
 */

package com.anuraj.project.collabowf.fragment_operator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.anuraj.project.collabowf.OperatorMainActivity;
import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.SplashScreen;
import com.anuraj.project.collabowf.operator_activity.AddEventCalender;
import com.anuraj.project.collabowf.operator_calender.AgendaView;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragmentOperator extends Fragment implements CalendarPickerController {


    View rootView;
    AgendaCalendarView mAgendaCalendarView;


    public HomeFragmentOperator(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.operator_calender_layout, container, false);

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -5);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 4);

        List<CalendarEvent> eventList = new ArrayList<>();
        mockList(eventList);
        mAgendaCalendarView = rootView.findViewById(R.id.agenda_calendar_view);

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);


        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                mAgendaCalendarView.mCalendarView.mSelectedDay.mDate

//                Intent i = new Intent(getActivity(), AddEventCalender.class);
//                startActivity(i);


                // Create custom dialog object
                final Dialog dialog = new Dialog(getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.addevent_operator_layout);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                // set values for custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.textView7);
                //text.setText("Custom dialog Android example.");


                dialog.show();

                Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        dialog.dismiss();
                    }
                });





            }
        });



        return rootView;
    }

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
        endTime1.add(Calendar.MONTH, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Allocated to Morning Shift", "Morning Shift", "Assembly Area 12",
                ContextCompat.getColor(getContext(), R.color.sapUiNegativeElement_mng), startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Allocated to Evening Shift", "Evening Shift", "Assembly Area 11",
                ContextCompat.getColor(getContext(), R.color.sapUiCriticalElement_afternoon), startTime2, endTime2, true);
        eventList.add(event2);


        Calendar startTime4 = Calendar.getInstance();
        startTime4.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime4 = Calendar.getInstance();
        endTime4.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event3 = new BaseCalendarEvent("Allocated to Night Shift", "Night Shift", "Assembly Area 10",
                ContextCompat.getColor(getContext(), R.color.sapUiPositiveElement_night), startTime2, endTime2, true);
        eventList.add(event3);


        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 0);





//        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
//                ContextCompat.getColor(this, R.color.blue_dark), startTime3, endTime3, false, android.R.drawable.ic_dialog_info);
//        eventList.add(event3);
    }

    @Override
    public void onDaySelected(DayItem dayItem) {

    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        // Log.d(LOG_TAG, String.format("Selected event: %s", event));
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
//        }
    }








}
