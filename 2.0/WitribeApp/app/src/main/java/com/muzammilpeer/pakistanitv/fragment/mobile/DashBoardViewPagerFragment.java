package com.muzammilpeer.pakistanitv.fragment.mobile;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzammilpeer.pakistanitv.R;
import com.muzammilpeer.pakistanitv.adapter.DashBoardViewPagerAdapter;
import com.muzammilpeer.pakistanitv.manager.DashBoardManager;
import com.muzammilpeer.pakistanitv.transitions.DepthPageTransformer;
import com.ranisaurus.baselayer.fragment.BaseFragment;
import com.ranisaurus.utilitylayer.logger.Log4a;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class DashBoardViewPagerFragment extends BaseFragment {

    @Bind(R.id.container_viewpager)
    ViewPager mViewPager;

    private DashBoardViewPagerAdapter viewPagerAdapter;

    public DashBoardViewPagerFragment() {

    }

    public static DashBoardViewPagerFragment newInstance() {
        DashBoardViewPagerFragment fragment = new DashBoardViewPagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
        }
        super.onCreateView(inflater, R.layout.fragment_view_pager_dashboard);
        getBaseActivity().getTabLayoutView().setVisibility(View.VISIBLE);
        Log4a.d("onCreateView", "DashBoardViewPagerFragment");
        return mView;
    }

    @Override
    public void initViews() {
        super.initViews();
        getBaseActivity().getTabLayoutView().setVisibility(View.VISIBLE);

//        WindowUtil.showSystemUi(getBaseActivity());
    }

    @Override
    public void initObjects() {
        super.initObjects();
    }

    @Override
    public void initListenerOrAdapter() {
        super.initListenerOrAdapter();
        DashBoardManager.getDashBoardViewPagerDataSource();

        viewPagerAdapter = new DashBoardViewPagerAdapter(getChildFragmentManager(),getBaseActivity());
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(viewPagerAdapter);
        getBaseActivity().getTabLayoutView().setupWithViewPager(mViewPager);
        getBaseActivity().getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void initNetworkCalls() {
        super.initNetworkCalls();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log4a.d("onConfigurationChanged", " called");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (DashBoardManager.getDashBoardViewPagerDataSource().size() > 0) {
                viewPagerAdapter.notifyDataSetChanged();
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (DashBoardManager.getDashBoardViewPagerDataSource().size() > 0) {
                viewPagerAdapter.notifyDataSetChanged();
            }
        }
    }


}
