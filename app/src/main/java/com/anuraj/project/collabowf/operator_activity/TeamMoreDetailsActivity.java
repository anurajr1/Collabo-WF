/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 4/4/19 4:13 PM
 *
 */

package com.anuraj.project.collabowf.operator_activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.operator_adapter.RecyclerViewAdapter;
import com.anuraj.project.collabowf.operator_adapter.TeamDetails;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeamMoreDetailsActivity extends AppCompatActivity {

    TextView operatorName;
    String employeeID;
    DatabaseReference databaseReference;


    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teammoredetail_layout);
        //setting the close icon and title name
        //setupToolBar();
        Intent intent = getIntent();
        employeeID = intent.getExtras().getString("OperatorId");


        progressDialog = new ProgressDialog(getApplicationContext());

        progressDialog.setMessage("Loading Data");

    //    progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                    TeamDetails teamDetails = dataSnapshot.getValue(TeamDetails.class);
//
//                    list.add(teamDetails);
//                }
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//                progressDialog.dismiss();
//
//            }
//        });


        databaseReference.orderByChild("id").equalTo(employeeID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //filtered entry added to the class
                TeamDetails teamDetails = dataSnapshot.getValue(TeamDetails.class);

                operatorName = findViewById(R.id.textView3);
                operatorName.setText(teamDetails.getName());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar == null) return;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("More detail");
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
