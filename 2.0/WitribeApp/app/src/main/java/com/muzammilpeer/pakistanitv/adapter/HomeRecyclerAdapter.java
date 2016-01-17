package com.muzammilpeer.pakistanitv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzammilpeer.pakistanitv.R;
import com.muzammilpeer.pakistanitv.cell.HomePagerCell;
import com.muzammilpeer.pakistanitv.cell.HomePagerSectionCell;
import com.ranisaurus.baselayer.adapter.SimpleBaseAdapter;
import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.SampleModel;
import com.ranisaurus.newtorklayer.models.SectionSampleModel;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.ranisaurus.utilitylayer.reflection.ReflectionUtil;

import java.util.List;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class HomeRecyclerAdapter extends SimpleBaseAdapter {

    // The items to display in your RecyclerView
//    private List<BaseModel> mObjects;

    public static final int SECTION = 0, CELL = 1;

    public HomeRecyclerAdapter(List<BaseModel> items) {
        super(items);
    }

    //    // Provide a suitable constructor (depends on the kind of dataset)
//    public HomeRecyclerAdapter(List<BaseModel> items) {
//        this.mObjects = items;
//    }

//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return this.mObjects.size();
//    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (mObjects.get(position) instanceof SampleModel) {
            return CELL;
        } else if (mObjects.get(position) instanceof SectionSampleModel) {
            return SECTION;
        }
        return -1;
    }

    @Override
    public BaseCell onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        BaseCell viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case SECTION: {
                View inflatedView = inflater.inflate(R.layout.row_home_pager_section, viewGroup, false);
                viewHolder = (BaseCell) ReflectionUtil.instantiate(HomePagerSectionCell.class, View.class, inflatedView);
            }
            break;
            case CELL: {
                View inflatedView = inflater.inflate(R.layout.row_home_pager, viewGroup, false);
                viewHolder = (BaseCell) ReflectionUtil.instantiate(HomePagerCell.class, View.class, inflatedView);
            }
            break;
            default: {
                View inflatedView = inflater.inflate(R.layout.row_home_pager, viewGroup, false);
                viewHolder = (BaseCell) ReflectionUtil.instantiate(HomePagerCell.class, View.class, inflatedView);
            }
            break;
        }
        return viewHolder;
    }

    //utils
    public BaseModel getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public void onBindViewHolder(BaseCell holder, int position) {
//        BaseCell holder = (BaseCell) viewHolder;
        holder.setAdapter(this);
        holder.updateCell(getItem(position));
    }
}