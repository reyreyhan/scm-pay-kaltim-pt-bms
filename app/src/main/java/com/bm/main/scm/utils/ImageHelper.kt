package com.bm.main.scm.utils

import android.content.Context
import android.graphics.*
import androidx.exifinterface.media.ExifInterface;
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import android.provider.MediaStore
import android.graphics.Bitmap
import android.os.Environment
import androidx.annotation.Keep
import androidx.core.widget.NestedScrollView
import timber.log.Timber
import java.io.*
import kotlin.math.roundToInt


/**
 * Created by adriyoutomo on 26/10/2018.
 */
@Keep
object ImageHelper {

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        img.recycle()
        return rotatedImg
    }

    /**
     * Calculate an inSampleSize for use in a [BitmapFactory.Options] object when decoding
     * bitmaps using the decode* methods from [BitmapFactory]. This implementation calculates
     * the closest inSampleSize that will result in the final decoded bitmap having a width and
     * height equal to or larger than the requested width and height. This implementation does not
     * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
     * results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options   An options object with out* params already populated (run through a decode*
     * method with inJustDecodeBounds==true
     * @param reqWidth  The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int, reqHeight: Int
    ): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
            val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            val totalPixels = (width * height).toFloat()

            // Anything more than 2x the requested pixels we'll sample down further
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }
        }
        return inSampleSize
    }

    /**
     * https://www.samieltamawy.com/how-to-fix-the-camera-intent-rotated-image-in-android/
     * This method is responsible for solving the rotation issue if exist. Also scale the images to
     * 1024x1024 resolution
     *
     * @param context       The current context
     * @param selectedImage The Image URI
     * @return Bitmap image results
     * @throws IOException
     */
    @Throws(IOException::class)
    fun handleSamplingAndRotationBitmap(context: Context, selectedImage: Uri): Bitmap? {
        val maxheight = 1024
        val maxwidth = 1024

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var imageStream = context.contentResolver.openInputStream(selectedImage)
        BitmapFactory.decodeStream(imageStream, null, options)
        imageStream?.close()

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, maxwidth, maxheight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        imageStream = context.contentResolver.openInputStream(selectedImage)
        var img = BitmapFactory.decodeStream(imageStream, null, options)
        img = rotateImageIfRequired(img!!, selectedImage)
        imageStream?.close()
        return img
    }

    /**
     * Rotate an image if required.
     *
     * @param img           The image bitmap
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    @Throws(IOException::class)
    fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap {
        val ei = ExifInterface(selectedImage.path!!)
        return when {
            ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            ) == ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 180)
            ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            ) == ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
            ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            ) == ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 0)
            else -> {
                img
            }
        }
    }

    fun getBitmap(file: File): Bitmap {
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    fun getCameraPhotoOrientation(context: Context, imageFile: File): Int {
        var rotate = 0
        try {
            context.contentResolver.notifyChange(Uri.fromFile(imageFile), null)

            val exif = ExifInterface(imageFile.absolutePath)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
            }

            Log.i("RotateImage", "Exif orientation: $orientation")
            Log.i("RotateImage", "Rotate value: $rotate")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return rotate
    }

    fun takeScreenshot(
        context: Context,
        nestedScrollView: NestedScrollView,
        filename: String = "",
        then: (path: String) -> Unit = {}
    ) {

        //get screenshot scrollview
        val totalHeight = nestedScrollView.getChildAt(0).height ?: 0
        val totalWidth = nestedScrollView.getChildAt(0).width ?: 0

        val mBitmap = getBitmapFromView(nestedScrollView, totalHeight, totalWidth)

        //Save bitmap
        if (mBitmap != null) {
            val direct =
                File("${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}").also { if (!it.exists()) it.mkdir() }
            val fileName =
                if (filename.isEmpty()) "img_" + System.currentTimeMillis().toString() + ".jpg" else filename
            val myPath = File(direct, fileName)
            val fos: FileOutputStream?
            try {
                fos = FileOutputStream(myPath)
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
                val path = MediaStore.Images.Media.insertImage(
                    context.getContentResolver(),
                    mBitmap,
                    "Fastpay",
                    "Fastpay"
                )

                //share bitmap from uri
                then(path)
                return
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        Toast.makeText(context, "Gagal menyimpan gambar", Toast.LENGTH_SHORT).show()
    }

    fun scaleImage(
        context: Context,
        path: String,
        maxWidth: Int = 600,
        maxHeight: Int = 600,
        filename: String,
        then: (path: String) -> Unit
    ) {
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        val bitmap = BitmapFactory.decodeFile(path, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth

        var imgRatio = actualWidth.toFloat() / actualHeight.toFloat()
        val maxRatio = maxWidth / maxHeight

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = (maxHeight / actualHeight).toFloat()
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight
            } else if (imgRatio > maxRatio) {
                imgRatio = (maxWidth / actualWidth).toFloat()
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth
            } else {
                actualHeight = maxHeight
                actualWidth = maxWidth
            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
        options.inJustDecodeBounds = false
        options.inTempStorage = ByteArray(16 * 1024)

        var scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565)

        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix().apply { setScale(ratioX, ratioY, middleX, middleY) }

        val canvas = Canvas(scaledBitmap)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(
            bitmap,
            middleX - options.outWidth / 2,
            middleY - options.outHeight / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )
        bitmap.recycle()

        val exif: ExifInterface
        try {
            exif = ExifInterface(path)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            Timber.d("rt: $orientation")
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
            bitmapToCacheFile(context, scaledBitmap, filename, then)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun bitmapToCacheFile(context: Context, bitmap: Bitmap, filename: String, then: (path: String) -> Unit = {}) {
        val file = File(context.filesDir, filename).apply { if (!exists()) createNewFile() }

        BufferedOutputStream(FileOutputStream(file)).apply {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
            close()
        }

//        val bos = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos)
//
//        FileOutputStream(file).apply {
//            write(bos.toByteArray())
//            flush()
//            close()
//        }
        then(file.path)
    }

    private fun getBitmapFromView(view: View, totalHeight: Int, totalWidth: Int): Bitmap? {
        return try {
            val returnedBitmap =
                Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(returnedBitmap)
            val bgDrawable = view.background
            if (bgDrawable != null)
                bgDrawable.draw(canvas)
            else
                canvas.drawColor(Color.WHITE)
            view.draw(canvas)
            returnedBitmap
        } catch (e: Exception) {
            null
        }

    }
}
