package com.muzammilpeer.smarttvapp.cell;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.muzammilpeer.smarttvapp.R;
import com.muzammilpeer.smarttvapp.fragment.mobile.FirstLevelNavigationFragment;
import com.ranisaurus.baselayer.cell.BaseCell;
import com.ranisaurus.newtorklayer.models.Data;
import com.ranisaurus.newtorklayer.models.SampleModel;
import com.ranisaurus.utilitylayer.base.BaseModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by muzammilpeer on 12/26/15.
 */
public class HomePagerCell extends BaseCell implements View.OnClickListener {

    @Bind(R.id.pb_thumbnail)
    ProgressBar pbThumbnail;

    @Bind(R.id.mainHolder)
    LinearLayout placeHolder;

    @Bind(R.id.placeNameHolder)
    LinearLayout placeNameHolder;

    @Bind(R.id.iv_thumbnail)
    ImageView ivThumbnail;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.tv_subtitle)
    TextView tvSubTitle;


    int paletteColor = -1;

    public HomePagerCell(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

    }

    @Override
    public void updateCell(BaseModel model) {
        position = this.getAdapterPosition();
        mDataSource = model;

        if (mDataSource instanceof SampleModel) {
            SampleModel data = (SampleModel) model;

            tvTitle.setText(data.getTitle());
            placeNameHolder.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.colorPrimaryDark));
            Picasso.with(getBaseActivity()).load(R.drawable.canada).into(ivThumbnail, new Callback() {
                @Override
                public void onSuccess() {
                    if (pbThumbnail != null) {
                        pbThumbnail.setVisibility(GONE);
                    }
                    if (ivThumbnail != null) {
                        ivThumbnail.setVisibility(VISIBLE);
                    }
                }

                @Override
                public void onError() {
                    if (pbThumbnail != null) {
                        pbThumbnail.setVisibility(GONE);
                    }
                    if (ivThumbnail != null) {
                        ivThumbnail.setVisibility(VISIBLE);

                    }
                }
            });

            if (paletteColor == -1) {
                Bitmap photo = BitmapFactory.decodeResource(getBaseActivity().getResources(), R.drawable.canada);
                Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        paletteColor = palette.getMutedColor(getBaseActivity().getResources().getColor(android.R.color.black));
                        placeNameHolder.setBackgroundColor(paletteColor);
                    }
                });
            } else {
                placeNameHolder.setBackgroundColor(paletteColor);
            }
        } else if (mDataSource instanceof Data) {
            Data data = (Data) model;
            if (data.displayTitle != null && data.displayTitle.length() > 0) {
                tvTitle.setText(data.displayTitle);
            } else {
                tvTitle.setText(data.title);
            }

            tvSubTitle.setText(data.name);

            String imageUrl = data.image;
            if (imageUrl == null) {
                imageUrl = ("http://pitelevision.com/" + data.mobile_small_image);
            }

            placeNameHolder.setBackgroundColor(getBaseActivity().getResources().getColor(R.color.colorPrimaryDark));

            Picasso.with(getBaseActivity())
                    .load(imageUrl)
                    .resize(mAdapter.getCurrentColumnWidth(), mAdapter.getCurrentColumnWidth())
                    .centerInside()
                    .into(ivThumbnail, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (paletteColor == -1) {
                                Bitmap photo = ((BitmapDrawable) ivThumbnail.getDrawable()).getBitmap();
                                Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette palette) {
                                        paletteColor = palette.getMutedColor(getBaseActivity().getResources().getColor(android.R.color.black));
                                        placeNameHolder.setBackgroundColor(paletteColor);
                                    }
                                });

                            } else {
                                placeNameHolder.setBackgroundColor(paletteColor);
                            }

                            if (pbThumbnail != null) {
                                pbThumbnail.setVisibility(GONE);
                            }
                            if (ivThumbnail != null) {
                                ivThumbnail.setVisibility(VISIBLE);
                            }

                        }

                        @Override
                        public void onError() {
                            if (pbThumbnail != null) {
                                pbThumbnail.setVisibility(GONE);
                            }
                            if (ivThumbnail != null) {
                                ivThumbnail.setVisibility(VISIBLE);

                            }
                        }
                    });
        } else {
            ivThumbnail.setImageBitmap(null);
            tvTitle.setText(null);
            tvSubTitle.setText(null);
        }

    }

    @Override
    public void onClick(View v) {
        if (mDataSource instanceof Data) {
            Data data = (Data) mDataSource;
            FirstLevelNavigationFragment fragment = FirstLevelNavigationFragment.newInstance(data);
            getBaseActivity().replaceFragment(fragment, R.id.framelayout_dashboard);
        }
    }
}