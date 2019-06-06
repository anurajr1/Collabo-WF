package com.anuraj.project.collabowf.fragment;

/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 30/04/19 2:01 PM
 *
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anuraj.project.collabowf.LoginActivity;
import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.SplashScreen;
import com.anuraj.project.collabowf.image_util.ImageLoader;
import com.anuraj.project.collabowf.model.User;
import com.anuraj.project.collabowf.operator_activity.TeamMoreDetailsActivity;
import com.anuraj.project.collabowf.operator_adapter.RecyclerViewAdapter;
import com.anuraj.project.collabowf.operator_adapter.TeamDetails;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.anuraj.project.collabowf.utils.AppConstants.LOGIN_PREFERENCES;

public class ProfileFragment extends Fragment {
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;
    User users;
    TextView navUsername,navUserdomain,empID,empQuali,empCerti,empMob,empMail;
    ImageLoader imageLoader;
    ImageView proPic;

    List<TeamDetails> list = new ArrayList<>();
    public ProfileFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profilelayout, container, false);

        navUsername = (TextView) rootView.findViewById(R.id.textView_name);
        navUserdomain = (TextView) rootView.findViewById(R.id.textView_domain);
        empID = (TextView) rootView.findViewById(R.id.emp_ID);
        empMail = (TextView) rootView.findViewById(R.id.empMailID);
        empMob = (TextView) rootView.findViewById(R.id.empMobile);
        empQuali = (TextView) rootView.findViewById(R.id.empQuali);
        empCerti = (TextView) rootView.findViewById(R.id.empCerti);
        proPic = (ImageView) rootView.findViewById(R.id.profile_pic_imageview);



        SharedPreferences pref = getContext().getSharedPreferences(LOGIN_PREFERENCES, 0); // 0 - for private mode

        imageLoader = new ImageLoader(getContext());

        progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Loading Data");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    users = userSnapshot.getValue(User.class);
                    if(((pref.getString("employeeId",null)).equalsIgnoreCase(users.id))){

                        navUsername.setText(users.getName());
                        navUserdomain.setText(users.getDomain());
                        empID.setText(users.getId());
                        empMail.setText(users.getMailid());
                        empMob.setText(users.getMobile());
                        empQuali.setText(users.getQualification());
                        empCerti.setText(users.getCertificate());
                        //imageLoader.DisplayImage(users.getPropic(),proPic);
                        Glide.with(getContext()).load(users.getPropic()).into(proPic);

                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });
        return rootView;
    }
}
