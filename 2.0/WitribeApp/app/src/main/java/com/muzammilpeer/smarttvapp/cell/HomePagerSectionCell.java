package com.muzammilpeer.smarttvapp.cell;

import android.view.View;
import android.widget.TextView;

import com.muzammilpeer.smarttvapp.R;
import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.SectionSampleModel;
import com.ranisaurus.utilitylayer.base.BaseModel;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 12/27/15.
 */
public class HomePagerSectionCell extends BaseCell implements View.OnClickListener {

    @Bind(R.id.tv_title)
    TextView tvTitle;

    public HomePagerSectionCell(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {
        position = this.getAdapterPosition();
        mDataSource = model;
        SectionSampleModel data = (SectionSampleModel) model;

        tvTitle.setText(data.getTitle());
    }

    @Override
    public void onClick(View v) {

    }
}