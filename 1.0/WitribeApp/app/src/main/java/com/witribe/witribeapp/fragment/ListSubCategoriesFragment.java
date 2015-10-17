package com.witribe.witribeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
 * Created by muzammilpeer on 10/13/15.
 */
public class ListSubCategoriesFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_CATEGORY_NAME = "category_name";
    // UI references.
    @Bind(R.id.rc_subcategories)
    RecyclerView rcSubCategories;
    GeneralBaseAdapter<ListSubCategoryCell> dataGeneralBaseAdapter;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private String selectedCategory;

    public static ListSubCategoriesFragment newInstance(DataListResponseModel filterList) {
        ListSubCategoriesFragment fragment = new ListSubCategoriesFragment();
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
        super.onCreateView(inflater, R.layout.fragment_listchannels);

        if (getArguments() != null) {
            try {
                DataListResponseModel modelData = getArguments().getParcelable(ARG_CATEGORY_NAME);
                if (modelData != null && modelData.getData().size() > 0) {
                    this.getLocalDataSource().clear();
                    this.getLocalDataSource().addAll(modelData.getData());
                    dataGeneralBaseAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                Log4a.printException(e);
            }
        }

        return mView;
    }


    @Override
    public void initViews() {
        super.initViews();

    }

    @Override
    public void initObjects() {
        super.initObjects();

    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        dataGeneralBaseAdapter = new GeneralBaseAdapter<ListSubCategoryCell>(mContext, R.layout.row_list_subcategory, ListSubCategoryCell.class, this.getLocalDataSource());
        rcSubCategories.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        rcSubCategories.setLayoutManager(gaggeredGridLayoutManager);

        rcSubCategories.setAdapter(dataGeneralBaseAdapter);

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
//
//    private void getChannelsDataRequest() {
//        String[] params = new String[2];
//        WitribeAMFRequest request = new WitribeAMFRequest(null, NetworkRequestEnum.GET_CHANNELS);
//        try {
//            NetworkManager.getInstance().executeRequest(request, this);
//
//        } catch (Exception e) {
//            Log4a.printException(e);
//        }
//    }

//    @Override
//    public void responseWithError(Exception error, BaseNetworkRequest request) {
//        super.responseWithError(error, request);
//        try {
//            if (mView != null) {
//                switch (request.getNetworkRequestEnum()) {
//                    case GET_CHANNELS: {
//                        Log4a.e("Error ", "some error in network");
//                    }
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            Log4a.printException(e);
//        }
//    }

//    @Override
//    public void successWithData(Object data, BaseNetworkRequest request) {
//        super.successWithData(data, request);
//        try {
//            if (mView != null) {
//                switch (request.getNetworkRequestEnum()) {
//
//                    case GET_CHANNELS: {
//                        DataListResponseModel model = (DataListResponseModel) GsonUtil.getObjectFromJsonObject(data, DataListResponseModel.class);
//                        Log4a.e("Response ", model.toString() + "");
//                        this.getLocalDataSource().clear();
//                        this.getLocalDataSource().addAll(Arrays.asList(model.getData()));
//                        dataGeneralBaseAdapter.notifyDataSetChanged();
//                    }
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            Log4a.printException(e);
//        }
//    }


}
