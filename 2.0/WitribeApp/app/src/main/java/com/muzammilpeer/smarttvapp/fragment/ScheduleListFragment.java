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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ranisaurus.baselayer.adapter.GeneralBaseAdapter;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.ChannelScheduleRequestModel;
import com.ranisaurus.newtorklayer.models.ChannelScheduleResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.TVScheduleRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.ranisaurus.utilitylayer.view.WindowUtil;
import com.muzammilpeer.smarttvapp.R;
import com.muzammilpeer.smarttvapp.cell.ScheduleListCell;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/12/15.
 */
public class ScheduleListFragment extends BaseFragment implements View.OnClickListener {
    // UI references.
    @Bind(R.id.rc_schedule_lis)
    RecyclerView rcRecordVideoList;

    @Bind(R.id.ll_schedule_lis)
    LinearLayout llRecordVideoList;

    @Bind(R.id.pb_schedule_list)
    ProgressBar pbRecordVideoList;

    @Bind(R.id.srl_schedule_list)
    SwipeRefreshLayout srlScheduleList;

    @Bind(R.id.iv_next_date)
    ImageButton ivNextDate;

    @Bind(R.id.iv_previous_date)
    ImageButton ivPreviousDate;

    @Bind(R.id.tv_current_date)
    TextView tvCurrentDate;

    Date currentDate;
    Calendar cal = Calendar.getInstance();



    String selectedChannelName;

    GeneralBaseAdapter<ScheduleListCell> dataGeneralBaseAdapter;

    public static ScheduleListFragment newInstance(String channelName) {
        ScheduleListFragment fragment = new ScheduleListFragment();
        Bundle args = new Bundle();
        args.putString("channel_name", channelName);
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
            selectedChannelName = (String) getArguments().getString("channel_name");
        }

        super.onCreateView(inflater, R.layout.fragment_schedule_list);


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

        getBaseActivity().getSupportActionBar().setTitle(selectedChannelName + "'s Today");
    }

    @Override
    public void initObjects() {
        super.initObjects();

        currentDate = new Date();
        cal.setTime(currentDate);
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        ivNextDate.setOnClickListener(this);
        ivPreviousDate.setOnClickListener(this);

        dataGeneralBaseAdapter = new GeneralBaseAdapter<ScheduleListCell>(mContext, R.layout.row_schedule_list, ScheduleListCell.class, this.getLocalDataSource());

        rcRecordVideoList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcRecordVideoList.setLayoutManager(layoutManager);
        rcRecordVideoList.setAdapter(dataGeneralBaseAdapter);
        dataGeneralBaseAdapter.notifyDataSetChanged();


        srlScheduleList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getScheduleData(-1);

            }
        });

        srlScheduleList.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();


        getScheduleData(-1);
    }

    private void getScheduleData(int isAddDay) {

        if (selectedChannelName != null && selectedChannelName.length() > 0) {
            Log4a.e("Network Call", "GET_CHANNEL_SCHEDULE");
            showLoader();
            ChannelScheduleRequestModel requestModel = new ChannelScheduleRequestModel();
            requestModel.setUserid("0");
            requestModel.setChannellist(selectedChannelName.toLowerCase().replaceAll(" ", "%20"));

            if (isAddDay == 0) //next day
            {
                cal.add(Calendar.DAY_OF_MONTH,1);
            }else if (isAddDay == 1) { //PREVIOUS DAY
                cal.add(Calendar.DAY_OF_MONTH,-1);
            } else {// -1 for current date
                cal.setTime(currentDate);
            }

            String day = cal.get(Calendar.DAY_OF_MONTH) + "";
            String month = cal.get(Calendar.MONTH) + 1 + "";
            String year = cal.get(Calendar.YEAR) + "";
            String hour = cal.get(Calendar.HOUR_OF_DAY) + "";
            String minute = cal.get(Calendar.MINUTE) + "";


            String startFullDateTime = "";
            String endFullDateTime = "";

            if (isAddDay == 0) //next day
            {
                startFullDateTime = year
                        + (cal.get(Calendar.MONTH) < 9 ? "0" + month : month)
                        + (cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + day : day)
                        + "00"
                        + "00";

            }else if (isAddDay == 1) { //PREVIOUS DAY
                startFullDateTime = year
                        + (cal.get(Calendar.MONTH) < 9 ? "0" + month : month)
                        + (cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + day : day)
                        + "00"
                        + "00";
            }else { // -1 for current date
                startFullDateTime = year
                        + (cal.get(Calendar.MONTH) < 9 ? "0" + month : month)
                        + (cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + day : day)
                        + (cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + hour : hour)
                        + (cal.get(Calendar.MINUTE) < 10 ? "0" + minute : minute);


            }


            endFullDateTime = year
                    + (cal.get(Calendar.MONTH) < 9 ? "0" + month : month)
                    + (cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + day : day)
                    + "23"
                    + "59";


            tvCurrentDate.setText(year + "-"
                    + (cal.get(Calendar.MONTH) < 9 ? "0" + month : month) + "-"
                    + (cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + day : day));

            requestModel.setFromdatetime(startFullDateTime);
            requestModel.setTodatetime(endFullDateTime);

            requestModel.setDeviceview("other");
            requestModel.setChannellogo("0");

            Log4a.e("Channel Name = ", requestModel.getChannellist());

            TVScheduleRequest request = new TVScheduleRequest(requestModel, NetworkRequestEnum.GET_CHANNEL_SCHEDULE);
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_next_date: {
                getScheduleData(0);

            }break;
            case R.id.iv_previous_date: {
                getScheduleData(1);

            }break;

        }

    }

    @Override
    protected void showLoader() {
        pbRecordVideoList.setVisibility(View.VISIBLE);
        rcRecordVideoList.setVisibility(View.GONE);
        llRecordVideoList.setVisibility(View.GONE);
        srlScheduleList.setVisibility(View.GONE);
        srlScheduleList.setRefreshing(true);
    }

    @Override
    protected void hideLoader(boolean isError) {
        pbRecordVideoList.setVisibility(View.GONE);
        srlScheduleList.setRefreshing(false);

        if (isError) {
            llRecordVideoList.setVisibility(View.VISIBLE);
            rcRecordVideoList.setVisibility(View.GONE);
            srlScheduleList.setVisibility(View.GONE);
        } else {
            llRecordVideoList.setVisibility(View.GONE);
            rcRecordVideoList.setVisibility(View.VISIBLE);
            srlScheduleList.setVisibility(View.VISIBLE);
        }
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
                        getLocalDataSource().clear();
                        if (model.getScheduleGrid().getChannel().get(0).getProgramme().size() < 1) {
                            hideLoader(true);
                        } else {
                            getLocalDataSource().addAll(model.getScheduleGrid().getChannel().get(0).getProgramme());
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
