package com.ranisaurus.baselayer.cell;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ranisaurus.baselayer.activity.BaseAppCompactActivity;
import com.ranisaurus.baselayer.adapter.GeneralBaseAdapter;
import com.ranisaurus.baselayer.adapter.SimpleBaseAdapter;
import com.ranisaurus.utilitylayer.base.BaseModel;

import butterknife.ButterKnife;

/**
 * Created by muzammilpeer on 8/30/15.
 */
public abstract class BaseCell<T extends BaseModel> extends RecyclerView.ViewHolder {

    protected T mDataSource;
    protected long position;

    protected GeneralBaseAdapter mAdapter;
    protected SimpleBaseAdapter mSimpleAdapter;

    public BaseCell(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public BaseAppCompactActivity getBaseActivity() {
        return (BaseAppCompactActivity) itemView.getContext();
    }

    public abstract void updateCell(T model);

    public void setAdapter(GeneralBaseAdapter adapter) {
        mAdapter = adapter;
    }

    public void setAdapter(SimpleBaseAdapter mSimpleAdapter) {
        this.mSimpleAdapter = mSimpleAdapter;
    }

}