package com.bm.main.fpl.templates;

/**
 * Created by sarifhidayat on 3/26/18.
 **/

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;

public class RoundedCornersDrawable extends BitmapDrawable {

    @NonNull
    private final BitmapShader bitmapShader;
    private final Paint p;
    @NonNull
    private final RectF rect;
    private  float borderRadius=20f;

    public RoundedCornersDrawable(final Resources resources, final Bitmap bitmap) {
        super(resources, bitmap);
//        bitmapShader = new BitmapShader(getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader = new BitmapShader(getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        final Bitmap b = getBitmap();
        p = getPaint();
        p.setAntiAlias(true);
        p.setShader(bitmapShader);
        final int w = b.getWidth();
        final int h = b.getHeight();
        rect = new RectF(0, 0, w, h);
       // borderRadius = 10f;//0.15f * Math.min(w, h);
        borderRadius = borderRadius < 0 ? 20.0f * Math.min(w, h) : borderRadius;
    }

    @Override
    public void draw(@NonNull final Canvas canvas) {
        canvas.drawRoundRect(rect, borderRadius, borderRadius, p);
    }
}
