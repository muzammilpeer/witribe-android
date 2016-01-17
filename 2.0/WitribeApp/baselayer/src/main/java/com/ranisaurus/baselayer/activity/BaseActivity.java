package com.ranisaurus.baselayer.activity;

import android.app.Activity;
import android.os.Bundle;

import com.ranisaurus.utilitylayer.logger.Log4a;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.ButterKnife;

/**
 * Created by muzammilpeer on 1/2/16.
 */
public class BaseActivity extends Activity {


    protected AtomicBoolean isActivityLoaded = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivity();
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
//        NetworkManager.setConfiguration(new NetworkConfig(this);
    }

    //setup will be called by oncreateView
    private void setupActivity() {
        try {
            if (isActivityLoaded.get() == false) {
                //1. Load object once
                initObjects();
            }

            // 2. inject view with butterknife or manually
            initViews();
            //3. rebind the views with listeners or adapter again for renewal created views.
            initListenerOrAdapter();

            if (isActivityLoaded.get() == false) {
                //4. Network calls once
                initNetworkCalls();
            }

            //mark current fragment as loaded just recreate the views only.
            isActivityLoaded.set(true);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }
}
