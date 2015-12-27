package com.muzammilpeer.smarttvapp.fragment.mobile.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzammilpeer.smarttvapp.R;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.utilitylayer.logger.Log4a;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class VideoOnDemandPagerFragment extends BaseFragment {
    public VideoOnDemandPagerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            try {
            } catch (Exception e) {
                Log4a.printException(e);
            }
        }

        super.onCreateView(inflater, R.layout.fragment_video_on_demand_pager);

        return mView;
    }

}
