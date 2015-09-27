package com.ranisaurus.baselayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.utilitylayer.reflection.ReflectionUtil;

import java.util.List;

/**
 * Created by muzammilpeer on 8/30/15.
 */
public class GeneralBaseAdapter<VH extends BaseCell>
        extends RecyclerView.Adapter<VH> {

    Class<VH> viewHolderClass;
    int mResourceID;
    private Context mContext;
    private List<?> mObjects;

    public GeneralBaseAdapter(Context context, int resourceId, Class<VH> vhClass, List<?> dataSource) {
        mContext = context;
        mResourceID = resourceId;
        viewHolderClass = vhClass;
        this.mObjects = dataSource;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflatedView = LayoutInflater.from(mContext).inflate(mResourceID, viewGroup, false);
        return (VH) ReflectionUtil.instantiate(viewHolderClass, View.class, inflatedView);
    }

    @Override
    public void onBindViewHolder(VH viewHolder, int i) {
        viewHolder.updateCell(getItem(i));
    }


    @Override
    public int getItemCount() {
        return mObjects.size();
    }


    //utils
    public Object getItem(int position) {
        return mObjects.get(position);
    }

}