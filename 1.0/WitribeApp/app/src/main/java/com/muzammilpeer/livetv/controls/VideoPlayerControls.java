package com.muzammilpeer.livetv.controls;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.ToggleButton;

import com.muzammilpeer.livetv.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by muzammilpeer on 11/7/15.
 */
public class VideoPlayerControls extends Dialog implements DialogInterface.OnCancelListener, View.OnClickListener {

    IVideoPlayerControls mDelegate;

    static VideoPlayerControls mDialog;

    @Bind(R.id.fab_stop_video)
    FloatingActionButton fabStopVideo;
    @Bind(R.id.fab_play_video)
    FloatingActionButton fabPlayVideo;
    @Bind(R.id.fab_start_recording)
    FloatingActionButton fabStartRecording;
    @Bind(R.id.fab_stop_recording)
    FloatingActionButton fabStopRecording;
    @Bind(R.id.tbVideoQuality)
    ToggleButton tbVideoQuality;


    public VideoPlayerControls(Context context,IVideoPlayerControls delegate) {
        super(context);
        mDelegate = delegate;
    }

    public VideoPlayerControls(Context context, int themeResId) {
        super(context, themeResId);
    }

    public VideoPlayerControls(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        dialog.dismiss();
    }

    public static void setDelegate(IVideoPlayerControls delegate) {
        if (mDialog != null) {
            mDialog.mDelegate = delegate;
        }
    }

    public static void releaseDialog()
    {
        if (mDialog != null)
        {
            mDialog.dismiss();
            mDialog.mDelegate = null;
            mDialog = null;
        }
    }
    public static void showControls(Context context, String title,IVideoPlayerControls delegate) {

        if (mDialog == null) {
            mDialog = new VideoPlayerControls(context,delegate);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            mDialog.setTitle(title);
            mDialog.setCancelable(true);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                }
            });
            //The next line will add the ProgressBar to the dialog.
            mDialog.setContentView(R.layout.dialog_video_controls);

            ButterKnife.bind(mDialog);

            mDialog.fabStopVideo.setOnClickListener(mDialog);
            mDialog.fabPlayVideo.setOnClickListener(mDialog);
            mDialog.fabStartRecording.setOnClickListener(mDialog);
            mDialog.fabStopRecording.setOnClickListener(mDialog);
            mDialog.tbVideoQuality.setOnClickListener(mDialog);
        }

        if (mDialog != null) {
            mDialog.show();

        }
    }

    public static void hideControls() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public static boolean isShowingDialog() {
        if (mDialog != null) {
            return mDialog.isShowing();
        }

        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_stop_video: {
                if (mDelegate != null) {
                    mDelegate.onStopVideo();
                }
            }
            break;
            case R.id.fab_play_video: {
                if (mDelegate != null) {
                    mDelegate.onPlayVideo();
                }
            }
            break;
            case R.id.fab_start_recording: {
                if (mDelegate != null) {
                    mDelegate.onStartRecording();
                }
            }
            break;
            case R.id.fab_stop_recording: {
                if (mDelegate != null) {
                    mDelegate.onStopRecording();
                }
            }
            break;

            case R.id.tbVideoQuality: {
                if (mDelegate != null) {
                    mDelegate.onVideoQualitySelected(tbVideoQuality.isChecked());
                }
            }
            break;

        }

    }
}
