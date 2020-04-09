package com.bm.main.pos.feature.merchant

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.R
import com.bm.main.pos.rabbit.RabbitMqPrint
import com.bm.main.pos.utils.AppSession
import com.bm.main.pos.utils.Helper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.dialog_qr_merchant.*
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QRISDialog : DialogFragment() {
    companion object {
        const val TAG = "QRISDialog"

        fun newInstance(): QRISDialog =
            QRISDialog()

    }

    private val appSession = AppSession()
    private var qrBitmap: Bitmap? = null
    private var qrFile: File? = null
    private var qrFilePrint: File? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
        // Pick a style based on the num.
        val style = DialogFragment.STYLE_NO_FRAME
        val theme = R.style.DialogTheme
        setStyle(style, theme)
        dialog!!.setCancelable(true)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_qr_merchant, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(qr_img).asBitmap().load(PreferenceClass.getString("url_qr")).into(object: CustomTarget<Bitmap>(){
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                qrBitmap = resource
                //qrFile = FileUtils.bitmapToExternalFile(qr_img.context, resource, "MyQr.jpg")
                //qrFilePrint = FileUtils.bitmapToCacheFile(qr_img.context, FileUtils.resizeBitmap(resource, 300, 300), "MyQrSmall.jpg")
                qr_img.setImageBitmap(qrBitmap)
            }
        })

        store_name.text = PreferenceClass.getString("nama_toko").toUpperCase()
        store_nmid.text = PreferenceClass.getString("nmid").toUpperCase()

        print.setOnClickListener {
            it.isEnabled = false
            Toast.makeText(it.context, "Mencetak QR", Toast.LENGTH_SHORT).show()
            RabbitMqPrint.printStrukRabbit("", activity!!, qrFilePrint?.path) {
                print.isEnabled = true
                Toast.makeText(print.context, (if (it) "Berhasil" else "Gagal") + " mencetak QR", Toast.LENGTH_SHORT).show()
            }
        }

        download.setOnClickListener {
            it.isEnabled = false
            qrFile?.let {
                Toast.makeText(print.context, "QR tersimpan di ${it.path}", Toast.LENGTH_SHORT).show()
            } ?: run {
                Toast.makeText(print.context, "Gagal menyimpan QR. Mohon ulangi lagi", Toast.LENGTH_SHORT).show()
            }
            it.isEnabled = true
        }
    }

    override fun onCancel(dialog: DialogInterface) {
      dialog.dismiss()
    }
}