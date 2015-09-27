package com.ranisaurus.newtorklayer.manager;

import android.content.Context;

/**
 * Created by MuzammilPeer on 3/13/2015.
 */
public class NetworkConfig {
    public Context context;
    private String baseURL;
    private boolean isBaseURL;

    public NetworkConfig() {
    }

    public NetworkConfig(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public boolean isBaseURL() {
        return (baseURL == null && baseURL.length() > 0) ? false : true;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
}
