package com.muzammilpeer.pakistanitv.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.preferences.PreferencesUtil;

import io.fabric.sdk.android.Fabric;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class SmartTvApplication extends Application {


    public SmartTvApplication() {

    }
    private static SmartTvApplication instance = new SmartTvApplication();
    public static SmartTvApplication getInstance()
    {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        PreferencesUtil.setPreferencesContext(this);


    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log4a.e("onLowMemory", "SmartTvApplication");
    }

    @Override
    public void onTerminate() {
        Log4a.e("onTerminate","SmartTvApplication");
        super.onTerminate();
    }
}
