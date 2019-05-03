/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 2/5/19 11:25 AM
 *
 */

package com.anuraj.project.collabowf.fragment;

import android.app.ProgressDialog;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.SwipeableLayout.Alert;
import com.anuraj.project.collabowf.SwipeableLayout.AlertDataAdapter;
import com.anuraj.project.collabowf.SwipeableLayout.SwipeController;
import com.anuraj.project.collabowf.SwipeableLayout.SwipeControllerActions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlertFragment extends Fragment {
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;
    private AlertDataAdapter mAdapter;
    SwipeController swipeController = null;
    View rootView;

    public AlertFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.alertfragment, container, false);

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading Data");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

        setPlayersDataAdapter();
        setupRecyclerView();

        return rootView;
    }
    private void setPlayersDataAdapter() {
        List<Alert> players = new ArrayList<>();

        Alert player = new Alert();
        player.setName("Anu");
        player.setNationality("Indian");
        player.setClub("Mexico");
        player.setRating(90);
        player.setAge(25);
        players.add(player);

        mAdapter = new AlertDataAdapter(players);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                mAdapter.alerts.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}
