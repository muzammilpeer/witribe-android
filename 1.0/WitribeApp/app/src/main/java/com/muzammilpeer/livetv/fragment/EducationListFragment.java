package com.muzammilpeer.livetv.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.muzammilpeer.livetv.constant.PreferencesKeys;
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
import com.ranisaurus.utilitylayer.view.WindowUtil;
import com.muzammilpeer.livetv.R;
import com.muzammilpeer.livetv.cell.EducationListCell;
import com.muzammilpeer.livetv.manager.UserManager;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/15/15.
 */
public class EducationListFragment extends BaseFragment implements View.OnClickListener {
    // UI references.
    @Bind(R.id.rc_vod_list)
    RecyclerView rcVodList;

    @Bind(R.id.ll_vod_list)
    LinearLayout llVodList;

    @Bind(R.id.pb_vod_list)
    ProgressBar pbVodList;

    @Bind(R.id.srl_vod_list)
    SwipeRefreshLayout srlVodList;

    GeneralBaseAdapter<EducationListCell> dataGeneralBaseAdapter;

    Data selectedVodID;

    public static EducationListFragment newInstance() {
        EducationListFragment fragment = new EducationListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static EducationListFragment newInstance(Data vodID) {
        EducationListFragment fragment = new EducationListFragment();
        Bundle args = new Bundle();
        args.putParcelable("vod_id", vodID);
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
            selectedVodID = getArguments().getParcelable("vod_id");
        }

        super.onCreateView(inflater, R.layout.fragment_vod_list);


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

        if (selectedVodID == null) {
            getBaseActivity().getSupportActionBar().setTitle(R.string.menu_education);
        } else {
            getBaseActivity().getSupportActionBar().setTitle(selectedVodID.title);
        }

    }

    @Override
    public void initObjects() {
        super.initObjects();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log4a.e("onConfigurationChanged", " called");
        rcVodList.setLayoutManager(new GridLayoutManager(getBaseActivity(), PreferencesKeys.getGridColumnCount(getBaseActivity())));
    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();

        dataGeneralBaseAdapter = new GeneralBaseAdapter<EducationListCell>(mContext, R.layout.row_vod_list, EducationListCell.class, this.getLocalDataSource());

        rcVodList.setHasFixedSize(true);
        rcVodList.setLayoutManager(new GridLayoutManager(getBaseActivity(), PreferencesKeys.getGridColumnCount(getBaseActivity())));
        rcVodList.setAdapter(dataGeneralBaseAdapter);
        dataGeneralBaseAdapter.notifyDataSetChanged();


        srlVodList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlVodList.setRefreshing(false);
            }
        });

        srlVodList.setColorSchemeResources(android.R.color.holo_blue_bright,
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

        if (selectedVodID == null) {
            getVODCategoryListData();
        } else {
            if (selectedVodID.id_parent != null && selectedVodID.id_parent.length() > 0 && selectedVodID.id_parent.equalsIgnoreCase("445")) {
                getVODSubCategoryListData(selectedVodID.id);
            } else if (selectedVodID.id_parent != null && selectedVodID.id_parent.length() > 0 && !selectedVodID.id_parent.equalsIgnoreCase("445")) {
                getVODSubCategoryListData(selectedVodID.id, selectedVodID.id_category);
            }
        }
    }

    private void getVODCategoryListData() {
        String[] params = new String[1];
        params[0] = UserManager.getInstance().getUserProfile().getUserId();
        WitribeAMFRequest request = new WitribeAMFRequest(null, NetworkRequestEnum.GET_EDUCATION_MENU_FROM_DB);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }

    }

    private void getVODSubCategoryListData(String someId) {
        String[] params = new String[1];
        params[0] = someId;
//        params[1] = "4";

        WitribeAMFRequest request = new WitribeAMFRequest(params, NetworkRequestEnum.GET_INNER_MENU);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    private void getVODSubCategoryListData(String id, String id_category) {
        String[] params = new String[2];
        params[0] = id;
        params[1] = id_category;

        WitribeAMFRequest request = new WitribeAMFRequest(params, NetworkRequestEnum.GET_RELEVANT_VIDEOS_BY_ID);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    private void getVODSubCategorySpecialListData(String someId) {
        String[] params = new String[1];
        params[0] = someId;
        WitribeAMFRequest request = new WitribeAMFRequest(params, NetworkRequestEnum.GET_ALL_VIDEOS_IN_SPECIFIC_CATEGORY);
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
        pbVodList.setVisibility(View.VISIBLE);
        rcVodList.setVisibility(View.GONE);
        llVodList.setVisibility(View.GONE);
        srlVodList.setVisibility(View.GONE);
        srlVodList.setRefreshing(true);
    }

    @Override
    protected void hideLoader(boolean isError) {
        pbVodList.setVisibility(View.GONE);
        srlVodList.setRefreshing(false);

        if (isError) {
            llVodList.setVisibility(View.VISIBLE);
            rcVodList.setVisibility(View.GONE);
            srlVodList.setVisibility(View.GONE);
        } else {
            llVodList.setVisibility(View.GONE);
            rcVodList.setVisibility(View.VISIBLE);
            srlVodList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        super.responseWithError(error, request);
        try {
            if (mView != null) {
                switch (request.getNetworkRequestEnum()) {
                    case GET_EDUCATION_MENU_FROM_DB: {
                        Log4a.e("Error ", "some error in network");
                        hideLoader(true);
                    }
                    break;
                    case GET_ALL_VIDEOS_IN_SPECIFIC_CATEGORY: {
                        Log4a.e("Error ", "some error in network");
                        hideLoader(true);
                    }
                    break;
                    case GET_INNER_MENU: {
                        Log4a.e("Error ", "some error in network");
                        hideLoader(true);
                    }
                    break;
                    case GET_RELEVANT_VIDEOS_BY_ID: {
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

                    case GET_EDUCATION_MENU_FROM_DB: {
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
                    break;
                    case GET_ALL_VIDEOS_IN_SPECIFIC_CATEGORY: {
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
                    break;
                    case GET_INNER_MENU: {
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
                    break;
                    case GET_RELEVANT_VIDEOS_BY_ID: {
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
                    break;

                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


}
