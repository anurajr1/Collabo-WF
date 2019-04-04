/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 25/3/19 2:21 PM
 *
 */

package com.anuraj.project.collabowf.operator_activity;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;

import static com.anuraj.project.collabowf.utils.AppConstants.RALEWAY_REGULAR;
import static com.anuraj.project.collabowf.utils.AppConstants.RALEWAY_SEMI_BOLD;

public class AddEventCalender extends AppCompatActivity {
    Typeface ralewayRegular,ralewaySemiBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent_operator_layout);
        //setting the close icon and title name
        setupToolBar();

        ralewayRegular = Typeface.createFromAsset(getApplicationContext().getAssets(),
                RALEWAY_REGULAR);
        ralewaySemiBold = Typeface.createFromAsset(getApplicationContext().getAssets(),
                RALEWAY_SEMI_BOLD);

        TextView dateTitle = findViewById(R.id.textView6);
        dateTitle.setTypeface(ralewayRegular);
        TextView dateTitleValue = findViewById(R.id.textView7);
        dateTitleValue.setTypeface(ralewaySemiBold);
    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) return;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    //closing the current screen by clicking the cross button
    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }
}
