package com.ranisaurus.utilitylayer.keyboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by muzammilpeer on 9/26/15.
 */
public class KeyboardUtil {

    public static void hideKeyboard(Context context) {
        InputMethodManager inputManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        View v = ((Activity) context).getCurrentFocus();
        if (v == null) {
            return;
        }

        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
