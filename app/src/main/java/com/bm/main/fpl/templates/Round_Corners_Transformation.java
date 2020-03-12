package com.bm.main.fpl.templates;

/**
 * Created by sarifhidayat on 3/26/18.
 **/
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;

import java.security.MessageDigest;

//import com.squareup.picasso.Transformation;

public class Round_Corners_Transformation implements Transformation {
    private int Round;

    public Round_Corners_Transformation(){

    }

    public Round_Corners_Transformation(int margin, int Round) {
        this.Round = Round;

    }

//    @Override
//    public String key() {
//        return "Round" + Round;
//    }
//
//    @Override
//    public Bitmap transform(Bitmap arg0) {
//        // TODO Auto-generated method stub
//        return getRoundedTopLeftCornerBitmap(arg0);
//    }

    public Bitmap getRoundedTopLeftCornerBitmap(@NonNull Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float Px = Round;

        final Rect bottomRect = new Rect(0, bitmap.getHeight() / 2,
                bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, Px, Px, paint);
        // Fill in upper right corner
        // canvas.drawRect(topRightRect, paint);
        // Fill in bottom corners
        canvas.drawRect(bottomRect, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if (bitmap != output) {
            bitmap.recycle();
        }
        return output;
    }

//    @Nullable
//    @Override
//    public Resource transform(Resource resource, int outWidth, int outHeight) {
//        return null;
//    }

//    @Nullable
//    @Override
//    public String getId() {
//        return null;
//    }

    @NonNull
    @Override
    public Resource transform(@NonNull Context context, @NonNull Resource resource, int outWidth, int outHeight) {
        return null;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
