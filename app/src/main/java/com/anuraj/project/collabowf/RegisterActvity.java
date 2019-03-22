package com.anuraj.project.collabowf;
/**
 * Created by Anuraj R(i321994) a4anurajr@gmail.com
 */
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anuraj.project.collabowf.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class RegisterActvity extends Activity {

    private EditText inputName, inputPassword, inputDomain, inputId;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlayout);


        inputId = (EditText) findViewById(R.id.input_employeeid);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputDomain = (EditText) findViewById(R.id.input_domain);
        inputName = (EditText) findViewById(R.id.input_name);
        btnSave = (Button) findViewById(R.id.btn_register);


        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String password = inputPassword.getText().toString();
                String domain = inputDomain.getText().toString();
                String id = inputId.getText().toString();



                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(id, name, password, domain);
                } else {
                    updateUser(id, name, password, domain);
                }
            }
        });
    }


    /**
     * Creating new user node under 'users'
     */
    private void createUser(String id, String name, String password, String domain) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        User user = new User(id,name,password,domain);

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }
    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }

                Log.e(TAG, "User data is changed!" + user.name + ", " + user.id);

                // Display newly updated name and email
            //    txtDetails.setText(user.name + ", " + user.email);

                // clear edit text
            //    inputEmail.setText("");
            //    inputName.setText("");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }
    private void updateUser(String id, String name, String password, String domain) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(id))
            mFirebaseDatabase.child(userId).child("id").setValue(id);

        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(password))
            mFirebaseDatabase.child(userId).child("password").setValue(password);

        if (!TextUtils.isEmpty(domain))
            mFirebaseDatabase.child(userId).child("domain").setValue(domain);
    }
}
