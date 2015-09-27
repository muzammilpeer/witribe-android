package com.ranisaurus.baselayer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.koushikdutta.ion.Ion;
import com.ranisaurus.utilitylayer.logger.Log4a;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    protected AtomicBoolean isFragmentLoaded = new AtomicBoolean(false);

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

    public void restoreToolBarColorWithStatusBar() {

    }

    public void changeToolBarColorWithStatusBar(int color) {

    }

    public void showBackButton() {

    }

    public void hideBackButton() {

    }

    public void setScreenTitle(int title) {

    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void replaceFragment(Fragment frag, int containerID) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerID, frag).addToBackStack(null)
                .commit();
    }

    public void replaceFragmentWithoutStack(Fragment frag, int containerID) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerID, frag)
                .commit();
    }

    public void addFragment(Fragment frag, int containerID) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerID,
                        frag).commit();
    }


    public void popAllFragment() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public Fragment getLastFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            return fm.getFragments().get(fm.getBackStackEntryCount());
        }
        return null;
    }

    public int getFragmentsCount() {
        FragmentManager fm = getSupportFragmentManager();
        return fm.getBackStackEntryCount();
    }

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
