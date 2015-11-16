package com.witribe.witribeapp.cell;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.enums.NetworkRequestEnum;
import com.ranisaurus.newtorklayer.manager.NetworkManager;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.DataBooleanResponseModel;
import com.ranisaurus.newtorklayer.protocols.IResponseProtocol;
import com.ranisaurus.newtorklayer.requests.BaseNetworkRequest;
import com.ranisaurus.newtorklayer.requests.WitribeAMFRequest;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.ranisaurus.utilitylayer.logger.Log4a;
import com.ranisaurus.utilitylayer.network.GsonUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.witribe.witribeapp.R;
import com.witribe.witribeapp.fragment.WebViewFragment;
import com.witribe.witribeapp.manager.UserManager;
import com.witribe.witribeapp.view.SquareImageView;

import butterknife.Bind;

/**
 * Created by muzammilpeer on 11/14/15.
 */
public class FavouriteListCell extends BaseCell implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, IResponseProtocol {

    @Bind(R.id.iv_programme_image)
    SquareImageView ivProgrammeImage;

    @Bind(R.id.tv_programme_name)
    TextView tvProgrammeName;

    @Bind(R.id.tv_programme_category)
    TextView tvProgrammeCategory;


    @Bind(R.id.tv_programme_description)
    TextView tvProgrammeDescription;

    @Bind(R.id.pb_programme_image)
    ProgressBar pbProgrammeImage;

    @Bind(R.id.iv_context_menu)
    ImageView ivContextMenu;

    PopupMenu popup;


    public FavouriteListCell(View itemView) {
        super(itemView);

        ivContextMenu.setOnClickListener(this);
        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {
        position = this.getAdapterPosition();

        mDataSource = model;

        if (model instanceof Data) {
            Data dataSource = (Data) model;

            tvProgrammeCategory.setText(dataSource.category.toUpperCase());
            tvProgrammeDescription.setText(dataSource.description);
            tvProgrammeName.setText(dataSource.title.toUpperCase());

            String imageUrl = "";
            if (dataSource.mobile_small_image.contains("http")) {
                imageUrl = dataSource.mobile_small_image.replaceAll(" ", "%20");
            } else {
                imageUrl = ("http://pitelevision.com/" + dataSource.mobile_small_image).replaceAll(" ", "%20");
            }
            Log4a.e("Favourite Image = ", imageUrl);
            Picasso.with(itemView.getContext())
                    .load(imageUrl)
                    .resize((int) itemView.getResources().getDimension(R.dimen.cardview_thumbnail_height), (int) itemView.getResources().getDimension(R.dimen.cardview_thumbnail_height))
                    .centerInside()
                    .into(ivProgrammeImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbProgrammeImage.setVisibility(View.GONE);
                            ivProgrammeImage.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            pbProgrammeImage.setVisibility(View.GONE);
                            ivProgrammeImage.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            ivProgrammeImage.setImageBitmap(null);
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
            Data selectedModel = (Data) mDataSource;

            if (selectedModel.videoURL.contains("manifest.f4m?")) {
                Snackbar.make(itemView, "Cannot play live stream", Snackbar.LENGTH_SHORT).show();
            } else {
                WebViewFragment fragment = WebViewFragment.newInstance(selectedModel);
                getBaseActivity().replaceFragment(fragment, R.id.container_main);
            }
        }
    }

    private void deleteFavouritesData(String selectedID) {
        String[] params = new String[3];
        params[0] = UserManager.getInstance().getUserProfile().getUserId();
        params[1] = selectedID;
        params[2] = "1";

        WitribeAMFRequest request = new WitribeAMFRequest(params, NetworkRequestEnum.DELETE_FAVOURITE_LISTING);
        try {
            NetworkManager.getInstance().executeRequest(request, this);

        } catch (Exception e) {
            Log4a.printException(e);
        }

    }

    @Override
    public void responseWithError(Exception error, BaseNetworkRequest request) {
        try {
            if (itemView != null) {
                switch (request.getNetworkRequestEnum()) {
                    case DELETE_FAVOURITE_LISTING: {
                        Snackbar.make(itemView, "Error in delete", Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }


    }

    @Override
    public void successWithData(Object data, BaseNetworkRequest request) {

        try {
            if (itemView != null) {
                switch (request.getNetworkRequestEnum()) {

                    case DELETE_FAVOURITE_LISTING: {
                        DataBooleanResponseModel model = (DataBooleanResponseModel) GsonUtil.getObjectFromJsonObject(data, DataBooleanResponseModel.class);
                        Log4a.e("Response ", model.toString() + "");
                        if (model.getData().equalsIgnoreCase("true")) {
                            Snackbar.make(itemView, "Successfully deleted", Snackbar.LENGTH_SHORT).show();
                            Data selectedModel = (Data) mDataSource;
                            mAdapter.getmObjects().remove(selectedModel);
                            mAdapter.notifyItemRemoved((int) position);
                        } else {
                            Snackbar.make(itemView, "Error in delete", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                    break;
                }
            }
        } catch (Exception e) {
            Log4a.printException(e);
        }


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete_file: {
                Data dataSource = (Data) mDataSource;
                deleteFavouritesData(dataSource.favouriteId);
            }
            break;
            case R.id.menu_share_file: {
                Data dataSource = (Data) mDataSource;
                Snackbar.make(itemView, "Share feature add here", Snackbar.LENGTH_SHORT).show();
            }
            break;
        }

        return true;
    }

}