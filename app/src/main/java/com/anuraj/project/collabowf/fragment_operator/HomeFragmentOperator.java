/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 19/3/19 2:01 PM
 *
 */

package com.anuraj.project.collabowf.fragment_operator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anuraj.project.collabowf.R;

public class HomeFragmentOperator extends Fragment {
    public HomeFragmentOperator(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_operatorlayout, container, false);
        return rootView;
    }
}
