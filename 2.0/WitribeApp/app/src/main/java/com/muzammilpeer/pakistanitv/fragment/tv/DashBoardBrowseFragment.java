package com.muzammilpeer.pakistanitv.fragment.tv;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.muzammilpeer.pakistanitv.R;
import com.muzammilpeer.pakistanitv.presenter.CardPresenter;
import com.ranisaurus.baselayer.fragment.BaseBrowseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkConfig;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.newtorklayer.protocols.IResponseProtocol;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeGeneralRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by muzammilpeer on 1/2/16.
 */
public class DashBoardBrowseFragment extends BaseBrowseFragment implements IResponseProtocol {
    private static final String TAG = "MainFragment";

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final int GRID_ITEM_WIDTH = 200;
    private static final int GRID_ITEM_HEIGHT = 200;
    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 15;

    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private URI mBackgroundURI;
    private BackgroundManager mBackgroundManager;


    // data sources
    private DataListResponseModel channelsCategory;

    ArrayObjectAdapter rowChannelsAdapter;
    ArrayObjectAdapter rowVODAdapter;
    ArrayObjectAdapter rowEducationAdapter;
    ArrayObjectAdapter rowFavouriteAdapter;
    ArrayObjectAdapter rowRecordedShowsAdapter;
    ArrayObjectAdapter rowSettingsAdapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        NetworkManager.setConfiguration(new NetworkConfig(getActivity()));


        prepareBackgroundManager();

        setupUIElements();

        loadRows();

        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }


    //
    private void loadRows() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();
        GridItemPresenter mGridPresenter = new GridItemPresenter();
        rowChannelsAdapter = new ArrayObjectAdapter(cardPresenter);
        rowVODAdapter = new ArrayObjectAdapter(cardPresenter);
        rowEducationAdapter = new ArrayObjectAdapter(cardPresenter);
        rowFavouriteAdapter = new ArrayObjectAdapter(cardPresenter);
        rowRecordedShowsAdapter = new ArrayObjectAdapter(cardPresenter);
        rowSettingsAdapter = new ArrayObjectAdapter(mGridPresenter);

        rowSettingsAdapter.add(getResources().getString(R.string.settings_change_picture));
        rowSettingsAdapter.add(getResources().getString(R.string.settings_change_password));
        rowSettingsAdapter.add(getResources().getString(R.string.settings_unsubscribe));
        rowSettingsAdapter.add(getResources().getString(R.string.settings_payment));
        rowSettingsAdapter.add(getResources().getString(R.string.settings_options));
        rowSettingsAdapter.add(getResources().getString(R.string.settings_logout));
        rowSettingsAdapter.add(getResources().getString(R.string.settings_request_for_vod));


        getChannelsCategoryRequest();

    }

    private void getChannelsCategoryRequest() {
        if (channelsCategory == null || channelsCategory.getData().size() == 0) {
            Log4a.e("Network Call", "GET_CHANNEL_CATEGORIES");
            WitribeGeneralRequest request = new WitribeGeneralRequest(null, NetworkRequestEnum.GET_CHANNEL_CATEGORIES);
            try {
                NetworkManager.getInstance().executeRequest(request, this);
            } catch (Exception e) {
                Log4a.printException(e);
            }
        }
    }

    private void getEducationCategoryRequest() {
        WitribeGeneralRequest request = new WitribeGeneralRequest(null, NetworkRequestEnum.GET_EDUCATION_MENU_FROM_DB);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }

    }

    private void getVideoOnDemandCategoryRequest() {
        WitribeGeneralRequest request = new WitribeGeneralRequest(null, NetworkRequestEnum.GET_VODS_CATEGORIES);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }

    }

    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
