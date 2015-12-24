package com.muzammilpeer.smarttvapp.cell;

import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muzammilpeer.livetv.R;
import com.muzammilpeer.smarttvapp.constant.PreferencesKeys;
import com.muzammilpeer.smarttvapp.fragment.ChannelDetailFragment;
import com.muzammilpeer.smarttvapp.view.SquareImageView;
import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.ranisaurus.utilitylayer.view.CGSize;
import com.ranisaurus.utilitylayer.view.ImageUtil;

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

    @Bind(R.id.card_view)
    CardView cardView;

    public ListSubCategoryCell(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void updateCell(BaseModel model) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getBaseActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int viewSize = displaymetrics.widthPixels / PreferencesKeys.getGridColumnCount(getBaseActivity());
        int additionalHeight = (int)getBaseActivity().getResources().getDimension(R.dimen.cardview_bottom_view_height);
        itemView.setLayoutParams(new RelativeLayout.LayoutParams(viewSize, viewSize + additionalHeight));

        position = this.getAdapterPosition();


        mDataSource = model;

        if (model instanceof Data) {
            Data dataSource = (Data) model;

            tvCategoryName.setText(dataSource.title.toUpperCase());
            String imageUrl = ("http://pitelevision.com/" + dataSource.mobile_small_image);

            ImageUtil.getImageFromUrl(CGSize.make(viewSize, viewSize + additionalHeight),
                    ivCategoryPhoto, pbCategoryPhoto, imageUrl
            );


            //image loading using picaso
//            Picasso.with(itemView.getContext())
//                    .load(imageUrl)
//                    .resize(viewSize, viewSize  + 2*additionalHeight)
//                    .centerInside()
//                    .into(ivCategoryPhoto, new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            pbCategoryPhoto.setVisibility(View.GONE);
//                            ivCategoryPhoto.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onError() {
//                            pbCategoryPhoto.setVisibility(View.GONE);
//                            ivCategoryPhoto.setVisibility(View.VISIBLE);
//                        }
//                    });

        } else {
            ivCategoryPhoto.setImageBitmap(null);
        }

    }

    @Override
    public void onClick(View v) {
        DataListResponseModel sharedData = new DataListResponseModel();
        sharedData.setData((ArrayList) mAdapter.getmObjects());
        Data dataSource = (Data) mDataSource;

        getBaseActivity().replaceFragment(getBaseActivity().getLastFragment(),ChannelDetailFragment.newInstance(sharedData, dataSource), R.id.container_main,R.id.card_view,R.transition.change_image_transform,"shared_element_transition","transition_detail_list");
    }
}
