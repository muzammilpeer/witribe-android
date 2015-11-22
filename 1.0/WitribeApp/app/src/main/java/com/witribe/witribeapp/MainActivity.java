package com.witribe.witribeapp;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import com.ranisaurus.baselayer.activity.BaseActivity;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.witribe.witribeapp.activity.LoginActivity;
import com.witribe.witribeapp.fragment.EducationListFragment;
import com.witribe.witribeapp.fragment.FavouriteListFragment;
import com.witribe.witribeapp.fragment.RecordVideoListFragment;
import com.witribe.witribeapp.fragment.VODListFragment;
import com.witribe.witribeapp.fragment.WebViewFragment;
import com.witribe.witribeapp.manager.UserManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnClickListener {


    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Bind(R.id.container_tabs)
    TabLayout mTabLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    TextView tvFullName;
    TextView tvCustomerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        tvFullName = (TextView) navigationView.findViewById(R.id.tv_full_name);
        tvCustomerID = (TextView) navigationView.findViewById(R.id.tv_email_address);


        setSupportActionBar(mToolbar);
        setTabLayoutView(mTabLayout);
        mainToolbar = mToolbar;


        //setup first screen
        if (savedInstanceState == null) {
            MainActivityFragment fragment = new MainActivityFragment();
            addFragment(fragment, R.id.container_main);
        }

        refreshNavigationViewData();

        if (getIntent() != null && getIntent().getExtras() != null) {
            String action = (String) getIntent().getExtras().get("intent_notification");
//            if (action.equals("start")) {
//                startService(new Intent(this, MyService.class));
//                MyNotification.getInstance(getApplicationContext()).setOnGoing(true);
//            } else if (action.equals("stop")) {
//                stopService(new Intent(this, MyService.class));
//                MyNotification.getInstance(getApplicationContext()).setOnGoing(false);
//            } else if (action.equals("app")) {
//                //Your code
//            }
        }
    }

    @Override
    public void setupNavigationDrawer() {
        super.setupNavigationDrawer();

        mToolbar.setNavigationOnClickListener(this);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void refreshNavigationViewData() {
        super.refreshNavigationViewData();

        if (UserManager.getInstance().isUserLoggedIn()) {
            //setup left navigation bar
            setupNavigationDrawer();
            if (tvFullName != null) {
                tvFullName.setText(UserManager.getInstance().getUserProfile().getFirstName() + " " + UserManager.getInstance().getUserProfile().getLastName());
                tvCustomerID.setText(UserManager.getInstance().getUserProfile().getCustomerID());
            }
        } else {
            if (tvFullName != null) {
                tvFullName.setText("");
                tvCustomerID.setText("");
            }
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Log4a.e("Fragment Count =", getFragmentsCount() + "");
        int id = item.getItemId();

        if (id == R.id.nav_live_channels) {
            popAllFragment();
//            replaceFragment(MainActivityFragment.newInstance(), R.id.container_main);
        } else if (id == R.id.nav_video_on_demand) {
            popAllFragment();
            replaceFragment(VODListFragment.newInstance(), R.id.container_main);
        } else if (id == R.id.nav_education) {
            popAllFragment();
            replaceFragment(EducationListFragment.newInstance(), R.id.container_main);
        } else if (id == R.id.nav_my_favourite) {
            popAllFragment();
            replaceFragment(FavouriteListFragment.newInstance(), R.id.container_main);
        } else if (id == R.id.nav_recorded_list) {
            popAllFragment();
            replaceFragment(RecordVideoListFragment.newInstance(), R.id.container_main);
//            replaceFragment(MyServiceFragment.newInstance(), R.id.container_main);
        } else if (id == R.id.nav_logout) {
            UserManager.getInstance().logoutUser();
            popAllFragment();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        Log4a.e("onDestroy","MainActivity");
        super.onDestroy();
    }


}
