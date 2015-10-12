package com.ranisaurus.baselayer.cell;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ranisaurus.baselayer.activity.BaseActivity;
import com.ranisaurus.newtorklayer.models.BaseModel;

import butterknife.ButterKnife;

/**
 * Created by muzammilpeer on 8/30/15.
 */
abstract public class BaseCell<T extends BaseModel> extends RecyclerView.ViewHolder {

    protected T mDataSource;
    protected long position;

    public BaseCell(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) itemView.getContext();
    }

    abstract public void updateCell(T model);
}