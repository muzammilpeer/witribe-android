package com.witribe.witribeapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.DataBooleanResponseModel;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.newtorklayer.models.DataSingleResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeAMFRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.witribe.witribeapp.adapter.ChannelsCategoryViewPagaerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    @Bind(R.id.container_viewpager)
    ViewPager mViewPager;
    private ChannelsCategoryViewPagaerAdapter mSectionsPagerAdapter;
    private int selectedTabIndex;

    private Uri imageUri;
    private String selectedImagePath = "";

    private List subCategoriesDataSource = new ArrayList();

    private DataListResponseModel channels;
    private DataListResponseModel channelsCategory;

    public MainActivityFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        Log4a.e("onCreate", "MainActivityFragment");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log4a.e("onResume", "MainActivityFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null)
        {
            channels = savedInstanceState.getParcelable("channels");
            channelsCategory = savedInstanceState.getParcelable("channelsCategory");
        }



        super.onCreateView(inflater, R.layout.fragment_main);

        getBaseActivity().getTabLayoutView().setVisibility(View.VISIBLE);

        Log4a.e("onCreateView", "MainActivityFragment");

        return mView;
    }

    @Override
    public void initViews() {
        super.initViews();
        mSectionsPagerAdapter = new ChannelsCategoryViewPagaerAdapter(getChildFragmentManager(), subCategoriesDataSource, getLocalDataSource());
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }

    @Override
    public void initObjects() {
        super.initObjects();

        if (channelsCategory != null) {
            Log4a.e("initObjects savedInstanceState != null", "channelsCategory = " + channelsCategory.getData().size());
            subCategoriesDataSource.clear();
            subCategoriesDataSource.addAll(channelsCategory.getData());
        }

        if (channels != null) {
            Log4a.e("initObjects savedInstanceState != null", "channels = " + channels.getData().size());
            getLocalDataSource().clear();
            getLocalDataSource().addAll(channels.getData());
        }


    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();

        getBaseActivity().getTabLayoutView().setupWithViewPager(mViewPager);

        getBaseActivity().getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();

        if (channels != null)
        {
            Log4a.e("Network Call", "channels = " + channels.getData().size());
        }
        if (channelsCategory != null) {
            Log4a.e("Network Call", "channelsCategory = " + channelsCategory.getData().size());
        }


        if (channelsCategory == null || channelsCategory.getData().size() == 0)
        {
            Log4a.e("Network Call", "GET_CHANNEL_CATEGORIES");
            WitribeAMFRequest request = new WitribeAMFRequest(null, NetworkRequestEnum.GET_CHANNEL_CATEGORIES);
            try {
                NetworkManager.getInstance().executeRequest(request, this);
            } catch (Exception e) {
                Log4a.printException(e);
            }
        }

    }

    private void getChannelsDataRequest() {
        Log4a.e("Network Call ", "GET_CHANNELS");
        if (channels != null) {
            Log4a.e("onSaveInstanceState", "channels = " + channels.getData().size());
        }

        String[] params = new String[2];
        WitribeAMFRequest request = new WitribeAMFRequest(null, NetworkRequestEnum.GET_CHANNELS);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (channels != null)
        {
            Log4a.e("onSaveInstanceState", "channels = " + channels.getData().size());
            outState.putParcelable("channels", channels);
        }

        if (channelsCategory != null)
        {
            Log4a.e("onSaveInstanceState", "channelsCategory = " + channelsCategory.getData().size());
            outState.putParcelable("channelsCategory",channelsCategory);
        }

        if (imageUri == null) {
            outState.putString("file-uri", "");
        } else {
            outState.putString("file-uri", imageUri.toString());
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log4a.e("onConfigurationChanged", " called");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (subCategoriesDataSource.size() > 0) {
                mSectionsPagerAdapter.notifyDataSetChanged();
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (subCategoriesDataSource.size() > 0) {
                mSectionsPagerAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getBaseActivity().RESULT_OK) {
            if (requestCode == 1) {
                String fileName = "";
                try {
                    selectedImagePath = imageUri.getPath().toString();
                    fileName = selectedImagePath.substring(
                            selectedImagePath.lastIndexOf('/') + 1,
                            selectedImagePath.length());

                    Log4a.d("Capture Image Name = ", fileName);

                } catch (Exception e) {
                    Log4a.printException(e);
                }
            }
        } else if (resultCode == getBaseActivity().RESULT_CANCELED) {
            Intent returnIntent = new Intent();
            getBaseActivity().setResult(getBaseActivity().RESULT_CANCELED, returnIntent);
            getBaseActivity().finish();
        }
    }

    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        super.responseWithError(error, request);
        try {
            if (mView != null) {
                switch (request.getNetworkRequestEnum()) {
                    case LOGIN_WITRIBE_USER: {
                        Log4a.e("Error ", "some error in network");
                    }
                    break;
                    case GET_CHANNELS: {
                        Log4a.e("Error ", "some error in network");
                    }break;
                    case GET_CHANNEL_CATEGORIES: {
                        Log4a.e("Error ", "some error in network");
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

                    case LOGIN_WITRIBE_USER: {
                        DataSingleResponseModel model = (DataSingleResponseModel) GsonUtil.getObjectFromJsonObject(data, DataSingleResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                    }
                    break;
                    case GET_CHANNEL_CATEGORIES: {
                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                        // Create the adapter that will return a fragment for each of the three
                        // primary sections of the activity.
                        // Set up the ViewPager with the sections adapter.
                        channelsCategory = model;
                        //get all channels
                        getChannelsDataRequest();
                    }
                    break;
                    case ADD_FAVOURITE_LISTING: {
                        DataBooleanResponseModel model = (DataBooleanResponseModel) GsonUtil.getObjectFromJsonObject(data, DataBooleanResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");


                    }
                    break;

                    case GET_CHANNELS: {
                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                        channels = model;

                        subCategoriesDataSource.clear();
                        subCategoriesDataSource.addAll(channelsCategory.getData());

                        this.getLocalDataSource().clear();
                        this.getLocalDataSource().addAll(channels.getData());

                        mSectionsPagerAdapter.notifyDataSetChanged();
                        getBaseActivity().getTabLayoutView().setupWithViewPager(mViewPager);
                    }
                    break;

                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }
    }


}
