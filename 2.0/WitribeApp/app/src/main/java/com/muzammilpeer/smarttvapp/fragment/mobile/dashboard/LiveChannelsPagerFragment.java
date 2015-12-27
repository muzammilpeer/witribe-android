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
import com.muzammilpeer.smarttvapp.cell.HomePagerCell;
import com.ranisaurus.baselayer.adapter.GeneralBaseAdapter;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeAMFRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class LiveChannelsPagerFragment extends BaseFragment {

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    GeneralBaseAdapter<HomePagerCell> mGeneralBaseAdapter;
    GridLayoutManager mStaggeredLayoutManager;


    private DataListResponseModel channelsCategory;

    public LiveChannelsPagerFragment() {
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

        super.onCreateView(inflater, R.layout.fragment_live_channels_pager);

        return mView;
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initObjects() {
        super.initObjects();
    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();

        mGeneralBaseAdapter = new GeneralBaseAdapter<HomePagerCell>(getBaseActivity(), R.layout.row_home_pager, HomePagerCell.class, getLocalDataSource());
        mGeneralBaseAdapter.setCurrentColumnWidth(getRowWidthAtRunTime());
        mStaggeredLayoutManager = new GridLayoutManager(getBaseActivity(), getResources().getInteger(R.integer.gridColumns));
        mStaggeredLayoutManager.setSpanCount(getResources().getInteger(R.integer.gridColumns));
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);
        mRecyclerView.setAdapter(mGeneralBaseAdapter);

    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();

        if (channelsCategory == null || channelsCategory.getData().size() == 0) {
            Log4a.e("Network Call", "GET_CHANNEL_CATEGORIES");
            showLoader();
            WitribeAMFRequest request = new WitribeAMFRequest(null, NetworkRequestEnum.GET_CHANNEL_CATEGORIES);
            try {
                NetworkManager.getInstance().executeRequest(request, this);
            } catch (Exception e) {
                Log4a.printException(e);
            }
        }
    }

    @Override
    protected void showLoader() {
        super.showLoader();
    }

    @Override
    protected void hideLoader(boolean isError) {
        super.hideLoader(isError);
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


    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        super.responseWithError(error, request);
        try {
            if (mView != null) {
                switch (request.getNetworkRequestEnum()) {
                    case GET_CHANNEL_CATEGORIES: {
                        Log4a.e("Error ", "some error in network");
                        hideLoader(true);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    @Override
    public void successWithData(Object data, BaseNetworkRequest request) {
        super.successWithData(data, request);
        try {
            if (mView != null) {
                switch (request.getNetworkRequestEnum()) {
                    case GET_CHANNEL_CATEGORIES: {
                        hideLoader(false);
                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                        // Create the adapter that will return a fragment for each of the three
                        // primary sections of the activity.
                        // Set up the ViewPager with the sections adapter.
                        channelsCategory = model;

                        getLocalDataSource().addAll(channelsCategory.getData());
                        mGeneralBaseAdapter.notifyDataSetChanged();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


}
