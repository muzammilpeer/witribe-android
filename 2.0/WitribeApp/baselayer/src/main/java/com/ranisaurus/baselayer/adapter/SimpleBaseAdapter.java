package com.ranisaurus.baselayer.adapter;

import android.support.v7.widget.RecyclerView;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.utilitylayer.base.BaseModel;

import java.util.List;

/**
 * Created by muzammilpeer on 12/27/15.
 */
public abstract class SimpleBaseAdapter extends RecyclerView.Adapter<BaseCell> {

    // The items to display in your RecyclerView
    protected List<BaseModel> mObjects;

    private int currentColumnWidth;

    public SimpleBaseAdapter() {
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SimpleBaseAdapter(List<BaseModel> items) {
        this.mObjects = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.mObjects.size();
    }


    //utils
    public BaseModel getItem(int position) {
        return mObjects.get(position);
    }

    public int getCurrentColumnWidth() {
        return currentColumnWidth;
    }

    public void setCurrentColumnWidth(int currentColumnWidth) {
        this.currentColumnWidth = currentColumnWidth;
    }
}