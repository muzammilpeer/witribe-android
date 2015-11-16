package com.witribe.witribeapp.cell;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.fragment.ChannelDetailFragment;
import com.witribe.witribeapp.view.SquareImageView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/18/15.
 */
public class RelatedChannelCell extends BaseCell implements View.OnClickListener {

    @Bind(R.id.category_photo)
    SquareImageView ivCategoryPhoto;

    @Bind(R.id.pb_category_photo)
    ProgressBar pbCategoryPhoto;

    @Bind(R.id.category_name)
    TextView tvCategoryName;

    public RelatedChannelCell(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {

        position = this.getAdapterPosition();

        mDataSource = model;

        if (model instanceof Data) {
            Data dataSource = (Data) model;

            tvCategoryName.setText(dataSource.title);

            String imageUrl = "";


            if (dataSource.mob_large != null && dataSource.mob_large.length() > 0) {
                imageUrl = (dataSource.mob_large).replaceAll(" ", "%20");
            } else if (dataSource.img_poster != null && dataSource.img_poster.length() > 0) {
                if (dataSource.img_poster.contains("http")) {
                    imageUrl = (dataSource.img_poster).replaceAll(" ", "%20");
                } else {
                    imageUrl = ("http://piteach.com/iptv/uploads/images/" + dataSource.img_poster).replaceAll(" ", "%20");
                }
            } else {

                imageUrl = ("http://pitelevision.com/" + dataSource.mobile_large_image).replaceAll(" ", "%20");
            }

            Picasso.with(itemView.getContext())
                    .load(imageUrl)
                    .resize((int) itemView.getResources().getDimension(R.dimen.cardview_thumbnail_height), (int) itemView.getResources().getDimension(R.dimen.cardview_thumbnail_height))
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

        ChannelDetailFragment detailFragment = (ChannelDetailFragment) getBaseActivity().getLastFragment();
        getBaseActivity().popFragment(detailFragment);
        getBaseActivity().replaceFragment(ChannelDetailFragment.newInstance(sharedData, dataSource), R.id.container_main);
    }
}
