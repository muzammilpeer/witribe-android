package com.muzammilpeer.pakistanitv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.muzammilpeer.pakistanitv.manager.DashBoardManager;
import com.ranisaurus.baselayer.activity.BaseAppCompactActivity;
import com.ranisaurus.utilitylayer.logger.Log4a;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class DashBoardViewPagerAdapter extends FragmentPagerAdapter {

    BaseAppCompactActivity baseAppCompactActivity;

    public DashBoardViewPagerAdapter(FragmentManager fm,BaseAppCompactActivity activity) {
        super(fm);
        baseAppCompactActivity = activity;
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
        return baseAppCompactActivity.getString(DashBoardManager.getDashBoardViewPagerDataSource().get(position).getFragmentTitleID());
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