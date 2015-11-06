package com.witribe.witribeapp.fragment;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
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
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.manager.UserManager;

import java.util.UUID;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/18/15.
 */
public class WebViewFragment extends BaseFragment {

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

        WindowUtil.hideSystemUi(getBaseActivity());
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


        getBaseActivity().isFullScreenOptionEnable = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBaseActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getBaseActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);

//        switchFullScreen();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().
                getMetrics(displayMetrics);
        int fullScreenWidth = displayMetrics.widthPixels;
        int fullScreenHeight = displayMetrics.heightPixels;

        vwPlayerView.setDimensions(fullScreenHeight, fullScreenWidth);

        getBaseActivity().hideToolBar();

        getBaseActivity().getTabLayoutView().setVisibility(View.GONE);


//        getBaseActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        vwPlayerView.setVisibility(View.VISIBLE);

        getBaseActivity().getSupportActionBar().setTitle(R.string.live_stream);


//        if (getBaseActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            int height = displaymetrics.heightPixels;
//            int width = displaymetrics.widthPixels;
//            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
//            params.width = width;
//            params.height=height-40;
//            params.setMargins(0, 0, 0, 0);
//
//            vwPlayerView.setLayoutParams(params);
//        } else {
//
//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            int height = displaymetrics.heightPixels;
//            int width = displaymetrics.widthPixels;
//            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
//            params.width = width;
//            params.height=height / 3;
//            params.setMargins(0, 0, 0, 0);
//            vwPlayerView.setLayoutParams(params);
//        }
    }

    @Override
    public void initObjects() {
        super.initObjects();

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();

        tvViewersCount.setText(currentData.totalViews + "");

        //FFMpeg manager debuggin on
        FFmpegManager.getInstance().setContext(getBaseActivity());

        MediaController mediaController = new MediaController(getBaseActivity());
        mediaController.setAnchorView(vwPlayerView);
        vwPlayerView.setMediaController(mediaController);


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

        vwPlayerView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log4a.e("setOnInfoListener", "came");
                return false;
            }
        });

        vwPlayerView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log4a.e("setOnPreparedListener", "came");
            }
        });


        fabRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log4a.e("Clicking check it", "nothing happend Recording");
                if (recording) {
                    Log4a.e("Click", "Stop Recording");
                    recording = false;
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);

//                    if (!vwPlayerView.isPlaying()) {
//                        vwPlayerView.resume();
//                        vwPlayerView.requestFocus();
//                    }

                    //stop the recording
                    FFmpegManager.getInstance().stopLiveStreamRecording();

                    fabRecording.setImageDrawable(getBaseActivity().getResources().getDrawable(R.drawable.ic_album_white_24dp));

                } else if (recording == false) {
                    recording = true;
                    Log4a.e("Click", "Start Recording");
                    String folderPath = FileUtil.createFolder(getString(R.string.app_name));
                    MP4_FILE_PATH = folderPath + "/" + UUID.randomUUID() + ".mp4";

                    fabRecording.setImageDrawable(getBaseActivity().getResources().getDrawable(R.drawable.ic_stop_white_24dp));

                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 1000);

//                    if (vwPlayerView.canPause()) {
//                        vwPlayerView.pause();
//                    } else {
//                        vwPlayerView.stopPlayback();
//                    }
                    FFmpegManager.getInstance().startLiveStreamRecording(streamingUrl, MP4_FILE_PATH, new IBufferStream() {
                        @Override
                        public void onStreamInit(String progress) {

                        }

                        @Override
                        public void onStreamWorking(final String progress) {
                            getBaseActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (progress.contains("frame=")) {
//                                        frame=  138 fps= 25 q=-1.0 size=     307kB time=00:00:05.57 bitrate= 451.5kbits/s

                                        String fpsString = progress.substring(progress.indexOf("fps")+4,progress.indexOf("q"));
                                        String sizeString = progress.substring(progress.indexOf("size")+5,progress.indexOf("time"));
                                        String timeString = progress.substring(progress.indexOf("time")+5,progress.indexOf("bitrate"));
                                        String bitRateString = progress.substring(progress.indexOf("bitrate")+8);

                                        tvBitRate.setText(bitRateString+"");
                                        tvFileSize.setText(sizeString+"");
                                        tvTime.setText(timeString+"");
                                        tvFPS.setText(fpsString+"");

//                                        String[] timeFrameList = progress.split(" ");
//                                        if (timeFrameList.length > 0) {
//
//                                            String[] sizeArray = timeFrameList[6].split("=");
//                                            String[] fpsArray = timeFrameList[4].split("=");
//                                            String[] timeArray = timeFrameList[13].split("=");
//                                            if (timeArray.length > 1) {
//                                                tvTime.setText(timeArray[1]);
//                                            }
//                                            if (fpsArray.length > 1) {
//                                                tvFPS.setText(timeArray[1]);
//                                            }
//
//                                            if (sizeArray.length > 1) {
//                                                tvFileSize.setText(sizeArray[1]);
//                                            }
//
//                                            if (timeFrameList[15] != null) {
//                                                tvBitRate.setText(timeFrameList[15]);
//                                            }
//
//
//                                        }
//
//                                        Log4a.e("array fetched = ", timeFrameList.toString());
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
                    });

                }
            }
        });


    }


//    private void switchFullScreen() {
//        DisplayMetrics metrics = new DisplayMetrics();
//        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
//        params.width = metrics.widthPixels;
//        params.height = metrics.heightPixels;
//        params.leftMargin = 0;
//        vwPlayerView.setLayoutParams(params);
//    }

    private void backFullScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
        params.width = (int) (300 * metrics.density);
        params.height = (int) (250 * metrics.density);
        params.leftMargin = 30;
        vwPlayerView.setLayoutParams(params);
    }


    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();

    }


    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);

            getBaseActivity().getSupportActionBar().setTitle("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }

    };


//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Log4a.e("onConfigurationChanged", " called");
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//
//
//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            int height = displaymetrics.heightPixels;
//            int width = displaymetrics.widthPixels;
//            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
//            params.width = width;
//            params.height=height - 40;
//            params.setMargins(0, 0, 0, 0);
//
//            vwPlayerView.setLayoutParams(params);
//
//            vwPlayerView.setDimensions(width, height);
//
//
//        } else {
//            DisplayMetrics displaymetrics = new DisplayMetrics();
//            getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//            int height = displaymetrics.heightPixels;
//            int width = displaymetrics.widthPixels;
//            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
//            params.width = width;
//            params.height=height / 3;
//            params.setMargins(0, 0, 0, 0);
//            vwPlayerView.setLayoutParams(params);
//
//            vwPlayerView.setDimensions(width, height);
//
//        }
//    }

}
