package com.bm.main.pos.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import timber.log.Timber
import java.io.*
import java.net.URL
import java.nio.charset.Charset

/**
 * Created by adriyoutomo on 23/07/2019.
 */
object FileUtils {

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {
        (context.assets).open(jsonFileName).let {
            val buffer = ByteArray(it.available())
            it.read(buffer)
            it.close()
            return String(buffer, Charset.forName("UTF-8"))
        }
    }

    fun loadImageFromURL(url: String?): Drawable? {
        try {
            val stream = URL(url).content as InputStream
            return Drawable.createFromStream(stream, "")
        } catch (e: Exception) {
            return null
        }
    }

    fun getScaledBitmap(b: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {
        val m = Matrix()
        m.setRectToRect(
            RectF(0f, 0f, b.width.toFloat(), b.height.toFloat()),
            RectF(0f, 0f, reqWidth.toFloat(), reqHeight.toFloat()),
            Matrix.ScaleToFit.CENTER
        )
        return Bitmap.createBitmap(b, 0, 0, b.width, b.height, m, true)
    }

    fun resizeBitmap(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = image.width
        val height = image.height
        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

        var finalWidth = maxWidth
        var finalHeight = maxHeight
        if (ratioMax > ratioBitmap) {
            finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
        }

        return Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
    }

    fun bitmapToCacheFile(context: Context, bitmap: Bitmap, fileName: String): File {
        val file = File(context.filesDir.apply { if (!exists()) mkdirs() }, fileName).apply { if (!exists()) createNewFile() }
        bitmapToFile(bitmap, file)
        return file
    }

    fun bitmapToExternalFile(context: Context, bitmap: Bitmap, fileName: String): File? = try {
        val file = File(getExternalDir(context), fileName).apply { if (!exists()) createNewFile() }
        bitmapToFile(bitmap, file)
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).setData(Uri.fromFile(file)))
        file
    } catch (e: IOException) {
        Timber.e(e, "BitmapFile - error: ${e.localizedMessage}")
        null
    }

    fun getExternalDir(context: Context): File = try {
        File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Profit")
    } catch (_: IOException) {
        context.filesDir
    }.apply { if (!exists()) mkdirs() }

    fun bitmapToFile(bitmap: Bitmap, file: File): Boolean {
        return try {
            ByteArrayOutputStream().also {
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, it)
                FileOutputStream(file).apply {
                    write(it.toByteArray())
                    flush()
                    close()
                }
            }
            true
        } catch (e: Exception) {
            Timber.e("failed to created file")
            Timber.e(e)
            false
        }
    }
}