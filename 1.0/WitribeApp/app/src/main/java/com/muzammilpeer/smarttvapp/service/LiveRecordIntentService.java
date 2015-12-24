package com.muzammilpeer.smarttvapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.muzammilpeer.ffmpeglayer.imanager.IBufferStream;
import com.muzammilpeer.ffmpeglayer.manager.FFmpegManager;
import com.muzammilpeer.smarttvapp.manager.UserManager;
import com.muzammilpeer.smarttvapp.notifications.LiveRecordNotification;
import com.ranisaurus.utilitylayer.file.FileUtil;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.muzammilpeer.smarttvapp.R;

import java.util.UUID;

/**
 * Created by muzammilpeer on 11/21/15.
 */
public class LiveRecordIntentService extends IntentService implements IBufferStream {
    private String MP4_FILE_PATH = "";

    public static boolean isRecordingRunning = false;
    public static String LIVE_VIDEO_URL_KEY = "LIVE_VIDEO_URL";
    public static String LIVE_IMAGE_URL_KEY = "LIVE_IMAGE_URL_KEY";
    public static String LIVE_TITLE_URL_KEY = "LIVE_TITLE_URL_KEY";
    public static String LIVE_VIDEO_STOP_KEY = "LIVE_VIDEO_STOP_KEY";
    private String streamingLiveVideoUrl = "";

    private int LIVE_RECORDING_SESSION_ID = 123456789;

    private NotificationCompat.Builder mBuilder;

    public LiveRecordIntentService() {
        super("LiveRecordIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getExtras() != null) {
            if (intent.getExtras().getString(LIVE_VIDEO_URL_KEY) != null) {
                if (!FFmpegManager.getInstance().isLiveStreamRecording()) {
                    streamingLiveVideoUrl = intent.getStringExtra(LIVE_VIDEO_URL_KEY);
                    String folderPath = FileUtil.createFolder(getString(R.string.app_name) + "/video/");
                    MP4_FILE_PATH = folderPath + "/" + UUID.randomUUID() + ".mp4";
                    isRecordingRunning = true;
                    LiveRecordNotification.getInstance(getApplicationContext());
                    FFmpegManager.getInstance().startLiveStreamRecording(streamingLiveVideoUrl, MP4_FILE_PATH, this);

                } else {
                    Toast.makeText(this, "Recording already running", Toast.LENGTH_SHORT).show();
                }
            } else if (intent.getExtras().getString(LIVE_VIDEO_STOP_KEY) != null) {
                FFmpegManager.getInstance().stopLiveStreamRecording();
                isRecordingRunning = false;
                LiveRecordNotification.getInstance(getApplicationContext()).setOnGoing(false);
                LiveRecordNotification.getInstance(getApplicationContext()).hideNotification();

            }
//           startForeground(LIVE_RECORDING_SESSION_ID, LiveRecordNotification.getInstance(getApplicationContext()));
        }

        if (intent != null && intent.getAction() != null && intent.getAction().equalsIgnoreCase(LiveRecordNotification.INTENT_NOTIFICATION_START_RECORDING)) {
            if (!FFmpegManager.getInstance().isLiveStreamRecording()) {
                streamingLiveVideoUrl = UserManager.getInstance().getVideoStreamingUrl();
                String folderPath = FileUtil.createFolder(getString(R.string.app_name) + "/video/");
                MP4_FILE_PATH = folderPath + "/" + UUID.randomUUID() + ".mp4";
                isRecordingRunning = true;
                LiveRecordNotification.getInstance(getApplicationContext());
                FFmpegManager.getInstance().startLiveStreamRecording(streamingLiveVideoUrl, MP4_FILE_PATH, this);

            } else {
                Toast.makeText(this, "Recording already running", Toast.LENGTH_SHORT).show();
            }
        }
        if (intent != null && intent.getAction() != null && intent.getAction().equalsIgnoreCase(LiveRecordNotification.INTENT_NOTIFICATION_STOP_RECORDING)) {
            FFmpegManager.getInstance().stopLiveStreamRecording();
            isRecordingRunning = false;
            LiveRecordNotification.getInstance(getApplicationContext()).setOnGoing(false);
            LiveRecordNotification.getInstance(getApplicationContext()).hideNotification();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        //stop the recording
        FFmpegManager.getInstance().stopLiveStreamRecording();

        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    //ShellManager Callback
    @Override
    public void onStreamInit(String progress) {
        Log4a.e("onStreamInit", progress);
    }

    @Override
    public void onStreamWorking(final String progress) {
        Log4a.e("onStreamWorking", progress);

//        if (progress.contains("frame=")) {
//            //frame=  138 fps= 25 q=-1.0 size=     307kB time=00:00:05.57 bitrate= 451.5kbits/s
//            String fpsString = progress.substring(progress.indexOf("fps") + 4, progress.indexOf("q"));
//            String sizeString = progress.substring(progress.indexOf("size") + 5, progress.indexOf("time"));
//            String timeString = progress.substring(progress.indexOf("time") + 5, progress.indexOf("bitrate"));
//            String bitRateString = progress.substring(progress.indexOf("bitrate") + 8);
//
//            tvBitRate.setText(bitRateString + "");
//            tvFileSize.setText(sizeString + "");
//            tvTime.setText(timeString + "");
//            tvFPS.setText(fpsString + "");
//        }
    }

    @Override
    public void onStreamCompleted(String progress) {
        Log4a.e("onStreamCompleted", progress);
    }

    @Override
    public void onStreamError(String error) {
        Log4a.e("onStreamCompleted", error);
    }

}