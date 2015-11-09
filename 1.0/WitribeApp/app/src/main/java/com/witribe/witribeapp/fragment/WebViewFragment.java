package com.witribe.witribeapp.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.muzammilpeer.ffmpeglayer.helpers.CpuArchHelper;
import com.muzammilpeer.ffmpeglayer.imanager.IBufferStream;
import com.muzammilpeer.ffmpeglayer.manager.FFmpegManager;
import com.muzammilpeer.ffmpeglayer.manager.InstallationManager;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.utilitylayer.file.FileUtil;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.view.PlayerVideoView;
import com.ranisaurus.utilitylayer.view.WindowUtil;
import com.witribe.witribeapp.MainActivity;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.controls.IVideoPlayerControls;
import com.witribe.witribeapp.controls.VideoPlayerControls;
import com.witribe.witribeapp.manager.UserManager;

import java.util.UUID;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/18/15.
 */
public class WebViewFragment extends BaseFragment implements
        View.OnClickListener, IVideoPlayerControls,IBufferStream,View.OnTouchListener {

    private static final String ARG_CATEGORY_NAME = "category_name";
    //UI references.
    @Bind(R.id.vw_playerview)
    PlayerVideoView vwPlayerView;

    @Bind(R.id.fab_recording)
    FloatingActionButton fabRecording;

    @Bind(R.id.tvViewersCount)
    TextView tvViewersCount;

    @Bind(R.id.tvTime)
    TextView tvTime;

    @Bind(R.id.tvFileSize)
    TextView tvFileSize;

    @Bind(R.id.tvFPS)
    TextView tvFPS;

    @Bind(R.id.tvBitRate)
    TextView tvBitRate;

    @Bind(R.id.tvRecordingStatus)
    TextView tvRecordingStatus;

    private Data currentData;

    private boolean recording = false;

    private String streamingUrl;

    private String MP4_FILE_PATH = "";

    private Thread mThread;


    public static WebViewFragment newInstance(Data filterList) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_CATEGORY_NAME, filterList);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            try {
                currentData = getArguments().getParcelable(ARG_CATEGORY_NAME);
            } catch (Exception e) {
                Log4a.printException(e);
            }
        }

        super.onCreateView(inflater, R.layout.fragment_webview);

        return mView;
    }


    @Override
    public void initViews() {
        super.initViews();
        getBaseActivity().isFullScreenOptionEnable = false;

        //FFMpeg manager debuggin on
        FFmpegManager.getInstance().setContext(getBaseActivity().getApplicationContext());

        getBaseActivity().getSupportActionBar().setTitle(R.string.live_stream);
//        getBaseActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBaseActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
            getBaseActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        }
        getBaseActivity().getTabLayoutView().setVisibility(View.GONE);
        getBaseActivity().hideToolBar();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int displayHeight = displaymetrics.heightPixels;
        int displayWidth = displaymetrics.widthPixels;