//        super.responseWithError(error, request);
        try {
            if (getView() != null) {
                switch (request.getNetworkRequestEnum()) {
                    case GET_CHANNEL_CATEGORIES: {
                        Log4a.e("Error ", "some error in network");
                        mRowsAdapter.add(new ListRow(new HeaderItem(0, getResources().getString(R.string.view_pager_title_1)), rowChannelsAdapter));

                        getVideoOnDemandCategoryRequest();
                    }
                    break;
                    case GET_VODS_CATEGORIES: {
                        Log4a.e("Error ", "some error in network");
                        mRowsAdapter.add(new ListRow(new HeaderItem(1, getResources().getString(R.string.view_pager_title_2)), rowVODAdapter));
                        getEducationCategoryRequest();
                    }
                    break;
                    case GET_EDUCATION_MENU_FROM_DB: {
                        Log4a.e("Error ", "some error in network");
                        mRowsAdapter.add(new ListRow(new HeaderItem(2, getResources().getString(R.string.view_pager_title_3)), rowEducationAdapter));
                        loadLastRows();

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
//        super.successWithData(data, request);
        try {
            if (getView() != null) {
                switch (request.getNetworkRequestEnum()) {
                    case GET_CHANNEL_CATEGORIES: {
                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
                        Log4a.e("Response GET_CHANNEL_CATEGORIES =", model.toString() + "");

                        channelsCategory = model;
                        for (int i = 0; i < model.getData().size(); i++) {
                            rowChannelsAdapter.add(model.getData().get(i));
                        }

                        mRowsAdapter.add(new ListRow(new HeaderItem(0, getResources().getString(R.string.view_pager_title_1)), rowChannelsAdapter));

                        getVideoOnDemandCategoryRequest();

                    }
                    break;
                    case GET_VODS_CATEGORIES: {
                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
                        Log4a.e("Response VOD = ", model.toString() + "");
                        for (int i = 0; i < model.getData().size(); i++) {
                            rowVODAdapter.add(model.getData().get(i));
                        }

                        mRowsAdapter.add(new ListRow(new HeaderItem(1, getResources().getString(R.string.view_pager_title_2)), rowVODAdapter));

                        getEducationCategoryRequest();

                    }
                    break;
                    case GET_EDUCATION_MENU_FROM_DB: {
                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
                        Log4a.e("Response Education = ", model.toString() + "");

                        for (int i = 0; i < model.getData().size(); i++) {
                            rowEducationAdapter.add(model.getData().get(i));
                        }

                        mRowsAdapter.add(new ListRow(new HeaderItem(2, getResources().getString(R.string.view_pager_title_3)), rowEducationAdapter));
                        loadLastRows();

                    }
                    break;

                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    private void loadLastRows() {
        mRowsAdapter.add(new ListRow(new HeaderItem(3, getResources().getString(R.string.view_pager_title_4)), rowFavouriteAdapter));
        mRowsAdapter.add(new ListRow(new HeaderItem(4, getResources().getString(R.string.view_pager_title_5)), rowRecordedShowsAdapter));
        mRowsAdapter.add(new ListRow(new HeaderItem(5, getResources().getString(R.string.view_pager_title_6)), rowSettingsAdapter));
        setAdapter(mRowsAdapter);
    }


    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = getResources().getDrawable(R.mipmap.ic_launcher);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
//        setBadgeDrawable(getActivity().getResources().getDrawable(
//                R.mipmap.ic_launcher));
        setTitle(getString(R.string.app_name)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }


    private void setupEventListeners() {
        setOnSearchClickedListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement your own in-app search", Toast.LENGTH_LONG)
                        .show();
            }
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    protected void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });
        mBackgroundTimer.cancel();
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }


    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {

//            if (item instanceof Movie) {
//                Movie movie = (Movie) item;
//                Log.d(TAG, "Item: " + item.toString());
//                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                intent.putExtra(DetailsActivity.MOVIE, movie);
//
//                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        getActivity(),
//                        ((ImageCardView) itemViewHolder.view).getMainImageView(),
//                        DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
//                getActivity().startActivity(intent, bundle);
//            } else if (item instanceof String) {
//                if (((String) item).indexOf(getString(R.string.error_fragment)) >= 0) {
//                    Intent intent = new Intent(getActivity(), BrowseErrorActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getActivity(), ((String) item), Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Data) {
                Data movie = (Data) item;

                String imageUrl = movie.image;
                if (imageUrl == null || imageUrl.length() < 1) {
                    imageUrl = movie.mobile_small_image;
                }
                if (imageUrl != null) {
                    try {
                        mBackgroundURI = new URI(imageUrl);
                        startBackgroundTimer();
                    } catch (URISyntaxException e) {
                        Log4a.printException(e);
                    }
                }

            }

        }
    }

    //
    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI.toString());
                    }
                }
            });

        }
    }

    private class GridItemPresenter extends Presenter {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH, GRID_ITEM_HEIGHT));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }
    }

}
