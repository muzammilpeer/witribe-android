package com.ranisaurus.utilitylayer.view;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;


/**
 * Created by muzammilpeer on 9/5/15.
 */
public class ResourceUtil {

    private static int COLOR_LIMIT = 12;

    public static Drawable getCircularDrawable(View itemView, long position, String defPackage) {
        return itemView.getResources().getDrawable(getCircularDrawableResourceID(itemView, position, defPackage));
    }

    public static int getCircularDrawableResourceID(View itemView, long position, String defPackage) {
        String resourceName = "icon_circular_" + (position % ResourceUtil.COLOR_LIMIT);
        return itemView.getResources().getIdentifier(resourceName, "drawable", defPackage);
    }

    public static int getCircularColorResourceID(Activity itemView, long position, String defPackage) {
        String resourceName = "icon_color_" + (position % ResourceUtil.COLOR_LIMIT);
        return itemView.getResources().getIdentifier(resourceName, "color", defPackage);
    }

    public static int getStatusBarColorResourceID(Activity itemView, long position, String defPackage) {
        String resourceName = "status_color_" + (position % ResourceUtil.COLOR_LIMIT);
        return itemView.getResources().getIdentifier(resourceName, "color", defPackage);
    }

    public static Drawable whiteDrawable(View itemView, int drawableID) {
        Drawable drawable = itemView.getResources().getDrawable(drawableID);
        drawable.setColorFilter(itemView.getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }
}
