package com.witribe.witribeapp.cell;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.ranisaurus.utilitylayer.file.DirectoryEnum;
import com.ranisaurus.utilitylayer.file.FileUtil;
import com.ranisaurus.utilitylayer.file.model.FileInfoModel;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.fragment.RecordedVideoDetailPlayScreenFragment;
import com.witribe.witribeapp.view.SquareImageView;

import java.util.Date;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/9/15.
 */
public class RecordedVideoListCell extends BaseCell implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    @Bind(R.id.iv_recorded_video_list)
    SquareImageView ivRecordedVideoList;


    @Bind(R.id.tvFileName)
    TextView tvFileName;

    @Bind(R.id.tvFileSize)
    TextView tvFileSize;


    @Bind(R.id.tvFileLastModified)
    TextView tvFileLastModified;

    @Bind(R.id.ll_main_row_recorded_video_list)
    LinearLayout llMainLayout;


    @Bind(R.id.iv_context_menu)
    ImageView ivContextMenu;

    PopupMenu popup;

    public RecordedVideoListCell(View itemView) {
        super(itemView);

        ivContextMenu.setOnClickListener(this);
        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {
        position = this.getAdapterPosition();

        mDataSource = model;

        if (model instanceof FileInfoModel) {
            FileInfoModel dataSource = (FileInfoModel) model;
            ivRecordedVideoList.setImageBitmap(dataSource.getCacheThumbnail());
            tvFileName.setText(dataSource.getFileName());

            long size = Long.parseLong(dataSource.getFileSize());
            float fileSize = size / 1024;

            tvFileSize.setText(fileSize + " kB");

            long timeStamp = Long.parseLong(dataSource.getDateModifed());
            Date fileTime = new Date(timeStamp);
            tvFileLastModified.setText(fileTime.toString());

//
//            if (position % 2 == 0) {
//                llMainLayout.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.colorPrimary));
//
//                tvFileName.setTextColor(getBaseActivity().getResources().getColor(android.R.color.white));
//                tvFileSize.setTextColor(getBaseActivity().getResources().getColor(android.R.color.white));
//                tvFileLastModified.setTextColor(getBaseActivity().getResources().getColor(android.R.color.white));
//
//            } else {
//                llMainLayout.setBackgroundColor(getBaseActivity().getResources().getColor(android.R.color.white));
//
//                tvFileName.setTextColor(getBaseActivity().getResources().getColor(R.color.colorPrimary));
//                tvFileSize.setTextColor(getBaseActivity().getResources().getColor(R.color.colorPrimary));
//                tvFileLastModified.setTextColor(getBaseActivity().getResources().getColor(R.color.colorPrimary));
//            }

        } else {
            ivRecordedVideoList.setImageBitmap(null);
        }

    }

    @Override
    public void onClick(View v) {
        if (v == ivContextMenu) {
            if (popup != null) {
                popup.dismiss();
                popup = null;
            }

            if (popup == null) {
                popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.menu_popup_recorded_video_list);
                popup.setOnMenuItemClickListener(this);
                popup.show();
            }
        }else if (v == itemView)
        {
            FileInfoModel selectedModel = (FileInfoModel) mDataSource;
            getBaseActivity().replaceFragment(RecordedVideoDetailPlayScreenFragment.newInstance(selectedModel),R.id.container_main);
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete_file: {
                FileInfoModel dataSource = (FileInfoModel) mDataSource;

                if (FileUtil.deleteFile(dataSource) == DirectoryEnum.SUCCUESSFULLY_DONE) {
                    Snackbar.make(itemView, "Successfully deleted", Snackbar.LENGTH_SHORT).show();
                    mAdapter.getmObjects().remove(dataSource);
                    mAdapter.notifyItemRemoved((int) position);
                } else {
                    Snackbar.make(itemView, "Error in file delete", Snackbar.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.menu_share_file: {
                FileInfoModel dataSource = (FileInfoModel) mDataSource;
                Snackbar.make(itemView, "Share feature add here", Snackbar.LENGTH_SHORT).show();
            }
            break;
        }

        return true;
    }
}
