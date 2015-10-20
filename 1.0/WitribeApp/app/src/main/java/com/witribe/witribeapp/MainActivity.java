package com.witribe.witribeapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ranisaurus.utilitylayer.logger.Log4a;
import com.witribe.witribeapp.fragment.LoginFragment;
import com.witribe.witribeapp.fragment.WebViewFragment;

public class MainActivity extends RecordingActivity implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(this);

        setSupportActionBar(mToolbar);

        setTabLayoutView((TabLayout) findViewById(R.id.container_tabs));


        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.setDrawerListener(mToggle);
        mDrawer.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mToggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //setup first screen
        if (savedInstanceState == null) {
            MainActivityFragment fragment = new MainActivityFragment();
            addFragment(fragment, R.id.container_main);
        }
    }

    public void refreshNavigationToolbar() {
        if (getFragmentsCount() == 1) {
            mToggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mDrawer.setDrawerListener(mToggle);
        } else {
            mToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToggle.syncState();
    }


    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentsCount() == 0) {
                exitAppDialog();
            } else if (getFragmentsCount() == 1) {
                mToggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                mDrawer.setDrawerListener(mToggle);
                mToggle.syncState();
                super.onBackPressed();
            } else {
                if (getLastFragment() instanceof WebViewFragment) {
                    Log4a.e("Last screen was watch live", "check it");
                    showToolBar();
                }

                mToggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mToggle.syncState();
                super.onBackPressed();
            }
        }


    }

    private void exitAppDialog() {
        // warning dialog that user is about to exit app
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.exit_title)
                .setMessage(R.string.exit_message)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Stop the activity
                                popAllFragment();
                                finish();
                            }

                        }).setNegativeButton(R.string.no, null).show();
    }


    @Override
    public void replaceFragment(Fragment frag, int containerID) {
        super.replaceFragment(frag, containerID);

        if (frag instanceof MainActivityFragment) {
            mToggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            mToolbar.setNavigationOnClickListener(this);
            mToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToggle.syncState();
    }

    @Override
    public void onClick(View v) {
        if (v.getContentDescription().toString().equalsIgnoreCase("Navigate up")) {
            onBackPressed();
        } else if (v.getContentDescription().toString().equalsIgnoreCase("Open navigation drawer")) {
            mDrawer.openDrawer(GravityCompat.START);
        }

        Log4a.e("click recieved", v.getContentDescription().toString());
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

        Log4a.e("Fragment Count =", getFragmentsCount() + "");
        int id = item.getItemId();

        if (id == R.id.nav_live_channels) {
            popAllFragment();
//            replaceFragment(new MainActivityFragment(), R.id.container_main);
        } else if (id == R.id.nav_video_on_demand) {

        } else if (id == R.id.nav_education) {

        } else if (id == R.id.nav_my_favourite) {

        } else if (id == R.id.nav_recent_view) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_login) {
            replaceFragment(new LoginFragment(), R.id.container_main);
        } else if (id == R.id.nav_logout) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
