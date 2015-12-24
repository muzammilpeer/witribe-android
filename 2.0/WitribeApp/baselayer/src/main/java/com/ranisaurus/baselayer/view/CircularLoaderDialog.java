package com.ranisaurus.baselayer.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;

import com.ranisaurus.baselayer.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by muzammilpeer on 8/30/15.
 */
public class CircularLoaderDialog extends Dialog {
    public static AtomicInteger progressDialogCounter = new AtomicInteger(0);

    public CircularLoaderDialog(Context context) {
        super(context, R.style.CircularLoaderDialogTheme);
    }

    public CircularLoaderDialog show(Context context, CharSequence title,
                                     CharSequence message) {
        return show(context, title, message, false);
    }

    public CircularLoaderDialog show(Context context, CharSequence title,
                                     CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public CircularLoaderDialog show(Context context, CharSequence title,
                                     CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public CircularLoaderDialog show(Context context, CharSequence title,
                                     CharSequence message, boolean indeterminate, boolean cancelable,
                                     OnCancelListener cancelListener) {
        CircularLoaderDialog dialog = new CircularLoaderDialog(context);
        dialog.setTitle(title);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        //The next line will add the ProgressBar to the dialog.
        dialog.addContentView(new ProgressBar(context), new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
    }

}
