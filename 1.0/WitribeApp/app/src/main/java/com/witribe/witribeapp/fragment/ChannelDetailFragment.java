package com.witribe.witribeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranisaurus.baselayer.adapter.GeneralBaseAdapter;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.cell.ListSubCategoryCell;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/17/15.
 */
public class ChannelDetailFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_CATEGORY_NAME = "category_name";
    //     UI references.
    @Bind(R.id.rc_related_channels)
    RecyclerView rcRelatedChannels;

    GeneralBaseAdapter<ListSubCategoryCell> dataGeneralBaseAdapter;

    private DataListResponseModel currentData;
//    private StaggeredGridLayoutManager gaggeredGridLayoutManager;


    public static ChannelDetailFragment newInstance(DataListResponseModel filterList) {
        ChannelDetailFragment fragment = new ChannelDetailFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_CATEGORY_NAME, filterList);

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
    }

    @Override
    public void initObjects() {
        super.initObjects();

        getLocalDataSource().clear();
        if (currentData != null) {
            if (currentData.getData().size() > 8) {
                getLocalDataSource().addAll(currentData.getData().subList(0, 8));
            } else {
                getLocalDataSource().addAll(currentData.getData());
            }
        }
    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        dataGeneralBaseAdapter = new GeneralBaseAdapter<ListSubCategoryCell>(mContext, R.layout.row_list_subcategory, ListSubCategoryCell.class, this.getLocalDataSource());
        rcRelatedChannels.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcRelatedChannels.setLayoutManager(layoutManager);

        rcRelatedChannels.setAdapter(dataGeneralBaseAdapter);

        dataGeneralBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        }
    }


}