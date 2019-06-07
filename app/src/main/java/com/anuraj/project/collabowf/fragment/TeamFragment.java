package com.anuraj.project.collabowf.fragment;

/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 30/04/19 12:01 PM
 *
 */
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.operator_adapter.RecyclerViewAdapter;
import com.anuraj.project.collabowf.operator_adapter.TeamDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.anuraj.project.collabowf.snackbar.GlobalSnackBar.setSnackBar;

public class TeamFragment extends Fragment {
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    List<TeamDetails> list = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;
    public TeamFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.teamfragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading Data");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    TeamDetails teamDetails = dataSnapshot.getValue(TeamDetails.class);
                    //filter to filter only operator list
                    if(teamDetails.getDomain().equalsIgnoreCase("Operator")){
                        list.add(teamDetails);
                    }
                }

                adapter = new RecyclerViewAdapter(getActivity(), list);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();

                setSnackBar(rootView,"Hi, Found " + list.size() + " Team Members");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

        return rootView;
    }

//    public static void setSnackBar(View root, String snackTitle) {
//        Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT);
//        snackbar.show();
//        View view = snackbar.getView();
//
//        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams)view.getLayoutParams();
//        params.gravity = Gravity.TOP;
//        params.topMargin = 140;
//        view.setLayoutParams(params);
//
//        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
//        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
//    }
}
