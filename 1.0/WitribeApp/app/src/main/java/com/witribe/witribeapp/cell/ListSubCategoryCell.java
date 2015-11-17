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
import com.ranisaurus.utilitylayer.view.CGSize;
import com.ranisaurus.utilitylayer.view.ImageUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.fragment.ChannelDetailFragment;
import com.witribe.witribeapp.fragment.ListSubCategoriesFragment;
import com.witribe.witribeapp.view.SquareImageView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/13/15.
 */
public class ListSubCategoryCell extends BaseCell implements View.OnClickListener {

    @Bind(R.id.category_photo)
    SquareImageView ivCategoryPhoto;

    @Bind(R.id.pb_category_photo)
    ProgressBar pbCategoryPhoto;

    @Bind(R.id.category_name)
    TextView tvCategoryName;

    public ListSubCategoryCell(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int viewSize = displaymetrics.widthPixels / ListSubCategoriesFragment.gridSize;
        itemView.setLayoutParams(new RelativeLayout.LayoutParams(viewSize, viewSize));
//        .resize((int) itemView.getResources().getDimension(R.dimen.cardview_thumbnail_height), (int) itemView.getResources().getDimension(R.dimen.cardview_thumbnail_height))

        position = this.getAdapterPosition();


        mDataSource = model;

        if (model instanceof Data) {
            Data dataSource = (Data) model;

            tvCategoryName.setText(dataSource.title.toUpperCase());
            String imageUrl = ("http://pitelevision.com/" + dataSource.mobile_small_image);

            ImageUtil.getImageFromUrl(CGSize.make(viewSize, viewSize - tvCategoryName.getHeight()),
                    ivCategoryPhoto, pbCategoryPhoto, imageUrl
            );


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

        getBaseActivity().replaceFragment(ChannelDetailFragment.newInstance(sharedData, dataSource), R.id.container_main);
    }
}
