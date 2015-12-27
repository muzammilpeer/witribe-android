package com.muzammilpeer.smarttvapp.fragment.mobile.dashboard;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzammilpeer.smarttvapp.R;
import com.muzammilpeer.smarttvapp.adapter.HomeRecyclerAdapter;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.models.SampleModel;
import com.ranisaurus.newtorklayer.models.SectionSampleModel;
import com.ranisaurus.utilitylayer.logger.Log4a;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class HomePagerFragment extends BaseFragment {

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;


    HomeRecyclerAdapter mGeneralBaseAdapter;
    GridLayoutManager mStaggeredLayoutManager;

    public HomePagerFragment() {
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

        super.onCreateView(inflater, R.layout.fragment_home_pager);

        return mView;
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initObjects() {
        super.initObjects();

        getLocalDataSource().clear();
        getLocalDataSource().add(new SectionSampleModel(getString(R.string.section_top_watched_shows)));
        for (int i = 0; i < 8; i++) {
            getLocalDataSource().add(new SampleModel("Counter = " + i));
        }

        getLocalDataSource().add(new SectionSampleModel(getString(R.string.section_continuoue_watching)));
        for (int i = 0; i < 2; i++) {
            getLocalDataSource().add(new SampleModel("Counter = " + i));
        }

        getLocalDataSource().add(new SectionSampleModel(getString(R.string.section_shows_you_watch)));
        for (int i = 0; i < 2; i++) {
            getLocalDataSource().add(new SampleModel("Counter = " + i));
        }
    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();

        mGeneralBaseAdapter = new HomeRecyclerAdapter(getLocalDataSource());

        mStaggeredLayoutManager = new GridLayoutManager(getBaseActivity(), 1);
        mStaggeredLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mGeneralBaseAdapter.getItemViewType(position)) {
                    case HomeRecyclerAdapter.SECTION:
                        return getResources().getInteger(R.integer.gridColumns);
                    default:
                        return 1;
                }
            }
        });

        mStaggeredLayoutManager.setSpanCount(getResources().getInteger(R.integer.gridColumns));
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        mRecyclerView.setAdapter(mGeneralBaseAdapter);
//        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (isChecked) {
//                    currentSpanSize = 2;
//                    mStaggeredLayoutManager.setSpanCount(currentSpanSize);
//                } else {
//                    currentSpanSize = 1;
//                    mStaggeredLayoutManager.setSpanCount(currentSpanSize);
//                }
//            }
//        });

    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log4a.d("onConfigurationChanged", " called");


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (getLocalDataSource().size() > 0) {
                mStaggeredLayoutManager.setSpanCount(getResources().getInteger(R.integer.gridColumns));
                mGeneralBaseAdapter.setCurrentColumnWidth(getRowWidthAtRunTime());
                mGeneralBaseAdapter.notifyDataSetChanged();
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (getLocalDataSource().size() > 0) {
                mStaggeredLayoutManager.setSpanCount(getResources().getInteger(R.integer.gridColumns));
                mGeneralBaseAdapter.setCurrentColumnWidth(getRowWidthAtRunTime());
                mGeneralBaseAdapter.notifyDataSetChanged();
            }
        }
    }
}
