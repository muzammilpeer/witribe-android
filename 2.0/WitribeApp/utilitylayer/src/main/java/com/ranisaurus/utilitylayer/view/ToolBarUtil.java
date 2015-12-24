package com.ranisaurus.utilitylayer.view;

import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by muzammilpeer on 9/5/15.
 */
public class ToolBarUtil {

    public static void changeStatusBarColor(Window window, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

}
