package com.witribe.witribeapp.fragment;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.manager.UserManager;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/18/15.
 */
public class WebViewFragment extends BaseFragment {

    private static final String ARG_CATEGORY_NAME = "category_name";
    //UI references.
    @Bind(R.id.wv_webview)
    WebView wvWebView;

    @Bind(R.id.vw_playerview)
    VideoView vwPlayerView;

    @Bind(R.id.fab_recording)
    FloatingActionButton fabRecording;

    private Data currentData;

    private boolean recording=false;


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

        getBaseActivity().getTabLayoutView().setVisibility(View.GONE);
        getBaseActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    @Override
    public void initObjects() {
        super.initObjects();

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();

        wvWebView.setVisibility(View.GONE);
        vwPlayerView.setVisibility(View.VISIBLE);

        MediaController mediaController = new MediaController(getBaseActivity());
        mediaController.setAnchorView(vwPlayerView);
        vwPlayerView.setMediaController(mediaController);

        String fullUrl = currentData.video_iosStreamUrl + "&token=" + UserManager.getInstance().getUserProfile().getToken();
        Log4a.e("Streaming URL = ", fullUrl);

        MediaPlayer mediaPlayer = new MediaPlayer();
        vwPlayerView.setVideoURI(Uri.parse(fullUrl));
        vwPlayerView.start();
        vwPlayerView.requestFocus();
        switchFullScreen();
        getBaseActivity().hideToolBar();


        fabRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recording) {
                    recording = false;
                    getBaseActivity().stopScreenRecording();
                } else {
                    recording = true;
                    getBaseActivity().startScreenRecording();
                }
            }
        });



        getBaseActivity().getSupportActionBar().setTitle(R.string.live_stream);
    }

    private void switchFullScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        vwPlayerView.setLayoutParams(params);
    }

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
}
