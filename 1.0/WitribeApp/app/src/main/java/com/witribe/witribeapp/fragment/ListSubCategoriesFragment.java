package com.witribe.witribeapp.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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

    @Bind(R.id.ll_subcategories)
    LinearLayout llSubcategories;

    @Bind(R.id.pb_subcategories)
    ProgressBar pbSubCategories;

    public static int gridSize = 3;

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

        showLoader();

        if (getArguments() != null) {
            try {
                DataListResponseModel modelData = getArguments().getParcelable(ARG_CATEGORY_NAME);
                if (modelData != null && modelData.getData().size() > 0) {
                    this.getLocalDataSource().clear();
                    this.getLocalDataSource().addAll(modelData.getData());
                    dataGeneralBaseAdapter.notifyDataSetChanged();
                    hideLoader(false);
                } else {
                    hideLoader(true);
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log4a.e("onConfigurationChanged", " called");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridSize = 5;
        }else {
            gridSize = 3;
        }
        rcSubCategories.setLayoutManager(new GridLayoutManager(getBaseActivity(), gridSize));
    }


            @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();


        dataGeneralBaseAdapter = new GeneralBaseAdapter<ListSubCategoryCell>(mContext, R.layout.row_list_subcategory, ListSubCategoryCell.class, this.getLocalDataSource());

        if (getBaseActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridSize = 5;
        }else {
            gridSize = 3;
        }

        rcSubCategories.setHasFixedSize(true);
        rcSubCategories.setLayoutManager(new GridLayoutManager(getBaseActivity(), gridSize));
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


    @Override
    protected void showLoader() {
        pbSubCategories.setVisibility(View.VISIBLE);
        rcSubCategories.setVisibility(View.GONE);
        llSubcategories.setVisibility(View.GONE);
    }

    @Override
    protected void hideLoader(boolean isError) {
        pbSubCategories.setVisibility(View.GONE);

        if (isError) {
            llSubcategories.setVisibility(View.VISIBLE);
            rcSubCategories.setVisibility(View.GONE);
        } else {
            llSubcategories.setVisibility(View.GONE);
            rcSubCategories.setVisibility(View.VISIBLE);
        }
    }
}
