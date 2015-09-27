package com.ranisaurus.newtorklayer.manager;

import android.content.Context;

/**
 * Created by MuzammilPeer on 3/13/2015.
 */
public class NetworkConfig {
    private static String baseURL = "http://pitelevision.com/amfphp/Amfphp/index.php?contentType=application/json";
    public Context context;
    private boolean isBaseURL;

    public NetworkConfig() {
    }

    public NetworkConfig(Context context) {
        this.context = context;
    }

    public static String getBaseURL() {
        return baseURL;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isBaseURL() {
        return (getBaseURL() == null && getBaseURL().length() > 0) ? false : true;
    }

    public static void setBaseURL(String url) {
        baseURL = url;
    }
}
