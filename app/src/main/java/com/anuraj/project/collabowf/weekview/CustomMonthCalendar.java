/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 7/8/16 12:51 PM
 *
 */

package com.anuraj.project.collabowf.weekview;


import android.app.Fragment;

import com.anuraj.project.collabowf.R;
import com.roomorama.caldroid.CaldroidFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomMonthCalendar extends CaldroidFragment {

    public CustomMonthCalendar() {
        // Required empty public constructor
    }

    protected int getGridViewRes() {
        return R.layout.custom_month_calendar;
    }
}
