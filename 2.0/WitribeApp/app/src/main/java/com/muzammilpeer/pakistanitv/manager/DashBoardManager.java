package com.muzammilpeer.pakistanitv.manager;

import com.muzammilpeer.pakistanitv.R;
import com.muzammilpeer.pakistanitv.fragment.mobile.dashboard.EducationPagerFragment;
import com.muzammilpeer.pakistanitv.fragment.mobile.dashboard.FavouritesPagerFragment;
import com.muzammilpeer.pakistanitv.fragment.mobile.dashboard.HomePagerFragment;
import com.muzammilpeer.pakistanitv.fragment.mobile.dashboard.LiveChannelsPagerFragment;
import com.muzammilpeer.pakistanitv.fragment.mobile.dashboard.RecordShowsPagerFragment;
import com.muzammilpeer.pakistanitv.fragment.mobile.dashboard.SettingsPagerFragment;
import com.muzammilpeer.pakistanitv.fragment.mobile.dashboard.VideoOnDemandPagerFragment;
import com.muzammilpeer.pakistanitv.model.DashBoardViewPagerModel;

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
