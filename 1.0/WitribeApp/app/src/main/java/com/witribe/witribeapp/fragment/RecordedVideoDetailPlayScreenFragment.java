package com.witribe.witribeapp.fragment;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;

import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.utilitylayer.file.model.FileInfoModel;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.view.PlayerVideoView;
import com.ranisaurus.utilitylayer.view.WindowUtil;
import com.witribe.witribeapp.R;

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
            currentFile = (FileInfoModel)getArguments().getSerializable("selectedFile");
        }

        super.onCreateView(inflater, R.layout.fragment_recorded_video_detail_playscreen);
        return mView;
    }


    @Override
    public void initViews() {
        super.initViews();

        getBaseActivity().isFullScreenOptionEnable = false;
        getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getBaseActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        WindowUtil.showSystemUi(getBaseActivity());
        getBaseActivity().showToolBar();
        getBaseActivity().getTabLayoutView().setVisibility(View.GONE);


        //app:layout_scrollFlags="scroll|enterAlways"
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) getBaseActivity().getMainToolbar().getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
        getBaseActivity().getMainToolbar().setLayoutParams(params);
        getBaseActivity().getMainToolbar().setVisibility(View.VISIBLE);
        getBaseActivity().getMainToolbar().setCollapsible(false);

        if (currentFile != null)
        {
            getBaseActivity().getSupportActionBar().setTitle(currentFile.getFileName());
        }


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int displayHeight = displaymetrics.heightPixels;
        int displayWidth = displaymetrics.widthPixels;

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

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        if (currentFile != null)
        {
            vwPlayerView.setMediaController(new MediaController(getBaseActivity()));
            vwPlayerView.setVideoURI(Uri.parse(currentFile.getFullPath()));
            vwPlayerView.start();
            vwPlayerView.requestFocus();
            String duration = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(vwPlayerView.getDuration()),
                    TimeUnit.MILLISECONDS.toSeconds(vwPlayerView.getDuration()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(vwPlayerView.getDuration())));
            Log4a.e("Video Duration = ",duration);

        }



    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();


    }

}
