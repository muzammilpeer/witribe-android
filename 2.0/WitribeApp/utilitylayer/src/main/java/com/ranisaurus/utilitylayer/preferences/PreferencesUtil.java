package com.ranisaurus.utilitylayer.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class PreferencesUtil {

    public static final String APP_SETTINGS_FILE = "shared_preferences";

    private static Context context;

    private PreferencesUtil() {

    }


    public static void setPreferencesContext(Context mContext) {
        context = mContext;
    }

    public static void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(APP_SETTINGS_FILE,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void savePreferences(String key, String value) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(APP_SETTINGS_FILE,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void savePreferences(String key, int value) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(APP_SETTINGS_FILE,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void clearAllPreferences() {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(APP_SETTINGS_FILE,
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    public static String getPreferences(String key, String default_value) {

        SharedPreferences sharedPreferences = context
                .getSharedPreferences(APP_SETTINGS_FILE,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, default_value);
    }

    public static boolean getPreferences(String key, boolean default_value) {

        SharedPreferences sharedPreferences = context
                .getSharedPreferences(APP_SETTINGS_FILE,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, default_value);
    }

    public static int getPreferences(String key, int default_value) {

        SharedPreferences sharedPreferences = context
                .getSharedPreferences(APP_SETTINGS_FILE,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, default_value);
    }
}
