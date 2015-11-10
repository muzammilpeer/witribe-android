package com.witribe.witribeapp.cell;

import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.ranisaurus.utilitylayer.file.model.FileInfoModel;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.view.SquareImageView;

import java.util.Date;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/9/15.
 */
public class RecordedVideoListCell extends BaseCell implements View.OnClickListener {

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


    public RecordedVideoListCell(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {
        position = this.getAdapterPosition();

        mDataSource = model;

        if (model instanceof FileInfoModel) {
            FileInfoModel dataSource = (FileInfoModel) model;
            ivRecordedVideoList.setImageBitmap(ThumbnailUtils.createVideoThumbnail(dataSource.getFullPath(), MediaStore.Video.Thumbnails.MICRO_KIND));
            tvFileName.setText(dataSource.getFileName());

            long size = Long.parseLong(dataSource.getFileSize());
            float fileSize = size/1024;

            tvFileSize.setText(fileSize + " kB");

            long timeStamp =  Long.parseLong(dataSource.getDateModifed());
            Date fileTime = new Date(timeStamp);
            tvFileLastModified.setText(fileTime.toString());


            if (position %2 == 0) {
                llMainLayout.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.color_primary_blue_light));

                tvFileName.setTextColor(getBaseActivity().getResources().getColor(android.R.color.white));
                tvFileSize.setTextColor(getBaseActivity().getResources().getColor(android.R.color.white));
                tvFileLastModified.setTextColor(getBaseActivity().getResources().getColor(android.R.color.white));

            }else {
                llMainLayout.setBackgroundColor(getBaseActivity().getResources().getColor(android.R.color.white));

                tvFileName.setTextColor(getBaseActivity().getResources().getColor(R.color.color_primary_blue_light));
                tvFileSize.setTextColor(getBaseActivity().getResources().getColor(R.color.color_primary_blue_light));
                tvFileLastModified.setTextColor(getBaseActivity().getResources().getColor(R.color.color_primary_blue_light));
            }

        } else {
            ivRecordedVideoList.setImageBitmap(null);
        }

    }

    @Override
    public void onClick(View v) {
        FileInfoModel dataSource = (FileInfoModel) mDataSource;
//        getBaseActivity().replaceFragment(ChannelDetailFragment.newInstance(), R.id.container_main);
    }
}
