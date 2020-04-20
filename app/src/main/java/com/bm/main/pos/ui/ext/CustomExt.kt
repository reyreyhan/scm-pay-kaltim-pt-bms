package com.bm.main.pos.ui.ext

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bm.main.pos.R
import com.bm.main.pos.callback.DialogCallback
import com.bm.main.pos.utils.MyTagHandler
import com.bm.main.pos.ui.ext.setColorFilter
import com.bm.main.pos.ui.ext.toGone
import com.bm.main.pos.ui.ext.toVisible


fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun Fragment.alert(msg: String) {
    val builder = AlertDialog.Builder(activity!!)
    builder.setTitle("Peringatan")
    builder.setMessage(msg)
    builder.setCancelable(false)
    builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
    }
    builder.show()
}

fun AppCompatActivity.alert(context: Context, msg: String) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Peringatan")
    builder.setMessage(msg)
    builder.setCancelable(false)
    builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
    }
    builder.show()
}

fun Fragment.successDialog(msg: String, callback: DialogCallback) {
    val builder = AlertDialog.Builder(activity!!, R.style.AlertDialogTheme)
    builder.setTitle("Berhasil")
    builder.setMessage(msg)
    builder.setCancelable(false)
    builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
        callback.onSuccess()
    }
    builder.show()
}

fun AppCompatActivity.successDialog(context: Context, msg: String, callback: DialogCallback) {
    val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
    builder.setTitle("Berhasil")
    builder.setMessage(msg)
    builder.setCancelable(false)
    builder.setPositiveButton("OK") { dialog, _ ->
        dialog.dismiss()
        callback.onSuccess()
    }
    builder.show()
}

fun Fragment.toast(msg: String) {
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
//    alert(msg)
}

fun AppCompatActivity.toast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//    alert(context, msg)
}

fun TextView.htmlText(text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY, null, MyTagHandler()))
    } else {
//        setText(Html.fromHtml(text))
        setText(
            Html.fromHtml(text, null, MyTagHandler())
        )
    }
}

fun Drawable.setColorFilter(color: Int) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        this.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
//    } else {
//        @Suppress("DEPRECATION")
//        this.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
//    }
    @Suppress("DEPRECATION")
    this.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}

//fun Drawable.setColorFilter(color: Int, blendMode: BlendMode, porterMode: PorterDuff.Mode) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//        this.colorFilter = BlendModeColorFilter(color, blendMode)
//    } else {
//        @Suppress("DEPRECATION")
//        this.setColorFilter(color, porterMode)
//    }
//}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
