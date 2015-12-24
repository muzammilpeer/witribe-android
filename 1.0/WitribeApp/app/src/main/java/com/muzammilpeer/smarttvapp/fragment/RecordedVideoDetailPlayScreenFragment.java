package com.muzammilpeer.smarttvapp.fragment;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.utilitylayer.file.model.FileInfoModel;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.view.PlayerVideoView;
import com.ranisaurus.utilitylayer.view.WindowUtil;
import com.muzammilpeer.livetv.R;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/10/15.
 */
public class RecordedVideoDetailPlayScreenFragment extends BaseFragment {
    // UI references.
    @Bind(R.id.vw_playerview)
    PlayerVideoView vwPlayerView;

    FileInfoModel currentFile;

    public static RecordedVideoDetailPlayScreenFragment newInstance(FileInfoModel selectedFile) {
        RecordedVideoDetailPlayScreenFragment fragment = new RecordedVideoDetailPlayScreenFragment();
        Bundle args = new Bundle();
        args.putSerializable("selectedFile", selectedFile);
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
            currentFile = (FileInfoModel) getArguments().getSerializable("selectedFile");
        }

        super.onCreateView(inflater, R.layout.fragment_recorded_video_detail_playscreen);
        return mView;
    }


    @Override
    public void initViews() {
        super.initViews();

        getBaseActivity().isFullScreenOptionEnable = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getBaseActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
            getBaseActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        }

        getBaseActivity().getTabLayoutView().setVisibility(View.GONE);
        getBaseActivity().hideToolBar();

        if (currentFile != null) {
            getBaseActivity().getSupportActionBar().setTitle(currentFile.getFileName());
        }


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        if (getBaseActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            WindowUtil.hideSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displaymetrics.widthPixels, displaymetrics.heightPixels);
            vwPlayerView.getHolder().setFixedSize(displaymetrics.widthPixels, displaymetrics.heightPixels);

            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
            params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            vwPlayerView.setLayoutParams(params1); //causes layout update
        } else {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            WindowUtil.showSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displaymetrics.widthPixels, getResources().getDimensionPixelSize(R.dimen.videoplayer_potrait_height));
            vwPlayerView.getHolder().setFixedSize(displaymetrics.widthPixels, getResources().getDimensionPixelSize(R.dimen.videoplayer_potrait_height));

            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
            params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
            vwPlayerView.setLayoutParams(params1); //causes layout update
        }
        vwPlayerView.invalidate();


    }

    @Override
    public void initObjects() {
        super.initObjects();

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        if (currentFile != null) {
            vwPlayerView.setMediaController(new MediaController(getBaseActivity()));
            vwPlayerView.setVideoURI(Uri.parse(currentFile.getFullPath()));
            vwPlayerView.start();
            vwPlayerView.requestFocus();
            String duration = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(vwPlayerView.getDuration()),
                    TimeUnit.MILLISECONDS.toSeconds(vwPlayerView.getDuration()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(vwPlayerView.getDuration())));
            Log4a.e("Video Duration = ", duration);

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
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            WindowUtil.hideSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displaymetrics.widthPixels, displaymetrics.heightPixels);
            vwPlayerView.getHolder().setFixedSize(displaymetrics.widthPixels, displaymetrics.heightPixels);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            vwPlayerView.setLayoutParams(params); //causes layout update
        } else {
            getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getBaseActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            WindowUtil.showSystemUi(getBaseActivity());

            vwPlayerView.setDimensions(displaymetrics.widthPixels, getResources().getDimensionPixelSize(R.dimen.videoplayer_potrait_height));
            vwPlayerView.getHolder().setFixedSize(displaymetrics.widthPixels, getResources().getDimensionPixelSize(R.dimen.videoplayer_potrait_height));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vwPlayerView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
            vwPlayerView.setLayoutParams(params); //causes layout update
        }
    }

}
