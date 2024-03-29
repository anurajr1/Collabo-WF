package com.anuraj.project.collabowf;
/**
 * Created by Anuraj R(i321994) a4anurajr@gmail.com
 */
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.anuraj.project.collabowf.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static com.anuraj.project.collabowf.utils.AppConstants.LOGIN_PREFERENCES;

public class LoginActivity extends Activity {
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    User users;
    private EditText inputId, inputPassword;
    private Button btnLogin,btnQr;
    SharedPreferences pref;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        pref = getApplicationContext().getSharedPreferences(LOGIN_PREFERENCES, 0); // 0 - for private mode

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        inputId = (EditText) findViewById(R.id.input_employeeid);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnQr = (Button) findViewById(R.id.button_qrcode);


        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();




        //load qr layout on onclick
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(LoginActivity.this, QrCodeActivity.class);
//                startActivity(i);
                IntentIntegrator integrator = new IntentIntegrator(LoginActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan the QR Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String password = inputPassword.getText().toString();
                final String id = inputId.getText().toString();
                if(!(password.isEmpty()) && !(id.isEmpty())){

                    // Read from the database
                    mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                                users = userSnapshot.getValue(User.class);
                                if((id.equalsIgnoreCase(users.id)) && (password.equalsIgnoreCase(users.password))){
                                    //storing the data to shared preference
                                    pref = getApplicationContext().getSharedPreferences(LOGIN_PREFERENCES, 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("employeeId", users.id);
                                    editor.putString("supId", users.supid);
                                    editor.putString("employeePassword", users.password);
                                    editor.putString("employeeDomain", users.domain);
                                    editor.putString("employeeName", users.name);
                                    editor.commit(); // commit changes

                                    Intent i = new Intent(LoginActivity.this, SplashScreen.class);
                                    i.putExtra("employeeDomain",users.domain);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });

                }
                else{
                    Toast.makeText(getBaseContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }











    @Override
    protected void onStart() {
        super.onStart();

        if ((pref.getString("employeeId", null))!= null) {
            Intent intent = new Intent(this, SplashScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("employeeDomain",(pref.getString("employeeDomain", null)));
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.e("Scan", "Cancelled scan");
            } else {
                Log.e("Scan", "Scanned");
                // Read from the database
                mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String qrCodeValue = result.getContents();
                        String emplyID = null;
                        String pass = null;
                        try {
                            JSONObject reader = new JSONObject(qrCodeValue);
                            //JSONObject sys  = reader.getJSONObject("user");
                            emplyID = reader.getString("employeeId");
                            pass = reader.getString("password");
                        }
                        catch (Exception e){

                        }

                        Boolean ErrorQR = true;
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                            users = userSnapshot.getValue(User.class);
                            if((emplyID.equalsIgnoreCase(users.id)) && (pass.equalsIgnoreCase(users.password))){
                                //storing the data to shared preference
                                    pref = getApplicationContext().getSharedPreferences(LOGIN_PREFERENCES, 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("employeeId", users.id);
                                    editor.putString("supId", users.supid);
                                    editor.putString("employeePassword", users.password);
                                    editor.putString("employeeDomain", users.domain);
                                    editor.putString("employeeName", users.name);
                                    editor.commit(); // commit changes

                                    //setting the errorQR to true
                                    ErrorQR = false;
                                    Intent i = new Intent(LoginActivity.this, SplashScreen.class);
                                    i.putExtra("employeeDomain",users.domain);
                                    startActivity(i);
                                    finish();
                            }
                        }
                        if(ErrorQR){
                            Toast.makeText(getApplicationContext(), "Wrong Credentials. Please re-check the QR", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
