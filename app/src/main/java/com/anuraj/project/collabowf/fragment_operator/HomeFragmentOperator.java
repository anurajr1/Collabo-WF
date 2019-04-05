/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 19/3/19 2:01 PM
 *
 */

package com.anuraj.project.collabowf.fragment_operator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.anuraj.project.collabowf.R;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.calendar.CalendarView;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        CalendarView cal = (CalendarView) rootView.findViewById(R.id.calendar_view);
        cal.findViewById(R.id.month_label);


        View view;
        LayoutInflater inflater1 = (LayoutInflater)   getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater1.inflate(R.layout.view_agenda_event, null);








        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CalendarView cal = (CalendarView) rootView.findViewById(R.id.calendar_view);
                Date selectedDate = cal.getSelectedDay().getDate();

                //generating the suitable date format to display
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
                String date =null;
                try {
                    date = formatter.format(selectedDate);
                }
                catch (Exception   e){

                }

                // Create custom dialog object
                final Dialog dialog = new Dialog(getContext());
                // Include dialog.xml file
                dialog.setContentView(R.layout.addevent_operator_layout);

                // set values for custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.textView7);
                text.setText(date);

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


        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String dateInString = "08-05-2019";
        Calendar calendar = null;
        try {
            Date date = sdf.parse(dateInString);

            calendar = Calendar.getInstance();
            calendar.setTime(date);

        }
        catch (Exception e){

        }

        BaseCalendarEvent event2 = new BaseCalendarEvent("Allocated to Evening Shift", "Evening Shift", "Assembly Area 11",
                ContextCompat.getColor(getContext(), R.color.sapUiCriticalElement_afternoon), calendar, calendar, true);
        eventList.add(event2);

        BaseCalendarEvent event3 = new BaseCalendarEvent("On Leave", "Leave", "unavailable",
                ContextCompat.getColor(getContext(), R.color.sapUiNeutralElement_grey), calendar, calendar, true);
        eventList.add(event3);


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
