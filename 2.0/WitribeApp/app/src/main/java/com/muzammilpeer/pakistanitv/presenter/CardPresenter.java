package com.muzammilpeer.pakistanitv.presenter;

import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.muzammilpeer.pakistanitv.R;
import com.muzammilpeer.pakistanitv.view.TextDrawable;
import com.ranisaurus.newtorklayer.models.Data;

/**
 * Created by muzammilpeer on 1/2/16.
 */
public class CardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";

    private static int CARD_WIDTH = 313;
    private static int CARD_HEIGHT = 176;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;
    private Drawable mDefaultCardImage;

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");

        sDefaultBackgroundColor = parent.getResources().getColor(R.color.default_background);
        sSelectedBackgroundColor = parent.getResources().getColor(R.color.selected_background);
//        mDefaultCardImage = parent.getResources().getDrawable(R.drawable.movie);

        ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Data movie = (Data) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;

        String imageUrl = movie.image;
        String cellTitle = movie.displayTitle;
        String cellSubTitle = movie.name;
        if (imageUrl == null || imageUrl.length() < 1) {
            imageUrl = movie.mobile_small_image;
        }

        if (cellTitle == null || cellTitle.length() < 1) {
            cellTitle = movie.title;
        }

        if (cellSubTitle == null || imageUrl.length() < 1) {
            cellSubTitle = cellTitle;
        }

        Log.d(TAG, "onBindViewHolder");
        if (imageUrl != null) {
            cardView.setTitleText(cellTitle);
            cardView.setContentText(cellSubTitle);
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

            if (cellTitle != null && cellTitle.length() > 0) {
                mDefaultCardImage = new TextDrawable(String.valueOf(cellTitle.charAt(0)));
            }

            Glide.with(viewHolder.view.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        // Remove references to images so that the garbage collector can free up memory
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }
}
