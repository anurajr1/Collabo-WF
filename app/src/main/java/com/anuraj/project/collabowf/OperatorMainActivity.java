/*
 * *
 *  * Created by Anuraj R (a4anurajr@gmail.com) on 2019
 *  * Last modified 25/3/19 2:19 PM
 *
 */

package com.anuraj.project.collabowf;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anuraj.project.collabowf.fragment.ProfileFragment;
import com.anuraj.project.collabowf.fragment.TeamCalenderFragment;
import com.anuraj.project.collabowf.fragment.TeamFragment;

import static com.anuraj.project.collabowf.MainActivity.setSnackBar;
import static com.anuraj.project.collabowf.utils.AppConstants.LOGIN_PREFERENCES;

public class OperatorMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String employeeDomain = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operator_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(LOGIN_PREFERENCES, 0); // 0 - for private mode

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_operator);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView);
        navUsername.setText(pref.getString("employeeName", null));

        //setting the display and naviagtion drawer selection to my team calender view
        displayView(R.id.nav_team_calender);
        navigationView.setCheckedItem(R.id.nav_team_calender);


        //getting the layout for snackbar
        RelativeLayout relativeLayout = findViewById(R.id.rel_main);

        employeeDomain = pref.getString("employeeDomain", null);
        //showing welcome snackbar
        setSnackBar(relativeLayout, "Welcome "+ employeeDomain+ " : " +pref.getString("employeeName", null));

    }

    //for navigating to fragements
    public void displayView(int viewId) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_team_calender:
                fragment = new TeamCalenderFragment();
                title =getString(R.string.nav_team_calender);
                break;

            case R.id.nav_share:
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBody = "Download From Playstore :-) \n"+"https://goo.gl/YFnyUD";
//                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Spread word");
//                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;

        }

        if (fragment != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_operator);
        drawer.closeDrawer(GravityCompat.START);
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_operator);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,getString(R.string.exit_msg), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else {
            super.onBackPressed();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());
        return true;
    }
}
