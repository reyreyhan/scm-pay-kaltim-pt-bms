package com.bm.main.scm.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.*;
import android.media.ExifInterface;
import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by Adri D. Utomo on 30/09/18.
 */
public class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {
    @SuppressLint("StaticFieldLeak")
    private String mCurrentPhotoPath;
    private ProgressDialog progressDialog;
    private ImageTaskListener imageListener;

    public ImageCompressionAsyncTask(String currentPhotoPath, ImageTaskListener imageListener) {
        mCurrentPhotoPath = currentPhotoPath;
        this.imageListener = imageListener;
        if (imageListener != null) {
            imageListener.onStart();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        return compressImage();
    }

    private String compressImage() {

        String filePath = mCurrentPhotoPath;
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));


        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
//            Timber.d("Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
//                Timber.d("Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
//                Timber.d("Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
//                Timber.d("Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        String filename = mCurrentPhotoPath;
        try {
            out = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (imageListener != null) imageListener.onFinish();
        mCurrentPhotoPath = result;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmaps = BitmapFactory.decodeFile(result, bmOptions);
        if (bitmaps != null && imageListener != null) {
            imageListener.onSuccess();
        } else {
            imageListener.onError();
        }

    }

    public interface ImageTaskListener {
        void onStart();

        void onFinish();

        void onSuccess();

        void onError();
    }
}
