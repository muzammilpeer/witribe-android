package com.muzammilpeer.smarttvapp.constant;

import android.content.Context;

import com.muzammilpeer.smarttvapp.R;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class PreferencesKeys {
    public static String user_profile = "user_profile";

    public static int getGridColumnCount(Context context)
    {
        return (int) context.getResources().getInteger(R.integer.gridColumns);
    }
}
