package com.muzammilpeer.smarttvapp.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muzammilpeer.ffmpeglayer.helpers.CpuArchHelper;
import com.muzammilpeer.ffmpeglayer.imanager.IBufferStream;
import com.muzammilpeer.ffmpeglayer.manager.FFmpegManager;
import com.muzammilpeer.ffmpeglayer.manager.InstallationManager;
import com.muzammilpeer.smarttvapp.manager.UserManager;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.view.CGSize;
import com.ranisaurus.utilitylayer.view.PlayerVideoView;
import com.ranisaurus.utilitylayer.view.WindowUtil;
import com.muzammilpeer.livetv.R;
import com.muzammilpeer.smarttvapp.controls.IVideoPlayerControls;
import com.muzammilpeer.smarttvapp.controls.VideoPlayerControls;
import com.muzammilpeer.smarttvapp.service.LiveRecordIntentService;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/18/15.
 */
public class WebViewFragment extends BaseFragment implements
        View.OnClickListener, IVideoPlayerControls, IBufferStream, View.OnTouchListener {


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

    @Bind(R.id.ll_bottom_view)
    LinearLayout llViewsCount;

    @Bind(R.id.ll_recording_view)
    LinearLayout llRecordingView;


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

        CGSize displaySize = WindowUtil.getScreenSizeInPixel(getBaseActivity());

        if (getBaseActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            WindowUtil.hideSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displaySize.WIDTH, displaySize.HEIGHT);
            vwPlayerView.getHolder().setFixedSize(displaySize.WIDTH, displaySize.HEIGHT);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            vwPlayerView.setLayoutParams(params); //causes layout update
        } else {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            WindowUtil.showSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displaySize.WIDTH, getResources().getDimensionPixelSize(R.dimen.videoplayer_potrait_height));
            vwPlayerView.getHolder().setFixedSize(displaySize.WIDTH, getResources().getDimensionPixelSize(R.dimen.videoplayer_potrait_height));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
            vwPlayerView.setLayoutParams(params); //causes layout update
        }
        vwPlayerView.invalidate();


    }

    @Override
    public void initObjects() {
        super.initObjects();

        VideoPlayerControls.releaseDialog();

        if (currentData.videoURL != null && currentData.videoURL.length() > 0) {
            streamingUrl = currentData.videoURL + "&token=" + UserManager.getInstance().getUserProfile().getToken();
        } else if (currentData.vod_flash != null && currentData.vod_flash.length() > 0) {
            streamingUrl = currentData.vod_flash.replaceAll(" ", "%20");
        } else if (currentData.video != null && currentData.video.length() > 0) {
            streamingUrl = currentData.video.replaceAll(" ", "%20");
        } else {
            streamingUrl = currentData.video_iosStreamUrl + "&token=" + UserManager.getInstance().getUserProfile().getToken();
        }

        UserManager.getInstance().setVideoStreamingUrl(streamingUrl);
        Log4a.e("Streaming URL = ", UserManager.getInstance().getVideoStreamingUrl());


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

        if (currentData.video != null && currentData.video.length() > 0) {
            vwPlayerView.setMediaController(new MediaController(getBaseActivity()));
        } else {
            vwPlayerView.setMediaController(null);
        }
        vwPlayerView.setVideoURI(Uri.parse(UserManager.getInstance().getVideoStreamingUrl()));
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
                Log4a.e("Live Sttream Duration =  ", mp.getDuration() + "");

//                if (mp.getDuration() == -1) {
//                    //live stream channel
//                } else {
//                    //live movie
//                    vwPlayerView.setMediaController(new MediaController(getBaseActivity()));
//                    vwPlayerView.setOnTouchListener(null);
//
//                }

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
                if (currentData.video == null || currentData.video.length() < 1) {
                    if (VideoPlayerControls.isShowingDialog()) {
                        VideoPlayerControls.hideControls();
                    } else {
                        VideoPlayerControls.showControls(getBaseActivity(), "test", this);

                    }
                }

            }
        }
        return false;
    }

    private void stopRecording() {
        Log4a.e("Click", "Stop Recording");
        if (tvRecordingStatus != null)
            tvRecordingStatus.setText(" Stopped");

        tvBitRate.setText("");
        tvFileSize.setText("");
        tvTime.setText("");
        tvFPS.setText("");


        recording = false;

        //stop the recording
//        FFmpegManager.getInstance().stopLiveStreamRecording();
        Intent intent = new Intent(getBaseActivity(), LiveRecordIntentService.class);
        intent.putExtra(LiveRecordIntentService.LIVE_VIDEO_STOP_KEY, "true");

        getBaseActivity().startService(intent);
//        LiveRecordNotification.getInstance(getBaseActivity()).setOnGoing(false);
//        LiveRecordNotification.getInstance(getBaseActivity()).hideNotification();

        if (fabRecording != null)
            fabRecording.setImageDrawable(getBaseActivity().getResources().getDrawable(R.drawable.ic_videocam_white_24dp));

    }

    private void startRecording() {
        if (tvRecordingStatus != null)
            tvRecordingStatus.setText(" Started");

        recording = true;
        Log4a.e("Click", "Start Recording");
//        String folderPath = FileUtil.createFolder(getString(R.string.app_name));
//        MP4_FILE_PATH = folderPath + "/" + UUID.randomUUID() + ".mp4";
//        FFmpegManager.getInstance().startLiveStreamRecording(streamingUrl, MP4_FILE_PATH, this);

        Intent intent = new Intent(getBaseActivity(), LiveRecordIntentService.class);
        intent.putExtra(LiveRecordIntentService.LIVE_VIDEO_URL_KEY, UserManager.getInstance().getVideoStreamingUrl());
        getBaseActivity().startService(intent);
//        LiveRecordNotification.getInstance(getBaseActivity());

        if (fabRecording != null)
            fabRecording.setImageDrawable(getBaseActivity().getResources().getDrawable(R.drawable.ic_videocam_off_white_24dp));

    }


    //ShellManager Callback
    @Override
    public void onStreamInit(String progress) {
//        Log4a.e("onStreamInit", progress);
    }

    @Override
    public void onStreamWorking(final String progress) {
        Log4a.e("onStreamWorking", progress);

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
//        Log4a.e("onStreamCompleted", progress);

    }

    @Override
    public void onStreamError(String error) {
//        Log4a.e("onStreamCompleted", error);

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
        if (recording) {
            stopRecording();
        }
    }

    @Override
    public void onVideoQualitySelected(boolean HQ) {
        if (HQ) {
            if (currentData.videoURL != null && currentData.videoURL.length() > 0) {
                streamingUrl = currentData.videoURL + "&token=" + UserManager.getInstance().getUserProfile().getToken();
            } else if (currentData.vod_flash != null && currentData.vod_flash.length() > 0) {
                streamingUrl = currentData.vod_flash.replaceAll(" ", "%20");
            } else if (currentData.video != null && currentData.video.length() > 0) {
                streamingUrl = currentData.video.replaceAll(" ", "%20");
            } else {
                streamingUrl = currentData.video_iosStreamUrl + "&token=" + UserManager.getInstance().getUserProfile().getToken();
            }
        } else {
            if (currentData.videoURL != null && currentData.videoURL.length() > 0) {
                streamingUrl = currentData.videoURL + "&token=" + UserManager.getInstance().getUserProfile().getToken();
            } else if (currentData.vod_flash != null && currentData.vod_flash.length() > 0) {
                streamingUrl = currentData.vod_flash.replaceAll(" ", "%20");
            } else if (currentData.sdvideo != null && currentData.sdvideo.length() > 0) {
                streamingUrl = currentData.sdvideo.replaceAll(" ", "%20");
            } else {
                streamingUrl = currentData.video_iosStreamUrlLow + "&token=" + UserManager.getInstance().getUserProfile().getToken();
            }

        }
        UserManager.getInstance().setVideoStreamingUrl(streamingUrl);

        if (vwPlayerView != null) {
            vwPlayerView.stopPlayback();
            vwPlayerView.setMediaController(null);
            vwPlayerView.setVideoURI(Uri.parse(UserManager.getInstance().getVideoStreamingUrl()));
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

        if (llViewsCount != null) {
            CGSize displaySize = WindowUtil.getScreenSizeInPixel(getBaseActivity());


            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                WindowUtil.hideSystemUi(getBaseActivity());

                vwPlayerView.setDimensions(displaySize.WIDTH, displaySize.HEIGHT);
                vwPlayerView.getHolder().setFixedSize(displaySize.WIDTH, displaySize.HEIGHT);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                vwPlayerView.setLayoutParams(params); //causes layout update

                llViewsCount.setVisibility(View.GONE);
                llRecordingView.setVisibility(View.GONE);

            } else {
                getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                WindowUtil.showSystemUi(getBaseActivity());

                vwPlayerView.setDimensions(displaySize.WIDTH, getResources().getDimensionPixelSize(R.dimen.videoplayer_potrait_height));
                vwPlayerView.getHolder().setFixedSize(displaySize.WIDTH, getResources().getDimensionPixelSize(R.dimen.videoplayer_potrait_height));

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                vwPlayerView.setLayoutParams(params); //causes layout update

                llViewsCount.setVisibility(View.VISIBLE);
                llRecordingView.setVisibility(View.GONE);
            }
        }
    }

}
