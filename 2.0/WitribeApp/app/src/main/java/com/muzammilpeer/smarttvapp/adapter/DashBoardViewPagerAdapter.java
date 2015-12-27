package com.muzammilpeer.smarttvapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.muzammilpeer.smarttvapp.manager.DashBoardManager;
import com.ranisaurus.baselayer.activity.BaseActivity;
import com.ranisaurus.utilitylayer.logger.Log4a;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class DashBoardViewPagerAdapter extends FragmentPagerAdapter {

    BaseActivity baseActivity;

    public DashBoardViewPagerAdapter(FragmentManager fm,BaseActivity activity) {
        super(fm);
        baseActivity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        return DashBoardManager.getDashBoardViewPagerDataSource().get(position).getFragmentObject();
    }

    @Override
    public int getCount() {
        return DashBoardManager.getDashBoardViewPagerDataSource().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return baseActivity.getString(DashBoardManager.getDashBoardViewPagerDataSource().get(position).getFragmentTitleID());
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