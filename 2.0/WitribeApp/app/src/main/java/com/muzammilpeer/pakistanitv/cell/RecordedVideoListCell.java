package com.muzammilpeer.pakistanitv.cell;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.muzammilpeer.pakistanitv.fragment.MainActivityFragment;
import com.muzammilpeer.pakistanitv.fragment.RecordedVideoDetailPlayScreenFragment;
import com.muzammilpeer.pakistanitv.view.SquareImageView;
import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.ranisaurus.utilitylayer.file.DirectoryEnum;
import com.ranisaurus.utilitylayer.file.FileUtil;
import com.ranisaurus.utilitylayer.file.model.FileInfoModel;
import com.muzammilpeer.pakistanitv.R;

import java.io.File;
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
        position = this.getLayoutPosition();

        mDataSource = model;

        if (model instanceof FileInfoModel) {
            FileInfoModel dataSource = (FileInfoModel) model;
            ivRecordedVideoList.setImageBitmap(dataSource.getCacheThumbnail());
            tvFileName.setText(dataSource.getFileName());

            long size = Long.parseLong(dataSource.getFileSize());
            float fileSize = size / 1024;
            if (fileSize >= 1024)
            {
                fileSize = fileSize/1024;
                tvFileSize.setText((int)fileSize + " MB");
            }else {
                tvFileSize.setText((int)fileSize + " KB");
            }


            long timeStamp = Long.parseLong(dataSource.getDateModifed());
            Date fileTime = new Date(timeStamp);
            tvFileLastModified.setText(fileTime.toString());

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
        } else if (v == itemView) {
            FileInfoModel selectedModel = (FileInfoModel) mDataSource;
            getBaseActivity().replaceFragment(MainActivityFragment.newInstance(), RecordedVideoDetailPlayScreenFragment.newInstance(selectedModel), R.id.container_main,R.id.card_view,R.transition.change_image_transform,"shared_element_transition","transition_recorded_detail_list");
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
                    mAdapter.notifyItemRemoved((int) this.getLayoutPosition());
                } else {
                    Snackbar.make(itemView, "Error in file delete", Snackbar.LENGTH_SHORT).show();
                }
            }
            break;
            case R.id.menu_share_file: {
                FileInfoModel dataSource = (FileInfoModel) mDataSource;

                String videoType = "video/*";
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);

                // Create the URI from the media
                File media = new File(dataSource.getFullPath());
                Uri videoUri = Uri.fromFile(media);

                shareIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
                shareIntent.setType(videoType);
                getBaseActivity().startActivity(Intent.createChooser(shareIntent, "Share"));


            }
            break;
        }

        return true;
    }

}
