package com.anuraj.project.collabowf;
/**
 * Created by Anuraj R(i321994) a4anurajr@gmail.com
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {
	// Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;
    //ImageView imageAnimation;
   // Animation anim;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        //imageAnimation = (ImageView) findViewById(R.id.imageView1);
        //anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_bounce);
        //imageAnimation.setVisibility(View.VISIBLE);
        //imageAnimation.startAnimation(anim);
        new Handler().postDelayed(new Runnable() {
 
           
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start  app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
