package com.bm.main.pos.utils

import android.content.Context
import android.graphics.Bitmap
import android.media.ExifInterface
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest


/**
 * Created by adriyoutomo on 26/07/2019.
 */
class RotateBitmapTransformation(val context: Context, val orientation: Int) : BitmapTransformation() {

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val exifOrientationDegrees = getExifOrientationDegrees(orientation)
        return TransformationUtils.rotateImageExif(pool, toTransform, exifOrientationDegrees)
    }

    private fun getExifOrientationDegrees(orientation: Int): Int {
        val exifInt: Int
        when (orientation) {
            90 -> exifInt = ExifInterface.ORIENTATION_ROTATE_90
            // other cases
            else -> exifInt = ExifInterface.ORIENTATION_NORMAL
        }
        return exifInt
    }
}