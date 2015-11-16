package com.witribe.witribeapp.cell;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.fragment.ChannelDetailFragment;
import com.witribe.witribeapp.fragment.VODListFragment;
import com.witribe.witribeapp.view.SquareImageView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/14/15.
 */
public class VodListCell extends BaseCell implements View.OnClickListener {

    @Bind(R.id.iv_vod_list)
    SquareImageView ivCategoryPhoto;

    @Bind(R.id.pb_vod_list)
    ProgressBar pbCategoryPhoto;

    @Bind(R.id.tv_vod_list)
    TextView tvCategoryName;

    public VodListCell(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int viewSize = displaymetrics.widthPixels / VODListFragment.gridSize;
        itemView.setLayoutParams(new RelativeLayout.LayoutParams(viewSize, viewSize));

        itemView.setLayoutParams(new RelativeLayout.LayoutParams(viewSize, viewSize));

        position = this.getAdapterPosition();


        mDataSource = model;

        if (model instanceof Data) {
            Data dataSource = (Data) model;

            String imageUrl = "";
            if (dataSource.vodId != null && dataSource.vodId.length() > 0) {
                tvCategoryName.setText(dataSource.title.toUpperCase());
                imageUrl = (dataSource.mob_small).replaceAll(" ", "%20");
            } else if (dataSource.id != null && dataSource.id.length() > 0) {
                tvCategoryName.setText(dataSource.name.toUpperCase());
                imageUrl = (dataSource.mob_small).replaceAll(" ", "%20");
            } else {
                tvCategoryName.setText(dataSource.name.toUpperCase());
                imageUrl = (dataSource.image).replaceAll(" ", "%20");
            }


            //image loading using picaso
            Picasso.with(itemView.getContext())
                    .load(imageUrl)
                    .resize(viewSize, viewSize - tvCategoryName.getHeight())
                    .centerInside()
                    .into(ivCategoryPhoto, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbCategoryPhoto.setVisibility(View.GONE);
                            ivCategoryPhoto.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            pbCategoryPhoto.setVisibility(View.GONE);
                            ivCategoryPhoto.setVisibility(View.VISIBLE);
                        }
                    });

        } else {
            ivCategoryPhoto.setImageBitmap(null);
        }

    }

    @Override
    public void onClick(View v) {
        DataListResponseModel sharedData = new DataListResponseModel();
        sharedData.setData((ArrayList) mAdapter.getmObjects());

        Data dataSource = (Data) mDataSource;

        if (dataSource.vodId != null && dataSource.vodId.length() > 0 && dataSource.vodCategoryId != null && dataSource.vodCategoryId.length() > 0) {
            getBaseActivity().replaceFragment(ChannelDetailFragment.newInstance(sharedData, dataSource), R.id.container_main);
        } else if (dataSource.vodId != null && dataSource.vodId.length() > 0) {
            getBaseActivity().replaceFragment(VODListFragment.newInstance(dataSource), R.id.container_main);
        } else if (dataSource.id != null && dataSource.id.length() > 0) {
            getBaseActivity().replaceFragment(VODListFragment.newInstance(dataSource), R.id.container_main);
        } else if (dataSource.vodCategoryId != null && dataSource.vodCategoryId.length() > 0) {
            getBaseActivity().replaceFragment(VODListFragment.newInstance(dataSource), R.id.container_main);
        } else {
            getBaseActivity().replaceFragment(ChannelDetailFragment.newInstance(sharedData, dataSource), R.id.container_main);
        }
    }
}
