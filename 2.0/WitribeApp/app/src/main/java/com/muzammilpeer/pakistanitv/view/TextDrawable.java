package com.muzammilpeer.pakistanitv.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by muzammilpeer on 1/2/16.
 */
public class TextDrawable extends Drawable {

    private final String text;
    private final Paint paint;

    public TextDrawable(String text) {
        this.text = text;
        this.paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(280f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect r = getBounds();
        int count = canvas.save();
        canvas.translate(r.left, r.top);
        float midW = r.width() / 2;
        float midH = r.height() / 2 - ((paint.descent() + paint.ascent()) / 2);
        canvas.drawText(text, midW, midH, paint);
        canvas.restoreToCount(count);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}