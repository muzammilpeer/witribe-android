package com.witribe.witribeapp.cell;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.BaseModel;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataListResponseModel;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.fragment.ChannelDetailFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/18/15.
 */
public class RelatedChannelCell extends BaseCell implements View.OnClickListener {

    @Bind(R.id.category_photo)
    ImageView ivCategoryPhoto;

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
            String imageUrl = ("http://pitelevision.com/" + dataSource.mobile_small_image).replaceAll(" ", "%20");

            Ion.with(itemView.getContext()).load(imageUrl).withBitmap()
                    .placeholder(R.drawable.bg_placeholder)
                    .error(R.drawable.bg_placeholder)
                    .asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {
                            ivCategoryPhoto.setImageBitmap(result != null ? result : BitmapFactory.decodeResource(ivCategoryPhoto.getContext().getResources(), R.drawable.bg_placeholder));
                            pbCategoryPhoto.setVisibility(View.GONE);
                            ivCategoryPhoto.setVisibility(View.VISIBLE);
                        }
                    });
        }

    }

    @Override
    public void onClick(View v) {
        DataListResponseModel sharedData = new DataListResponseModel();
        sharedData.setData((ArrayList) mAdapter.getmObjects());
        Data dataSource = (Data) mDataSource;

        getBaseActivity().replaceFragmentWithoutStack(ChannelDetailFragment.newInstance(sharedData, dataSource), R.id.container_main);
    }
}
