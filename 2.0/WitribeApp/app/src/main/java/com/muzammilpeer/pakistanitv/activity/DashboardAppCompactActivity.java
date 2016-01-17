package com.muzammilpeer.pakistanitv.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.muzammilpeer.pakistanitv.R;
import com.muzammilpeer.pakistanitv.fragment.MainActivityFragment;
import com.muzammilpeer.pakistanitv.fragment.mobile.DashBoardViewPagerFragment;
import com.ranisaurus.baselayer.activity.BaseAppCompactActivity;
import com.ranisaurus.utilitylayer.logger.Log4a;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DashboardAppCompactActivity extends BaseAppCompactActivity implements Toolbar.OnClickListener {
    @Bind(R.id.tablayout_dashboard)
    TabLayout mTabLayout;
    @Bind(R.id.toolbar_dashboard)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        setTabLayoutView(mTabLayout);
        mainToolbar = mToolbar;


        //setup first screen
        if (savedInstanceState == null) {
            DashBoardViewPagerFragment fragment = new DashBoardViewPagerFragment();
            addFragment(fragment, R.id.framelayout_dashboard);
        }
//        Crashlytics.setUserIdentifier(UserManager.getInstance().getUserProfile().getCustomerID() + "");
//        Crashlytics.setUserEmail(UserManager.getInstance().getUserProfile().getToken() + "");
//        Crashlytics.setUserName(UserManager.getInstance().getUserProfile().getFirstName() + UserManager.getInstance().getUserProfile().getLastName() + "");
    }


    @Override
    public void onBackPressed() {
        if (getFragmentsCount() == 0) {
            exitAppDialog();
        }else {
            super.onBackPressed();
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
    public void replaceFragment(Fragment sourceFragment, Fragment destinationFragment, int containerID, int transitionElementID, int transitionID, String shared_element_key, String add_to_back_stack_key) {
        super.replaceFragment(sourceFragment, destinationFragment, containerID, transitionElementID, transitionID, shared_element_key, add_to_back_stack_key);
        if (destinationFragment instanceof MainActivityFragment) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            mToolbar.setNavigationOnClickListener(this);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onDestroy() {
        Log4a.e("onDestroy", "MainActivity");
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {

    }
}
