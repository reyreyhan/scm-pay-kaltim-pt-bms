package com.bm.main.pos.utils

import android.content.Context
import android.graphics.*
import androidx.exifinterface.media.ExifInterface
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import timber.log.Timber
import java.io.File
import java.io.IOException
import kotlin.math.roundToInt

/**
 * Created by adriyoutomo on 23/07/2019.
 */
//https://abdelhady.net/2015/03/28/android-loading-images-super-fast-like-whatsapp-part-2/

open class ImageCompression(private val context: Context) : AsyncTask<String, Void, String>() {

    // Create the storage directory if it does not exist
    val filename: String
        get() {
            val mediaStorageDir = File(
                Environment.getExternalStorageDirectory().toString()
                        + "/Android/data/"
                        + context.applicationContext.packageName
                        + "/Files/Compressed"
            )
//            val mediaStorageDir = File(
//                Environment.getExternalStorageDirectory().toString(),"/Profit/img/"

//                      )
            //  dir = new File(path, "/Profit/struk
            if (!mediaStorageDir.exists()) {
                mediaStorageDir.mkdirs()
            }

            val mImageName = "IMG_" + System.currentTimeMillis().toString() + ".jpg"
//            val file = File(mediaStorageDir, mImageName)
//            return file.absolutePath
            return mediaStorageDir.absolutePath + "/" + mImageName

        }

    override fun doInBackground(vararg strings: String): String? {
        return if (strings.isEmpty()) null else compressImage(strings[0])

    }

    override fun onPostExecute(imagePath: String) {
        // imagePath is path of new compressed image.
    }


    fun compressImage(imagePath: String): String {
        Timber.d("image Compression $imagePath")
        var scaledBitmap: Bitmap? = null

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bmp: Bitmap? = BitmapFactory.decodeFile(imagePath, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth

        var imgRatio = actualWidth.toFloat() / actualHeight.toFloat()
        val maxRatio = maxWidth / maxHeight

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
        options.inJustDecodeBounds = false
        options.inTempStorage = ByteArray(16 * 1024)

        try {
            bmp = BitmapFactory.decodeFile(imagePath, options)
            if (actualWidth < 1) actualWidth = 200
            if (actualHeight < 1) actualHeight = 200
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565)


            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f

            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

            val canvas = Canvas(scaledBitmap!!)
            canvas.setMatrix(scaleMatrix)
            canvas.drawBitmap(
                bmp!!,
                middleX - bmp.width / 2,
                middleY - bmp.height / 2,
                Paint(Paint.FILTER_BITMAP_FLAG)
            )

            bmp?.recycle()

            val exif: ExifInterface
            exif = ExifInterface(imagePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            Log.d("rt", "$orientation")
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            }
            scaledBitmap = Bitmap.createBitmap(
                scaledBitmap,
                0,
                0,
                scaledBitmap.width,
                scaledBitmap.height,
                matrix,
                true
            )
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }
        /* var out: FileOutputStream? = null
         val filepath = filename
         try {
             out = FileOutputStream(filepath)

             //write the compressed bitmap at the destination specified by filename.
             scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)

         } catch (e: FileNotFoundException) {
             e.printStackTrace()
         }*/

        return imagePath
    }

    companion object {
        private const val maxHeight = 320.0f
        private const val maxWidth = 320.0f

        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
                val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = (width * height).toFloat()
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }

            return inSampleSize
        }
    }

}