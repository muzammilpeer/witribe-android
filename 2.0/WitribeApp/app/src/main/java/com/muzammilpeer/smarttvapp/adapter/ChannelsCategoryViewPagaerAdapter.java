package com.muzammilpeer.smarttvapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.muzammilpeer.smarttvapp.fragment.ListSubCategoriesFragment;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.utilitylayer.logger.Log4a;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muzammilpeer on 10/17/15.
 */
public class ChannelsCategoryViewPagaerAdapter extends FragmentPagerAdapter {

    private List<Data> mDataSource;
    private List<Data> mFilterDataSource;


    public ChannelsCategoryViewPagaerAdapter(FragmentManager fm, List dataSource, List filterDataSource) {
        super(fm);
        mDataSource = dataSource;
        mFilterDataSource = filterDataSource;
    }

    @Override
    public Fragment getItem(int position) {
        if (mDataSource != null) {
            return ListSubCategoriesFragment.newInstance(filterChannel(mDataSource.get(position).displayTitle, mFilterDataSource));
        } else {
            return ListSubCategoriesFragment.newInstance(null);
        }
    }

    @Override
    public int getCount() {
        if (mDataSource != null) {
            return mDataSource.size();
        } else {
            return 1;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mDataSource != null) {
            return mDataSource.get(position).displayTitle;
        } else {
            return "No data";
        }
    }

    @Override
    public void notifyDataSetChanged() {
        try {
            super.notifyDataSetChanged();
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

}