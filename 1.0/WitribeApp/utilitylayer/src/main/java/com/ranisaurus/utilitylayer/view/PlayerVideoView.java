package com.ranisaurus.utilitylayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by muzammilpeer on 11/4/15.
 */
public class PlayerVideoView extends VideoView {

    private int mForceHeight = 0;
    private int mForceWidth = 0;
    public PlayerVideoView(Context context) {
        super(context);
    }

    public PlayerVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setDimensions(int w, int h) {
        this.mForceHeight = h;
        this.mForceWidth = w;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mForceWidth, mForceHeight);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}