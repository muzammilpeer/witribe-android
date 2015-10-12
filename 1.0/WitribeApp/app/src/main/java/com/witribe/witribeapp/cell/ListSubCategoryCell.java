package com.witribe.witribeapp.cell;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.BaseModel;
import com.ranisaurus.newtorklayer.models.Data;
import com.witribe.witribeapp.R;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 10/13/15.
 */
public class ListSubCategoryCell extends BaseCell implements View.OnClickListener {

    @Bind(R.id.category_photo)
    ImageView ivCategoryPhoto;

    @Bind(R.id.category_name)
    TextView tvCategoryName;

    public ListSubCategoryCell(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void updateCell(BaseModel model) {
        position = this.getAdapterPosition();

        if (model instanceof Data) {
            Data dataSource = (Data) model;

            tvCategoryName.setText(dataSource.title);
            String imageUrl = "http://pitelevision.com/" + dataSource.mobile_small_image;

            Ion.with(itemView.getContext())
                    .load(imageUrl)
                    .withBitmap()
                    .placeholder(android.R.color.white)
                    .error(android.R.color.darker_gray)
                    .intoImageView(ivCategoryPhoto);
        }

    }

    @Override
    public void onClick(View v) {
//        getBaseActivity().replaceFragment(TagLineFragment.createInstance(mDataSource), R.id.container_main);
    }
}