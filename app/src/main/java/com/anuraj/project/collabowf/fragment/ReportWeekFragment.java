/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 9/6/19 11:39 AM
 *
 */

package com.anuraj.project.collabowf.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anuraj.project.collabowf.R;

public class ReportWeekFragment extends Fragment {
    View rootView;
    public ReportWeekFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.report_week_fragment, container, false);

        return rootView;
    }
}