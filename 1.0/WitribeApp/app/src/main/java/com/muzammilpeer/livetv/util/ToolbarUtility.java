package com.muzammilpeer.livetv.util;

import android.content.Context;
import android.content.res.TypedArray;

import com.muzammilpeer.livetv.R;

/**
 * Created by muzammilpeer on 10/25/15.
 */
public class ToolbarUtility {
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabsHeight);
    }
}
