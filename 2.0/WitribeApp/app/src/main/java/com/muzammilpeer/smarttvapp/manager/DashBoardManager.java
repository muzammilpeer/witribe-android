package com.muzammilpeer.smarttvapp.manager;

import com.muzammilpeer.smarttvapp.R;
import com.muzammilpeer.smarttvapp.fragment.mobile.dashboard.EducationPagerFragment;
import com.muzammilpeer.smarttvapp.fragment.mobile.dashboard.FavouritesPagerFragment;
import com.muzammilpeer.smarttvapp.fragment.mobile.dashboard.HomePagerFragment;
import com.muzammilpeer.smarttvapp.fragment.mobile.dashboard.LiveChannelsPagerFragment;
import com.muzammilpeer.smarttvapp.fragment.mobile.dashboard.RecordShowsPagerFragment;
import com.muzammilpeer.smarttvapp.fragment.mobile.dashboard.SettingsPagerFragment;
import com.muzammilpeer.smarttvapp.fragment.mobile.dashboard.VideoOnDemandPagerFragment;
import com.muzammilpeer.smarttvapp.model.DashBoardViewPagerModel;

import java.util.ArrayList;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class DashBoardManager {

    private static ArrayList<DashBoardViewPagerModel> viewPagerDataSource;

    public static ArrayList<DashBoardViewPagerModel> getDashBoardViewPagerDataSource()
    {
        if (viewPagerDataSource == null)
        {
            viewPagerDataSource = new ArrayList<DashBoardViewPagerModel>();

            viewPagerDataSource.add(new DashBoardViewPagerModel(R.string.view_pager_title_0,HomePagerFragment.class));
            viewPagerDataSource.add(new DashBoardViewPagerModel(R.string.view_pager_title_1,LiveChannelsPagerFragment.class));
            viewPagerDataSource.add(new DashBoardViewPagerModel(R.string.view_pager_title_2,VideoOnDemandPagerFragment.class));
            viewPagerDataSource.add(new DashBoardViewPagerModel(R.string.view_pager_title_3,EducationPagerFragment.class));
            viewPagerDataSource.add(new DashBoardViewPagerModel(R.string.view_pager_title_4,FavouritesPagerFragment.class));
            viewPagerDataSource.add(new DashBoardViewPagerModel(R.string.view_pager_title_5,RecordShowsPagerFragment.class));
            viewPagerDataSource.add(new DashBoardViewPagerModel(R.string.view_pager_title_6,SettingsPagerFragment.class));

        }
        return viewPagerDataSource;
    }
}
