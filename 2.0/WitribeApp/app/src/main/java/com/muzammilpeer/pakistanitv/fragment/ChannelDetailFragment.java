package com.muzammilpeer.pakistanitv.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muzammilpeer.pakistanitv.R;
import com.muzammilpeer.pakistanitv.cell.RelatedChannelCell;
import com.muzammilpeer.pakistanitv.manager.UserManager;
import com.ranisaurus.baselayer.adapter.GeneralBaseAdapter;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.ChannelScheduleResponseModel;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataBooleanResponseModel;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeGeneralRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.ranisaurus.utilitylayer.view.CGSize;
import com.ranisaurus.utilitylayer.view.ImageUtil;
import com.ranisaurus.utilitylayer.view.WindowUtil;

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

    @Bind(R.id.fab_schedule)
    FloatingActionButton fabSchedule;

    @Bind(R.id.fab_favourite_detail)
    FloatingActionButton fabFavourite;

    @Bind(R.id.tv_label_viewers)
    TextView tvLabelViewers;

    GeneralBaseAdapter<RelatedChannelCell> dataGeneralBaseAdapter;

    private DataListResponseModel currentData;

    private String currentVideoID = "";
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

        fabSchedule.setOnClickListener(this);
        fabFavourite.setOnClickListener(this);


        dataGeneralBaseAdapter = new GeneralBaseAdapter<RelatedChannelCell>(mContext, R.layout.row_related_channel, RelatedChannelCell.class, this.getLocalDataSource());
        rcRelatedChannels.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcRelatedChannels.setLayoutManager(layoutManager);

        rcRelatedChannels.setAdapter(dataGeneralBaseAdapter);

        dataGeneralBaseAdapter.notifyDataSetChanged();

        rlChannel.setOnClickListener(this);

        if (selectedData.id != null && selectedData.id.length() > 0) {
            currentVideoID = selectedData.id;
        } else if (selectedData.vodId != null && selectedData.vodId.length() > 0) {
            currentVideoID = selectedData.vodId;
        } else if (selectedData.vodCategoryId != null && selectedData.vodCategoryId.length() > 0) {
            currentVideoID = selectedData.vodCategoryId;
        }


        String imageUrl = "";
        if (selectedData.mob_large != null && selectedData.mob_large.length() > 0) {
            imageUrl = (selectedData.mob_large).replaceAll(" ", "%20");
            fabSchedule.setVisibility(View.GONE);
            tvLabelViewers.setVisibility(View.GONE);
            tvViewersCount.setVisibility(View.GONE);
        } else if (selectedData.mob_small != null && selectedData.mob_small.length() > 0) {
            if (selectedData.mob_small.contains("http")) {
                imageUrl = (selectedData.mob_small).replaceAll(" ", "%20");
            } else {
                imageUrl = ("http://piteach.com/iptv/uploads/images/" + selectedData.img_poster).replaceAll(" ", "%20");
            }
            fabSchedule.setVisibility(View.GONE);
            tvLabelViewers.setVisibility(View.GONE);
            tvViewersCount.setVisibility(View.GONE);
        } else {
            imageUrl = ("http://pitelevision.com/" + selectedData.mobile_large_image).replaceAll(" ", "%20");
            fabSchedule.setVisibility(View.VISIBLE);
            tvLabelViewers.setVisibility(View.VISIBLE);
            tvViewersCount.setVisibility(View.VISIBLE);

        }

        CGSize displaySize = WindowUtil.getScreenSizeInPixel(getBaseActivity());


        String localSaveImageUrl = ImageUtil.saveImageFromUrl(CGSize.make(displaySize.WIDTH, (int) getResources().getDimension(R.dimen.preview_image_height)),
                ivChannel, pbChannel, imageUrl
        );

        ImageUtil.getImageFromUrl(CGSize.make(displaySize.WIDTH, (int) getResources().getDimension(R.dimen.preview_image_height)),
                ivChannel, pbChannel, imageUrl
        );

        UserManager.getInstance().setThumbnailImageUrl(localSaveImageUrl);


        if (selectedData.description != null && selectedData.description.length() > 0) {
            tvChannelsDescription.setText(selectedData.description);
        } else {
            tvChannelsDescription.setText(getString(R.string.no_content_available));
        }
        tvViewersCount.setText(selectedData.totalViews);

        getBaseActivity().getSupportActionBar().setTitle(selectedData.title);
        UserManager.getInstance().setStreamingTitle(selectedData.title);
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_channel: {
                if (UserManager.getInstance().isUserLoggedIn()) {
                    WebViewFragment fragment = WebViewFragment.newInstance(selectedData);
                    getBaseActivity().replaceFragment(MainActivityFragment.newInstance(),fragment, R.id.container_main,R.id.iv_channel,R.transition.change_image_transform,"shared_element_transition_1","transaction_1");
                } else {
                    Snackbar.make(v, R.string.unauthorized_access, Snackbar.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.fab_schedule: {
                getBaseActivity().replaceFragment(MainActivityFragment.newInstance(),ScheduleListFragment.newInstance(selectedData.title), R.id.container_main,R.id.fab_schedule,R.transition.change_image_transform,"shared_element_transition","transition_schedule_list");
            }
            break;
            case R.id.fab_favourite_detail: {
                addFavouritesData(currentVideoID);
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
    public void onDestroyView() {
        super.onDestroyView();

        Log4a.e("On Destroy View", "IN DETAIL FRAGMENT");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log4a.e("onDestroy", "IN DETAIL FRAGMENT");
    }

    private void addFavouritesData(String selectedID) {
        String[] params = new String[3];
        params[0] = UserManager.getInstance().getUserProfile().getUserId();
        params[1] = selectedID;

        if (selectedData.vodId != null && selectedData.vodId.length() > 0) {
            params[2] = "2";
        } else {
            params[2] = "1";
        }

        WitribeGeneralRequest request = new WitribeGeneralRequest(params, NetworkRequestEnum.ADD_FAVOURITE_LISTING);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
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
                    case ADD_FAVOURITE_LISTING: {
                        Snackbar.make(mView, "Error in favourite", Snackbar.LENGTH_SHORT).show();
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
                    break;
                    case ADD_FAVOURITE_LISTING: {
                        DataBooleanResponseModel model = (DataBooleanResponseModel) GsonUtil.getObjectFromJsonObject(data, DataBooleanResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                        if (model.getData().equalsIgnoreCase("true")) {
                            Snackbar.make(mView, "Successfully Favourite added", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(mView, "Error in favourite", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

}