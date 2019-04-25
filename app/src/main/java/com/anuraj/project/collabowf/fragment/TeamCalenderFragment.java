package com.anuraj.project.collabowf.fragment;

/**
 * Created by Anuraj R(i321994) a4anurajr@gmail.com
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.anuraj.project.collabowf.LoginActivity;
import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.SplashScreen;
import com.anuraj.project.collabowf.model.RecordModel;
import com.anuraj.project.collabowf.model.User;
import com.anuraj.project.collabowf.weekview.WeekView;
import com.anuraj.project.collabowf.weekview.WeekViewEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;
import static com.anuraj.project.collabowf.utils.AppConstants.DAY_EVENT_SIZE;
import static com.anuraj.project.collabowf.utils.AppConstants.DAY_TEXT_SIZE;
import static com.anuraj.project.collabowf.utils.AppConstants.DAY_VIEW;
import static com.anuraj.project.collabowf.utils.AppConstants.DD_MMM_YYYY;
import static com.anuraj.project.collabowf.utils.AppConstants.END;
import static com.anuraj.project.collabowf.utils.AppConstants.EVENT_ADD_FAILURE_MESSAGE;
import static com.anuraj.project.collabowf.utils.AppConstants.LOGIN_PREFERENCES;
import static com.anuraj.project.collabowf.utils.AppConstants.RALEWAY_LIGHT;
import static com.anuraj.project.collabowf.utils.AppConstants.RALEWAY_REGULAR;
import static com.anuraj.project.collabowf.utils.AppConstants.RALEWAY_SEMI_BOLD;
import static com.anuraj.project.collabowf.utils.AppConstants.START;
import static com.anuraj.project.collabowf.utils.AppConstants.WEEK_EVENT_SIZE;
import static com.anuraj.project.collabowf.utils.AppConstants.WEEK_VIEW;

public class TeamCalenderFragment extends Fragment implements WeekView.MonthChangeListener,
        WeekView.EventClickListener, WeekView.EmptyViewClickListener,
        WeekView.ChangeBackgroundListener {

    // This is the counter for event count - can be removed after testing
    private static int count = 1;
    // To keep record of viewType being shown -
    private static int viewType;
    // This map is used to store the events
    HashMap<Integer, List<WeekViewEvent>> eventMap = new HashMap<>();
    // Typeface for text
    Typeface ralewayLight, ralewayRegular, ralewaySemiBold;
    // Day view & Week view object
    private WeekView mWeekView;
    // For button background toggle
    private Button buttonDayView, buttonWeekView, buttonMonthView;

    private SimpleDateFormat formatter;
    private Date[] startEndTime;

    String[] strOperator = {"Anuraj R","Ben johnson","Captain philip","Allen, Agnes","Blake, William","Crockett, Davy","Finney, Albert"
            ,"Golding, W","Han Shan","Jones, Norah","King, William","Kruk, John","Meir, Golda","Roth, Philip","West, Mae","Zola, Emile","Young, Neil"};


    WeekViewEvent event;
    List<WeekViewEvent> events;
    View rootView;

    private DatabaseReference mFirebaseDatabase,mFirebaseDatabaseRecords;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    RecordModel records;
    Date prevDate = null;

    public TeamCalenderFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.myteamcalenderlayout, container, false);


        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'recordmodel' node
        mFirebaseDatabase = mFirebaseInstance.getReference("recordmodel");

        // Initialize all the required components
        initComponents();

        parsedataRecords();



//        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, HH:mm:ss yyyy");
//        String nameMap = "00:45:00";
//        String dateInString = "Apr 25,"+" "+ nameMap + " 2019";
//        Date date =null;
//        try {
//            date = formatter.parse(dateInString);
//        }
//        catch (Exception   e){
//
//        }
//        //setting the time and add 15mins
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.MINUTE, 15);
//        //adding the event
//        addEvent(date,cal.getTime(),"Morning Shift");


        buttonDayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewType != DAY_VIEW) {
                    //getActivity().getSupportFragmentManager().beginTransaction().remove(customMonthCalendar).commitAllowingStateLoss();
                    mWeekView.setVisibility(View.VISIBLE);

                    // Lets change some dimensions to best fit the view.
//                    mWeekView.setTextSize((int) TypedValue.applyDimension
//                            (TypedValue.COMPLEX_UNIT_SP, DAY_TEXT_SIZE, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension
                            (TypedValue.COMPLEX_UNIT_SP, DAY_EVENT_SIZE, getResources().getDisplayMetrics()));

                    // Set the number of visible days to one
                    mWeekView.setNumberOfVisibleDays(DAY_VIEW);
                    viewType = DAY_VIEW;

                    changeButtonBackground(buttonDayView);
                    mWeekView.setFromMonthView(false);
                }
            }
        });

        buttonWeekView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewType != WEEK_VIEW) {
                    //getActivity().getSupportFragmentManager().beginTransaction().remove(customMonthCalendar).commitAllowingStateLoss();
                    mWeekView.setVisibility(View.VISIBLE);
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension
                            (TypedValue.COMPLEX_UNIT_SP, WEEK_EVENT_SIZE, getResources().getDisplayMetrics()));

                    // Set the number of visible days to seven
                    mWeekView.setNumberOfVisibleDays(WEEK_VIEW);
                    viewType = WEEK_VIEW;
                    changeButtonBackground(buttonWeekView);

//                    if (calendarPreference.contains(DATE_KEY_WEEK)) {
//                        long dateInMillis = calendarPreference.getLong(DATE_KEY_WEEK, 0);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTimeInMillis(dateInMillis);
//                        mWeekView.goToDate(calendar);
//                        mWeekView.notifyDatasetChanged();
//                    }
                    mWeekView.setFromMonthView(false);
                }
            }
        });

     //   createUser("02002","Anuraj","Morning shift");






        return rootView;
    }


    private void parsedataRecords(){

        // get reference to 'recordmodel' node
        mFirebaseDatabaseRecords = mFirebaseInstance.getReference("recordmodel");
        // Read from the database
        mFirebaseDatabaseRecords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
                for (String key : user.keySet()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.child(key).getChildren()) {
                        records = userSnapshot.getValue(RecordModel.class);

                        String[] splitValue = key.split(",");
                        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, HH:mm:ss yyyy");
                        String nameMap = "00:00:00";
                        String dateInString = splitValue[0]+","+" "+ nameMap + " "+splitValue[1];

                        Date date = prevDate;

                        try {
                            if(prevDate==null) {
                                date = formatter.parse(dateInString);
                            }else{
                                date = prevDate;
                            }
                        }
                        catch (Exception   e){

                        }
                        //setting the time and add 15mins
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        cal.add(Calendar.MINUTE, 15);

                        prevDate = cal.getTime();

                        //adding the event
                        addEvent(date,cal.getTime(),records.getStatus());

                    }
                    System.out.println("next day records fetch starts");
                    prevDate = null;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



//        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, HH:mm:ss yyyy");
//        String nameMap = "00:45:00";
//        String dateInString = "Apr 25,"+" "+ nameMap + " 2019";
//        Date date =null;
//        try {
//            date = formatter.parse(dateInString);
//        }
//        catch (Exception   e){
//
//        }
//        //setting the time and add 15mins
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.MINUTE, 15);
//        //adding the event
//        addEvent(date,cal.getTime(),"Morning Shift");









    }









    // Initialize all the required components
    private void initComponents() {
        // Set the view type to Day view
        viewType = DAY_VIEW;
        // Initialise the typefaces
        ralewayLight = Typeface.createFromAsset(getContext().getAssets(),
                RALEWAY_LIGHT);
        ralewayRegular = Typeface.createFromAsset(getContext().getAssets(),
                RALEWAY_REGULAR);
        ralewaySemiBold = Typeface.createFromAsset(getContext().getAssets(),
                RALEWAY_SEMI_BOLD);

        // To record the button pressed
        buttonDayView = (Button) rootView.findViewById(R.id.action_day_view);
        buttonWeekView = (Button) rootView.findViewById(R.id.action_week_view);
        buttonMonthView = (Button) rootView.findViewById(R.id.action_month_view);
        buttonDayView.setTypeface(ralewayRegular);
        buttonWeekView.setTypeface(ralewayRegular);
        buttonMonthView.setTypeface(ralewayRegular);

        formatter = new SimpleDateFormat(DD_MMM_YYYY, Locale.getDefault());

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) rootView.findViewById(R.id.weekView);
        mWeekView.goToToday();
        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);
        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);
        // to toggle button
        mWeekView.setmBackgroundListener(this);
        // Setup start and end time of the calendar view
//        mWeekView.setmEndMinute("24:00:00");
//        mWeekView.setmStartMinute("01:00:00");
        mWeekView.setOperatorNames(strOperator);
        //mWeekView.setOperatorLength(3);
        mWeekView.setEmptyViewClickListener(this);
    }


    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        // Populate the week view with some events.
        List<WeekViewEvent> events;
        events = extractEvent(newMonth);
        return events;
    }


    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(getContext(), "Pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    // It will implement the swipe on the events to cancel it
    public void onEventSwipe(WeekViewEvent event, RectF eventRect) {
        if (cancelEvent(event)) {
            Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onEmptyViewClicked(Calendar time) {
        startEndTime = convertTime(time);
        {
            // This tries to add event
            if (addEvent(startEndTime[0], startEndTime[1], "On Leave")) {
                Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                count++;
            } else {
                Toast.makeText(getContext(), EVENT_ADD_FAILURE_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    // It will implement the swipe on the empty view to add blank
    public void onEmptyViewSwiped(Calendar time) {
        startEndTime = convertTime(time);
        if (addEvent(startEndTime[0], startEndTime[1], "")) {
            Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
        }
    }

    // Extracts the event list of the specified month
    private List<WeekViewEvent> extractEvent(int month) {
        List<WeekViewEvent> events = eventMap.get(month);
        if (events == null) {
            events = new ArrayList<>();
        }
        return events;
    }


    // Add event to the calendar
    private boolean addEvent(Date startTime, Date endTime, String eventTitle) {
        Calendar currentDate = Calendar.getInstance();
        Date today = currentDate.getTime();
        Calendar startEventTime = Calendar.getInstance();
        startEventTime.setTime(startTime);
        int month = startTime.getMonth();
        Calendar endEventTime = Calendar.getInstance();
        endEventTime.setTime(endTime);
        events = extractEvent(month + 1);
        event = new WeekViewEvent(count, eventTitle, startEventTime, endEventTime);
//        if (startTime.getTime() < today.getTime()) {
//            event.setColor(getResources().getColor(R.color.event_color_past));
//        } else {

       // }

        if(eventTitle.equalsIgnoreCase("Morning Shift")){
            event.setColor(getResources().getColor(R.color.sapUiNegativeElement_mng));
        }else if(eventTitle.equalsIgnoreCase("Afternoon Shift")){
            event.setColor(getResources().getColor(R.color.sapUiCriticalElement_afternoon));
        }else if(eventTitle.equalsIgnoreCase("Night Shift")){
            event.setColor(getResources().getColor(R.color.sapUiPositiveElement_night));
        }else if(eventTitle.equalsIgnoreCase("On Leave")){
            event.setColor(getResources().getColor(R.color.sapUiNeutralElement_grey));
        }else if(eventTitle.equalsIgnoreCase("Holiday")){
            event.setColor(getResources().getColor(R.color.sapUiNegativeElement_red));
        }

        events.add(event);
        eventMap.put(month + 1, events);
        mWeekView.notifyDatasetChanged();
        return true;
    }

    @Override
    public void changeBackground() {
        viewType = DAY_VIEW;

    }

    // This will remove the event from calendar
    private boolean cancelEvent(WeekViewEvent event) {
        final int month = event.getStartTime().getTime().getMonth();
        List<WeekViewEvent> eventList = extractEvent(month + 1);
        for (WeekViewEvent viewEvent : eventList) {
            if (event.getStartTime().getTime().getTime() == viewEvent.getStartTime().getTime().getTime()) {
                eventList.remove(viewEvent);
                eventMap.put(month + 1, eventList);
                mWeekView.notifyDatasetChanged();
                count--;
                return true;
            }
        }
        return false;
    }

    /**
     * Convert the given Calendar touch time and compute its slot
     * @param time time at which it it touched
     * @return Date[] of start and end time of the slot
     */
    private Date[] convertTime(Calendar time) {
        int startMinute = mWeekView.getmStartMinute();
        Date startTime = new Date();
        Date endTime = new Date();

        Date date = time.getTime();
        int hour = date.getHours();
        int minute = date.getMinutes();

        int minutes = hour * 60 + minute;
        minutes += startMinute;
        minute = minutes % 60;
        int buffer = minute % 15;

        long timeInMillis = date.getTime();
        timeInMillis /= 1000;
        timeInMillis = (timeInMillis / 60) - buffer + startMinute;
        date.setTime(timeInMillis * 60 * 1000);

        startTime.setTime(timeInMillis * 60 * 1000);
        timeInMillis = timeInMillis + 15;
        date.setTime(timeInMillis * 60 * 1000);

        endTime.setTime(timeInMillis * 60 * 1000);
        Date[] startNend = new Date[2];
        startNend[START] = startTime;
        startNend[END] = endTime;
        return startNend;
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        return super.onOptionsItemSelected(item);
//    }



    // Change the background color of the selected button
    public void changeButtonBackground(Button button) {
        buttonDayView.setBackgroundColor(getResources().getColor(R.color.sapUiPageFooterBackground));
        buttonWeekView.setBackgroundColor(getResources().getColor(R.color.sapUiPageFooterBackground));
        buttonMonthView.setBackgroundColor(getResources().getColor(R.color.sapUiPageFooterBackground));
        button.setBackgroundColor(getResources().getColor(R.color.sapUiBaseColor));
    }




    /**
     * Creating new user node under 'recordmodel'
     */
    private void createUser(String id, String name, String status) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        RecordModel recordmod = new RecordModel(id,name,status);

        //mFirebaseDatabase.child("Apr 26,2019").setValue(recordmod);
        mFirebaseDatabase.child(("Apr 25,2019")).child("O2007").setValue(recordmod);

       // addUserChangeListener();
    }

}
