package com.anuraj.project.collabowf.fragment;

/**
 * Created by Anuraj R(i321994) a4anurajr@gmail.com
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anuraj.project.collabowf.R;

public class TeamCalenderFragment extends Fragment {
    public TeamCalenderFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myteamcalenderlayout, container, false);
        return rootView;
    }
}
