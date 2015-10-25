package com.ranisaurus.baselayer.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.koushikdutta.ion.Ion;
import com.ranisaurus.utilitylayer.logger.Log4a;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;

abstract public class BaseActivity extends AppCompatActivity {

    protected AtomicBoolean isFragmentLoaded = new AtomicBoolean(false);
    protected ActionBarDrawerToggle mToggle;
    protected DrawerLayout mDrawer;
    protected Toolbar mToolbar;

    private TabLayout tabLayoutView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initViews() {
        //Injection Views
        ButterKnife.bind(this);
    }

    public void initObjects() {

    }

    public void initListenerOrAdapter() {

    }


    public void initNetworkCalls() {
        //setup network layer
//        NetworkManager.setConfiguration(new NetworkConfig(this);
    }


    //setup will be called by oncreateView
    protected void setupActivity() {
        try {
            // 1. inject view with butterknife or manually
            initViews();

            if (isFragmentLoaded.get() == false) {
                //2. Load object once
                initObjects();
                //3. Network calls once
                initNetworkCalls();
            }

            //4. rebind the views with listeners or adapter again for renewal created views.
            initListenerOrAdapter();


            //mark current fragment as loaded just recreate the views only.
            isFragmentLoaded.set(true);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


    public void refreshNavigationToolbar() {

    }

    public TabLayout getTabLayoutView() {
        return tabLayoutView;
    }

    public void setTabLayoutView(TabLayout tabLayoutView) {
        this.tabLayoutView = tabLayoutView;
    }

    public void restoreToolBarColorWithStatusBar() {

    }

    public void changeToolBarColorWithStatusBar(int color) {

    }

    public void showBackButton() {

    }

    public void hideBackButton() {

    }

    public void showToolBar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
            mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
        }
    }

    public void hideToolBar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.GONE);
        }
    }

    public void setScreenTitle(int title) {

    }

    public void hideKeyboard() {
//        View view = getCurrentFocus();
//        if (view != null) {
//            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
//                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
    }

    public void replaceFragment(Fragment frag, int containerID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerID, frag).addToBackStack(null).commit();

    }

    public void replaceFragmentWithoutStack(Fragment frag, int containerID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerID, frag).commit();
    }

    public void addFragment(Fragment frag, int containerID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(containerID, frag).commit();
    }


    public void popAllFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        for (int i = 0; i < manager.getBackStackEntryCount(); ++i) {
            trans.remove(manager.getFragments().get(i));
        }
        trans.commit();
        manager.popBackStack();
    }

    public void popFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove(fragment);
        trans.commit();
        manager.popBackStack();
    }

    public void popFragmentTillLast() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
            fm.popBackStack();
        }
    }

    public Fragment getLastFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() >= 0) {
            return fm.getFragments().get(fm.getBackStackEntryCount());
        }
        return null;
    }

    public int getFragmentsCount() {
        FragmentManager fm = getSupportFragmentManager();
        return fm.getBackStackEntryCount();
    }

    //recording features

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    abstract public void startScreenRecording();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    abstract public void stopScreenRecording();

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Ion.getDefault(this).cancelAll(getApplicationContext());
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


}
