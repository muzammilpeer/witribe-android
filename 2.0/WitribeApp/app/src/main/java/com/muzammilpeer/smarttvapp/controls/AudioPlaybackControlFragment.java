package com.muzammilpeer.smarttvapp.controls;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzammilpeer.smarttvapp.R;
import com.ranisaurus.baselayer.fragment.BaseFragment;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class AudioPlaybackControlFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
        }

        super.onCreateView(inflater, R.layout.fragment_audio_playback_control);

        return mView;
    }
}