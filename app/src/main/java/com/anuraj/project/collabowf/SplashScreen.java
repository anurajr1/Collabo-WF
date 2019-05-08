package com.anuraj.project.collabowf;
/**
 * Created by Anuraj R(i321994) a4anurajr@gmail.com
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    private String employeeDomain;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        Intent intent = getIntent();
        employeeDomain = intent.getExtras().getString("employeeDomain");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if(employeeDomain.equalsIgnoreCase("Supervisor")) {
                    // Start Supervisor main activity
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                }else{
                    // Start operator main activity
                    Intent i = new Intent(SplashScreen.this, OperatorMainActivity.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
