package com.muzammilpeer.smarttvapp.fragment.mobile;

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
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeAMFRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 12/27/15.
 */
public class FirstLevelNavigationFragment extends BaseFragment {

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    GeneralBaseAdapter<HomePagerCell> mGeneralBaseAdapter;
    GridLayoutManager mStaggeredLayoutManager;
    private Data currentModel;
    private static final String ARG_SELECTED_DATA = "ARG_SELECTED_DATA";
    private DataListResponseModel channels;

    public FirstLevelNavigationFragment() {
    }

    public static FirstLevelNavigationFragment newInstance(Data selectedModel) {
        FirstLevelNavigationFragment fragment = new FirstLevelNavigationFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SELECTED_DATA, selectedModel);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            try {
                currentModel = getArguments().getParcelable(ARG_SELECTED_DATA);
                if (currentModel != null) {
                    hideLoader(false);
                } else {
                    hideLoader(true);
                }
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

        getBaseActivity().showToolBar();
        getBaseActivity().getTabLayoutView().setVisibility(View.GONE);
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

        if (currentModel != null) {
            showLoader();
            String[] params = new String[2];
            WitribeAMFRequest request = new WitribeAMFRequest(null, NetworkRequestEnum.GET_CHANNELS);
            try {
                NetworkManager.getInstance().executeRequest(request, this);

            } catch (Exception e) {
                Log4a.printException(e);
            }
        } else {
            hideLoader(true);
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
                    case GET_CHANNELS: {
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
                    case GET_CHANNELS: {
                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                        channels = model;
                        this.getLocalDataSource().clear();
                        this.getLocalDataSource().addAll(filterChannel(currentModel.displayTitle.toLowerCase(), channels.getData()));
                        mGeneralBaseAdapter.notifyDataSetChanged();
                        hideLoader(false);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


    private ArrayList<Data> filterChannel(String channelName, List<Data> records) {
        ArrayList<Data> dataList = new ArrayList<Data>();
        if (channelName.equalsIgnoreCase("all")) {
            dataList.addAll(records);
        } else if (channelName.equalsIgnoreCase("international")) {
            for (Data record : records) {
                if (record != null) {
                    if (record.packageId.equalsIgnoreCase("6")) {
                        dataList.add(record);
                    }
                }
            }
        } else {
            for (Data record : records) {
                if (record != null) {
                    if (record.category != null) {
                        if (record.category.equalsIgnoreCase(channelName) == true) {
                            dataList.add(record);
                        }
                    }
                }
            }
        }
        return dataList;
    }


}