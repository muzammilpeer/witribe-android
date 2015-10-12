package com.witribe.witribeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataBooleanResponseModel;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.newtorklayer.models.DataSingleResponseModel;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeAMFRequest;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.witribe.witribeapp.fragment.ListSubCategoriesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    @Bind(R.id.container_viewpager)
    ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int selectedTabIndex;


    private Uri imageUri;
    private String selectedImagePath = "";

    private List subCategoriesDataSource = new ArrayList();


    public MainActivityFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, R.layout.fragment_main);

        getBaseActivity().getTabLayoutView().setVisibility(View.VISIBLE);

        return mView;
    }

    @Override
    public void initViews() {
        super.initViews();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), subCategoriesDataSource);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    @Override
    public void initObjects() {
        super.initObjects();

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();

        getBaseActivity().getTabLayoutView().setupWithViewPager(mViewPager);
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();


//        String[] params = new String[2];
//        params[0] = "muzammilpeer98744";
//        params[1] = "pa5is8an";
//
//        WitribeAMFRequest request = new WitribeAMFRequest(params,NetworkRequestEnum.LOGIN_WITRIBE_USER);
//        try {
//            NetworkManager.getInstance().executeRequest(request,this);
//
//        }catch (Exception e)
//        {
//            Log4a.printException(e);
//        }


        WitribeAMFRequest request = new WitribeAMFRequest(null, NetworkRequestEnum.GET_CHANNEL_CATEGORIES);
        try {
            NetworkManager.getInstance().executeRequest(request, this);
        } catch (Exception e) {
            Log4a.printException(e);
        }

        String[] params = new String[3];
        params[0] = "39056";
        params[1] = "13";
        params[2] = "1";

        //let's suppose we have image testing thing
//        try {
//
//
//            Bitmap image = getCaptureCameraPictureBitmap(4);
//            String filePath = getCaptureCameraPictureFilePath();
//        } catch (Exception e) {
//            Log4a.printException(e);
//        }

//        WitribeAMFRequest request = new WitribeAMFRequest(params, NetworkRequestEnum.ADD_FAVOURITE_LISTING);
//        try {
//            NetworkManager.getInstance().executeRequest(request, this);
//
//        } catch (Exception e) {
//            Log4a.printException(e);
//        }

    }

    private void getChannelsDataRequest() {
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

        if (imageUri == null) {
            outState.putString("file-uri", "");
        } else {
            outState.putString("file-uri", imageUri.toString());
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
                        subCategoriesDataSource.clear();
                        subCategoriesDataSource.addAll(model.getData());

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
                        this.getLocalDataSource().clear();
                        this.getLocalDataSource().addAll(model.getData());

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

    private DataListResponseModel filterChannel(String channelName, List<Data> records) {
        DataListResponseModel result = new DataListResponseModel();
        ArrayList<Data> dataList = new ArrayList<Data>();

        if (channelName.equalsIgnoreCase("all")) {
            dataList.addAll(records);
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
        result.setData(dataList);
        return result;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(Data data) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, data.displayTitle);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getString(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Data> mDataSource;


        public SectionsPagerAdapter(FragmentManager fm, List dataSource) {
            super(fm);
            mDataSource = dataSource;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ListSubCategoriesFragment.newInstance(filterChannel(mDataSource.get(position).displayTitle, getLocalDataSource()));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mDataSource.size() - 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mDataSource.get(position).displayTitle;
        }

        @Override
        public void notifyDataSetChanged() {
            try {
                super.notifyDataSetChanged();
            } catch (Exception e) {
                Log4a.printException(e);
            }
        }

    }


}
