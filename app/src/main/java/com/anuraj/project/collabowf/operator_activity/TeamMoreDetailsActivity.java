/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 4/4/19 4:13 PM
 *
 */

package com.anuraj.project.collabowf.operator_activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anuraj.project.collabowf.R;
import com.anuraj.project.collabowf.image_util.ImageLoader;
import com.anuraj.project.collabowf.model.User;
import com.anuraj.project.collabowf.operator_adapter.RecyclerViewAdapter;
import com.anuraj.project.collabowf.operator_adapter.TeamDetails;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeamMoreDetailsActivity extends AppCompatActivity {

    TextView operatorName, operatorDomain,empQuali,empID,empCerti;
    ImageView proPic, closeImage,emailImage,MobileImage;
    String employeeID;
    DatabaseReference databaseReference;
    //ImageLoader imageLoader;
    User user;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teammoredetail_layout);
        //setting the close icon and title name
        //setupToolBar();
        Intent intent = getIntent();
        employeeID = intent.getExtras().getString("OperatorId");


        closeImage = findViewById(R.id.header_title_pro);

        closeImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish(); // close this activity as oppose to navigating back
            }
        });

        emailImage = findViewById(R.id.imageView2);

        emailImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { user.getMailid() });
                startActivity(Intent.createChooser(intent, ""));
            }
        });


        MobileImage = findViewById(R.id.imageView3);

        MobileImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+user.getMobile()));
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(TeamMoreDetailsActivity.this);

        //imageLoader = new ImageLoader(TeamMoreDetailsActivity.this);

        progressDialog.setMessage("Loading Data");

        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");


        databaseReference.orderByChild("id").equalTo(employeeID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                //filtered entry added to the class
                user = dataSnapshot.getValue(User.class);

                operatorName = findViewById(R.id.textView3);
                operatorDomain = findViewById(R.id.textView5);
                proPic = findViewById(R.id.profile_pic_imageview);
                empQuali = findViewById(R.id.empQuali);
                empID = findViewById(R.id.emp_ID);
                empCerti = findViewById(R.id.empCerti);

                //imageLoader.DisplayImage(user.getPropic(),proPic);
                Glide.with(TeamMoreDetailsActivity.this).load(user.getPropic()).into(proPic);
                operatorName.setText(user.getName());
                operatorDomain.setText(user.getDomain());
                empQuali.setText(user.getQualification());
                empID.setText(user.getId());
                empCerti.setText(user.getCertificate());


                //closing the progress bar
                progressDialog.dismiss();
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
                progressDialog.dismiss();
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
