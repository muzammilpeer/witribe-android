package com.witribe.witribeapp.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ranisaurus.baselayer.adapter.GeneralBaseAdapter;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.cell.RelatedChannelCell;

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

        Log4a.e("newInstance = "," channels = " + filterList.getData().size() + " ,instance = " +  sData);

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

        getBaseActivity().getTabLayoutView().setVisibility(View.GONE);

        getBaseActivity().showToolBar();
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


        final String imageUrl =  ("http://pitelevision.com/" + selectedData.mobile_large_image).replaceAll(" ", "%20");
        Ion.with(ivChannel.getContext()).load(imageUrl).withBitmap()
                .placeholder(R.drawable.bg_placeholder)
                .error(R.drawable.bg_placeholder)
                .asBitmap()
                .setCallback(new FutureCallback<Bitmap>() {
                    @Override
                    public void onCompleted(Exception e, Bitmap result) {
                        Log4a.e("Image Url = ", imageUrl);
                        if (ivChannel != null && ivChannel.getContext() != null)
                        {
                            ivChannel.setImageBitmap(result != null ? result : BitmapFactory.decodeResource(ivChannel.getContext().getResources(), R.drawable.bg_placeholder));
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

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_channel: {
                if (LoginFragment.user_profile != null)
                {
                    WebViewFragment fragment = WebViewFragment.newInstance(selectedData);
                    getBaseActivity().replaceFragment(fragment, R.id.container_main);
                }else {
                    Snackbar.make(v,R.string.unauthorized_access,Snackbar.LENGTH_SHORT).show();
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
}