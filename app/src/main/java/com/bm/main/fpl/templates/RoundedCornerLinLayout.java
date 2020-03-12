package com.bm.main.fpl.templates;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by sarifhidayat on 4/3/18.
 **/
public class RoundedCornerLinLayout extends LinearLayout {
    public static float radius = 18.0f;

    public RoundedCornerLinLayout(Context context) {
        super(context);
    }

    public RoundedCornerLinLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedCornerLinLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        //float radius = 36.0f;
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