//        displayHeight = mView.getHeight();
//        displayWidth = mView.getWidth();

        if (getBaseActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            WindowUtil.hideSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displayHeight, displayWidth);
            vwPlayerView.getHolder().setFixedSize(displayHeight, displayWidth);
        } else {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            WindowUtil.showSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displayWidth, displayHeight / 2);
            vwPlayerView.getHolder().setFixedSize(displayWidth, displayHeight / 2);
        }


    }

    @Override
    public void initObjects() {
        super.initObjects();

        VideoPlayerControls.releaseDialog();

        streamingUrl = currentData.video_iosStreamUrl + "&token=" + UserManager.getInstance().getUserProfile().getToken();
        Log4a.e("Streaming URL = ", streamingUrl);


        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Install FFMpeg
                String cpuArchNameFromAssets = CpuArchHelper.getCpuArch().getArchFullName();

                if (!TextUtils.isEmpty(cpuArchNameFromAssets)) {
                    InstallationManager installationManager = InstallationManager.getInstance(cpuArchNameFromAssets, getBaseActivity());
                    installationManager.installFFMpegInDevice();
                } else {
                    Log4a.printException(new Exception("Device not supported"));
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();

        tvViewersCount.setText(currentData.totalViews + "");

        vwPlayerView.setMediaController(null);
        vwPlayerView.setVideoURI(Uri.parse(streamingUrl));
        vwPlayerView.start();
        vwPlayerView.requestFocus();

        vwPlayerView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log4a.e("setOnErrorListener", "came");
                return false;
            }
        });

        vwPlayerView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log4a.e("setOnCompletionListener", "came");
            }
        });

        vwPlayerView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log4a.e("setOnPreparedListener", "came");
            }
        });

        vwPlayerView.setOnTouchListener(this);

        fabRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log4a.e("Clicking check it", "nothing happend Recording");
                if (recording) {
                    stopRecording();
                } else if (recording == false) {
                    startRecording();
                }
            }
        });


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (vwPlayerView != null) {
                if (VideoPlayerControls.isShowingDialog()) {
                    VideoPlayerControls.hideControls();
                } else {
                    VideoPlayerControls.showControls(getBaseActivity(), "test",this);

                }
            }
        }
        return false;
    }

    private void stopRecording() {
        Log4a.e("Click", "Stop Recording");
        if (tvRecordingStatus != null)
        tvRecordingStatus.setText(" Stopped");

        recording = false;

        //stop the recording
        FFmpegManager.getInstance().stopLiveStreamRecording();

        if (fabRecording != null)
        fabRecording.setImageDrawable(getBaseActivity().getResources().getDrawable(R.drawable.ic_videocam_white_24dp));

    }

    private void startRecording() {
        if (tvRecordingStatus != null)
        tvRecordingStatus.setText(" Started");

        recording = true;
        Log4a.e("Click", "Start Recording");
        String folderPath = FileUtil.createFolder(getString(R.string.app_name));
        MP4_FILE_PATH = folderPath + "/" + UUID.randomUUID() + ".mp4";
        FFmpegManager.getInstance().startLiveStreamRecording(streamingUrl, MP4_FILE_PATH, this);

        if (fabRecording != null)
        fabRecording.setImageDrawable(getBaseActivity().getResources().getDrawable(R.drawable.ic_videocam_off_white_24dp));

    }


    //ShellManager Callback
    @Override
    public void onStreamInit(String progress) {

    }

    @Override
    public void onStreamWorking(final String progress) {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progress.contains("frame=")) {
                    //frame=  138 fps= 25 q=-1.0 size=     307kB time=00:00:05.57 bitrate= 451.5kbits/s
                    String fpsString = progress.substring(progress.indexOf("fps") + 4, progress.indexOf("q"));
                    String sizeString = progress.substring(progress.indexOf("size") + 5, progress.indexOf("time"));
                    String timeString = progress.substring(progress.indexOf("time") + 5, progress.indexOf("bitrate"));
                    String bitRateString = progress.substring(progress.indexOf("bitrate") + 8);

                    tvBitRate.setText(bitRateString + "");
                    tvFileSize.setText(sizeString + "");
                    tvTime.setText(timeString + "");
                    tvFPS.setText(fpsString + "");
                }
            }
        });
    }

    @Override
    public void onStreamCompleted(String progress) {

    }

    @Override
    public void onStreamError(String error) {

    }

    //Video Player Callback

    @Override
    public void onPauseVideo() {
        if (vwPlayerView.isPlaying()) {
            vwPlayerView.pause();
        }
    }

    @Override
    public void onPlayVideo() {
        if (!vwPlayerView.isPlaying()) {
            vwPlayerView.start();
        }
    }

    @Override
    public void onStopVideo() {
        if (vwPlayerView.isPlaying()) {
            vwPlayerView.stopPlayback();
        }
    }

    @Override
    public void onStartRecording() {
        if (!recording) {
            startRecording();
        }
    }

    @Override
    public void onStopRecording() {
        if (recording)
        {
            stopRecording();
        }
    }

    @Override
    public void onVideoQualitySelected(boolean HQ) {
        if (HQ)
        {
            streamingUrl = currentData.video_iosStreamUrl + "&token=" + UserManager.getInstance().getUserProfile().getToken();
        }else {
            streamingUrl = currentData.video_iosStreamUrlLow + "&token=" + UserManager.getInstance().getUserProfile().getToken();
            if (streamingUrl == null || streamingUrl.length() < 1)
            {
                streamingUrl = currentData.video_iosStreamUrl + "&token=" + UserManager.getInstance().getUserProfile().getToken();
            }
        }

        if (vwPlayerView != null)
        {
            vwPlayerView.stopPlayback();
            vwPlayerView.setMediaController(null);
            vwPlayerView.setVideoURI(Uri.parse(streamingUrl));
            vwPlayerView.start();
            vwPlayerView.requestFocus();
        }
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log4a.e("onConfigurationChanged", " called");


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int displayHeight = displaymetrics.heightPixels;
        int displayWidth = displaymetrics.widthPixels;

        displayWidth = mView.getWidth();
        displayHeight = mView.getHeight();

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            WindowUtil.hideSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displayHeight, displayWidth);
            vwPlayerView.getHolder().setFixedSize(displayHeight, displayWidth);
        } else {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            WindowUtil.showSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displayWidth, displayHeight / 2);
            vwPlayerView.getHolder().setFixedSize(displayWidth, displayHeight / 2);
        }

    }


    private void showNotificationLollipop(int id) {
        // Instantiate a Builder object.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseActivity());
// Creates an Intent for the Activity
        Intent notifyIntent =
                new Intent(getBaseActivity(), MainActivity.class);
// Sets the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Creates the PendingIntent
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        getBaseActivity(),
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

// Puts the PendingIntent into the notification builder
        builder.setContentIntent(notifyPendingIntent);
// Notifications are issued by sending them to the
// NotificationManager system service.
        NotificationManager mNotificationManager =
                (NotificationManager) getBaseActivity().getSystemService(Context.NOTIFICATION_SERVICE);
// Builds an anonymous Notification object from the builder, and
// passes it to the NotificationManager
        mNotificationManager.notify(id, builder.build());
    }


    private void showNotificationWithPlaybackLockScreen() {
//        Notification notification = new Notification.Builder(getBaseActivity())
//                // Show controls on lock screen even when user hides sensitive content.
//                .setVisibility(Notification.VISIBILITY_PUBLIC)
//                .setSmallIcon(R.drawable.ic_play_arrow_white_24dp)
//                        // Add media control buttons that invoke intents in your media service
//                .addAction(R.drawable.ic_skip_previous_white_24dp, "Previous", prevPendingIntent) // #0
//                .addAction(R.drawable.ic_pause_white_24dp, "Pause", pausePendingIntent)  // #1
//                .addAction(R.drawable.ic_skip_next_white_24dp, "Next", nextPendingIntent)     // #2
//                        // Apply the media style template
//                .setStyle(new Notification.MediaStyle()
//                        .setShowActionsInCompactView(1 /* #1: pause button */)
//                        .setMediaSession(mMediaSession.getSessionToken())
//                        .setContentTitle("Wonderful music")
//                        .setContentText("My Awesome Band")
//                        .setLargeIcon(albumArtBitmap)
//                        .build();
    }

    private void downloadNotification(final int ID) {
        final NotificationManager mNotifyManager =
                (NotificationManager) getBaseActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseActivity());
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_videocam_off_white_24dp);
// Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr += 5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            mBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            mNotifyManager.notify(0, mBuilder.build());
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(5 * 1000);
                            } catch (InterruptedException e) {
                            }
                        }
                        // When the loop is finished, updates the notification
                        mBuilder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0, 0, false);
                        mNotifyManager.notify(ID, mBuilder.build());
                    }
                }
// Starts the thread by calling the run() method in its Runnable
        ).start();
    }


}
