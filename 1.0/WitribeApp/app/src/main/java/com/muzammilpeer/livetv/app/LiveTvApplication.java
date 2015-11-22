package com.muzammilpeer.livetv.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.preferences.PreferencesUtil;

import io.fabric.sdk.android.Fabric;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class LiveTvApplication extends Application {


    public LiveTvApplication() {

    }
    private static LiveTvApplication instance = new LiveTvApplication();
    public static LiveTvApplication getInstance()
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
        Log4a.e("onLowMemory", "LiveTvApplication");
    }

    @Override
    public void onTerminate() {
        Log4a.e("onTerminate","LiveTvApplication");
        super.onTerminate();
    }
}
