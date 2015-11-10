package com.witribe.witribeapp.app;

import android.app.Application;

import com.ranisaurus.utilitylayer.preferences.PreferencesUtil;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class WitribeApplication extends Application {


    public WitribeApplication() {

    }
    private static WitribeApplication instance = new WitribeApplication();
    public static WitribeApplication getInstance()
    {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        PreferencesUtil.setPreferencesContext(this);

//        Fabric.with(this, new Crashlytics());



    }


}
