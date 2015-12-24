package com.muzammilpeer.smarttvapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.muzammilpeer.smarttvapp.cell.FavouriteListCell;
import com.muzammilpeer.smarttvapp.manager.UserManager;
import com.ranisaurus.baselayer.adapter.GeneralBaseAdapter;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeAMFRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.ranisaurus.utilitylayer.view.WindowUtil;
import com.muzammilpeer.smarttvapp.R;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/14/15.
 */
public class FavouriteListFragment extends BaseFragment implements View.OnClickListener {
    // UI references.
    @Bind(R.id.rc_favourite_list)
    RecyclerView rcFavouriteList;

    @Bind(R.id.ll_favourite_list)
    LinearLayout llFavouriteList;

    @Bind(R.id.pb_favourite_list)
    ProgressBar pbFavouriteList;

    @Bind(R.id.srl_favourite_list)
    SwipeRefreshLayout srlFavouriteList;

    GeneralBaseAdapter<FavouriteListCell> dataGeneralBaseAdapter;

    public static FavouriteListFragment newInstance() {
        FavouriteListFragment fragment = new FavouriteListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
        }

        super.onCreateView(inflater, R.layout.fragment_favourite_list);


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
//        params.setScrollFlags(0);
        getBaseActivity().getMainToolbar().setLayoutParams(params);
        getBaseActivity().getMainToolbar().setVisibility(View.VISIBLE);
        getBaseActivity().getMainToolbar().setCollapsible(false);

        getBaseActivity().getSupportActionBar().setTitle(R.string.menu_my_favourite);
    }

    @Override
    public void initObjects() {
        super.initObjects();

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        dataGeneralBaseAdapter = new GeneralBaseAdapter<FavouriteListCell>(mContext, R.layout.row_favourite_list, FavouriteListCell.class, this.getLocalDataSource());

        rcFavouriteList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcFavouriteList.setLayoutManager(layoutManager);
        rcFavouriteList.setAdapter(dataGeneralBaseAdapter);
        dataGeneralBaseAdapter.notifyDataSetChanged();


        srlFavouriteList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFavouritesData();

            }
        });

        srlFavouriteList.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if (this.getLocalDataSource().size() > 0) {
            hideLoader(false);
            dataGeneralBaseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();


        getFavouritesData();
    }

    private void getFavouritesData() {
        String[] params = new String[1];
        params[0] = UserManager.getInstance().getUserProfile().getUserId();

        WitribeAMFRequest request = new WitribeAMFRequest(params, NetworkRequestEnum.GET_FAVOURITE_LISTING);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_next_date: {

            }
            break;
        }

    }

    @Override
    protected void showLoader() {
        pbFavouriteList.setVisibility(View.VISIBLE);
        rcFavouriteList.setVisibility(View.GONE);
        llFavouriteList.setVisibility(View.GONE);
        srlFavouriteList.setVisibility(View.GONE);
        srlFavouriteList.setRefreshing(true);
    }

    @Override
    protected void hideLoader(boolean isError) {
        pbFavouriteList.setVisibility(View.GONE);
        srlFavouriteList.setRefreshing(false);

        if (isError) {
            llFavouriteList.setVisibility(View.VISIBLE);
            rcFavouriteList.setVisibility(View.GONE);
            srlFavouriteList.setVisibility(View.GONE);
        } else {
            llFavouriteList.setVisibility(View.GONE);
            rcFavouriteList.setVisibility(View.VISIBLE);
            srlFavouriteList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        super.responseWithError(error, request);
        try {
            if (mView != null) {
                switch (request.getNetworkRequestEnum()) {
                    case GET_FAVOURITE_LISTING: {
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

                    case GET_FAVOURITE_LISTING: {
                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");

                        getLocalDataSource().clear();
                        if (model.getData().size() < 1) {
                            hideLoader(true);
                        } else {
                            getLocalDataSource().addAll(model.getData());
                            hideLoader(false);
                        }

                        dataGeneralBaseAdapter.notifyDataSetChanged();
                    }
                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


}
