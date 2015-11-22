package com.muzammilpeer.livetv.cell;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.ranisaurus.utilitylayer.view.CGSize;
import com.ranisaurus.utilitylayer.view.ImageUtil;
import com.muzammilpeer.livetv.R;
import com.muzammilpeer.livetv.fragment.ChannelDetailFragment;
import com.muzammilpeer.livetv.fragment.EducationListFragment;
import com.muzammilpeer.livetv.view.SquareImageView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/15/15.
 */
public class EducationListCell extends BaseCell implements View.OnClickListener {

    @Bind(R.id.iv_vod_list)
    SquareImageView ivCategoryPhoto;

    @Bind(R.id.pb_vod_list)
    ProgressBar pbCategoryPhoto;

    @Bind(R.id.tv_vod_list)
    TextView tvCategoryName;

    public EducationListCell(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int viewSize = displaymetrics.widthPixels / EducationListFragment.gridSize;
        itemView.setLayoutParams(new RelativeLayout.LayoutParams(viewSize, viewSize));

        position = this.getAdapterPosition();
        mDataSource = model;

        if (model instanceof Data) {
            Data dataSource = (Data) model;

            String imageUrl = "";
            if (dataSource.id_parent != null && dataSource.id_parent.length() > 0 && dataSource.id_parent.equalsIgnoreCase("445")) {
                tvCategoryName.setText(dataSource.title.toUpperCase());
                imageUrl = (dataSource.mob_small);
            } else if (dataSource.id_parent != null && dataSource.id_parent.length() > 0 && !dataSource.id_parent.equalsIgnoreCase("445")) {
                tvCategoryName.setText(dataSource.title.toUpperCase());
                if (dataSource.mob_small.contains("http")) {
                    imageUrl = (dataSource.mob_small);
                } else {
                    imageUrl = ("http://pitelevision.com/" + dataSource.mob_small);
                }
            } else {
                tvCategoryName.setText(dataSource.title.toUpperCase());
                imageUrl = ("http://piteach.com/iptv/uploads/images/" + dataSource.img_poster);
            }

            ImageUtil.getImageFromUrl(CGSize.make(viewSize, viewSize - tvCategoryName.getHeight()),
                    ivCategoryPhoto, pbCategoryPhoto, imageUrl
            );

        } else {
            ivCategoryPhoto.setImageBitmap(null);
        }

    }

    @Override
    public void onClick(View v) {
        DataListResponseModel sharedData = new DataListResponseModel();
        sharedData.setData((ArrayList) mAdapter.getmObjects());

        Data dataSource = (Data) mDataSource;

        if (dataSource.id_parent != null && dataSource.id_parent.length() > 0 && dataSource.id_parent.equalsIgnoreCase("445")) {
            getBaseActivity().replaceFragment(EducationListFragment.newInstance(dataSource), R.id.container_main);
        } else if (dataSource.id_parent != null && dataSource.id_parent.length() > 0 && !dataSource.id_parent.equalsIgnoreCase("445")) {
            getBaseActivity().replaceFragment(EducationListFragment.newInstance(dataSource), R.id.container_main);
        } else {
            getBaseActivity().replaceFragment(ChannelDetailFragment.newInstance(sharedData, dataSource), R.id.container_main);
        }


//        if (dataSource.vodId != null  && dataSource.vodId.length() > 0 && dataSource.vodCategoryId != null && dataSource.vodCategoryId.length() > 0) {
//            getBaseActivity().replaceFragment(ChannelDetailFragment.newInstance(sharedData, dataSource), R.id.container_main);
//        }else if (dataSource.vodId != null && dataSource.vodId.length() > 0) {
//            getBaseActivity().replaceFragment(VODListFragment.newInstance(dataSource), R.id.container_main);
//        } else if (dataSource.id != null && dataSource.id.length() > 0) {
//            getBaseActivity().replaceFragment(VODListFragment.newInstance(dataSource), R.id.container_main);
//        } else if (dataSource.vodCategoryId != null && dataSource.vodCategoryId.length() > 0) {
//            getBaseActivity().replaceFragment(VODListFragment.newInstance(dataSource), R.id.container_main);
//        } else {
//            getBaseActivity().replaceFragment(ChannelDetailFragment.newInstance(sharedData, dataSource), R.id.container_main);
//        }
    }
}
