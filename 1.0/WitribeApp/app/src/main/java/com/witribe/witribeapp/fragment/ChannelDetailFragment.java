package com.witribe.witribeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ranisaurus.baselayer.adapter.GeneralBaseAdapter;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.ChannelScheduleRequestModel;
import com.ranisaurus.newtorklayer.models.ChannelScheduleResponseModel;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.TVScheduleRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.ranisaurus.utilitylayer.view.WindowUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.cell.RelatedChannelCell;
import com.witribe.witribeapp.manager.UserManager;

import java.util.Collections;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/17/15.
 */
public class ChannelDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_CATEGORY_NAME = "category_name";
    private static final String ARG_SELECTED_DATA = "selected_data";
    //     UI references.
    @Bind(R.id.rc_related_channels)
    RecyclerView rcRelatedChannels;

    @Bind(R.id.iv_channel)
    ImageView ivChannel;

    @Bind(R.id.rl_channel)
    RelativeLayout rlChannel;


    @Bind(R.id.pb_channel)
    ProgressBar pbChannel;


    @Bind(R.id.tv_channel)
    TextView tvChannelsDescription;

    @Bind(R.id.tv_viewer_count)
    TextView tvViewersCount;

    GeneralBaseAdapter<RelatedChannelCell> dataGeneralBaseAdapter;

    private DataListResponseModel currentData;
    private Data selectedData;
//    private StaggeredGridLayoutManager gaggeredGridLayoutManager;


    public static ChannelDetailFragment newInstance(DataListResponseModel filterList, Data sData) {
        ChannelDetailFragment fragment = new ChannelDetailFragment();
        Bundle args = new Bundle();

        Log4a.e("newInstance = ", " channels = " + filterList.getData().size() + " ,instance = " + sData);

        args.putParcelable(ARG_CATEGORY_NAME, filterList);
        args.putParcelable(ARG_SELECTED_DATA, sData);

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
                selectedData = getArguments().getParcelable(ARG_SELECTED_DATA);
            } catch (Exception e) {
                Log4a.printException(e);
            }
        }

        super.onCreateView(inflater, R.layout.fragment_channel_detail);

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
    }

    @Override
    public void initObjects() {
        super.initObjects();

        getLocalDataSource().clear();
        if (currentData != null) {

//            if (currentData.getData().size() > 8) {
//                Collections.shuffle(currentData.getData());
//                getLocalDataSource().addAll(currentData.getData().subList(0, 8));
//            } else {
//                getLocalDataSource().addAll(currentData.getData());
//            }

            getLocalDataSource().addAll(currentData.getData());
            Collections.shuffle(getLocalDataSource());

        }
    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        dataGeneralBaseAdapter = new GeneralBaseAdapter<RelatedChannelCell>(mContext, R.layout.row_related_channel, RelatedChannelCell.class, this.getLocalDataSource());
        rcRelatedChannels.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcRelatedChannels.setLayoutManager(layoutManager);

        rcRelatedChannels.setAdapter(dataGeneralBaseAdapter);

        dataGeneralBaseAdapter.notifyDataSetChanged();

        rlChannel.setOnClickListener(this);


        final String imageUrl = ("http://pitelevision.com/" + selectedData.mobile_large_image).replaceAll(" ", "%20");

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int displayWidth = displaymetrics.widthPixels;
        Picasso.with(ivChannel.getContext())
                .load(imageUrl)
                .resize(displayWidth, (int) getResources().getDimension(R.dimen.preview_image_height))
                .centerInside()
                .into(ivChannel, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (pbChannel != null)
                        {
                            pbChannel.setVisibility(View.GONE);
                            ivChannel.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError() {
                        if (pbChannel != null)
                        {
                            pbChannel.setVisibility(View.GONE);
                            ivChannel.setVisibility(View.VISIBLE);
                        }
                    }
                });

        if (selectedData.description != null && selectedData.description.length() > 0) {
            tvChannelsDescription.setText(selectedData.description);
        } else {
            tvChannelsDescription.setText(getString(R.string.no_content_available));
        }
        tvViewersCount.setText(selectedData.totalViews);

        getBaseActivity().getSupportActionBar().setTitle(selectedData.title);
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();


        Log4a.e("Network Call", "GET_CHANNEL_SCHEDULE");
        showLoader();
        ChannelScheduleRequestModel requestModel = new ChannelScheduleRequestModel();
        requestModel.setUserid("0");
        requestModel.setChannellist(selectedData.title.toLowerCase());
        requestModel.setFromdatetime("201511020000");
        requestModel.setTodatetime("201511030000");
        requestModel.setDeviceview("other");
        requestModel.setChannellogo("0");

        Log4a.e("Channel Name = ", requestModel.getChannellist());

        TVScheduleRequest request = new TVScheduleRequest(requestModel, NetworkRequestEnum.GET_CHANNEL_SCHEDULE);
        try {
            NetworkManager.getInstance().executeRequest(request, this);
        } catch (Exception e) {
            Log4a.printException(e);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_channel: {
                if (UserManager.getInstance().isUserLoggedIn()) {
                    WebViewFragment fragment = WebViewFragment.newInstance(selectedData);
                    getBaseActivity().replaceFragment(fragment, R.id.container_main);
                } else {
                    Snackbar.make(v, R.string.unauthorized_access, Snackbar.LENGTH_SHORT).show();
                }
            }
            break;
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
    public void onDestroyView() {
        super.onDestroyView();

        Log4a.e("On Destroy View", "IN DETAIL FRAGMENT");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        NetworkManager.setConfiguration(new NetworkConfig(null));
//        getLocalDataSource().clear();
//        if (dataGeneralBaseAdapter != null)
//        {
//            dataGeneralBaseAdapter.notifyDataSetChanged();
//        }
//        setLocalDataSource(null);
//        System.gc();
        Log4a.e("onDestroy", "IN DETAIL FRAGMENT");
    }




    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        super.responseWithError(error, request);
        try {
            if (mView != null) {
                switch (request.getNetworkRequestEnum()) {
                    case GET_CHANNEL_SCHEDULE: {
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

                    case GET_CHANNEL_SCHEDULE: {
                        ChannelScheduleResponseModel model = (ChannelScheduleResponseModel) GsonUtil.getObjectFromJsonObject(data, ChannelScheduleResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                        hideLoader(false);
                    }
                }
            }
        } catch (Exception e)
        {
            Log4a.printException(e);
        }
    }

}