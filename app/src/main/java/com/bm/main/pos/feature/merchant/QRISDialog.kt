package com.bm.main.pos.feature.merchant

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.R
import com.bm.main.pos.rabbit.RabbitMqPrint
import com.bm.main.pos.utils.AppSession
import com.bm.main.pos.utils.FileUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.dialog_qr_merchant.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class QRISDialog : DialogFragment() {
    companion object {
        const val TAG = "QRISDialog"

        fun newInstance(): QRISDialog =
            QRISDialog()

    }

    private val appSession = AppSession()
    private lateinit var qrBitmap: Bitmap
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
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_qr_merchant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        download.isEnabled = false
        print.isEnabled = false
        Glide.with(qr_img).asBitmap().load(PreferenceClass.getString("url_qr"))
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    qrBitmap = resource
                    saveQrToTemp(requireActivity(), qrBitmap)
//                qrFile = FileUtils.bitmapToExternalFile(qr_img.context, resource, "MyQr.jpg")
                    //qrFilePrint = FileUtils.bitmapToCacheFile(qr_img.context, FileUtils.resizeBitmap(resource, 300, 300), "MyQrSmall.jpg")
                    qr_img.setImageBitmap(qrBitmap)
                    download.isEnabled = true
                    print.isEnabled = true
                }
            })

        store_name.text = PreferenceClass.getString("nama_toko").toUpperCase()
        store_nmid.text = PreferenceClass.getString("nmid").toUpperCase()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let { act ->
            print.setOnClickListener { it ->
                it.isEnabled = false
                qrFilePrint?.let { file ->
                    Toast.makeText(it.context, "Mencetak QR", Toast.LENGTH_SHORT).show()
                    RabbitMqPrint.printStrukRabbit("", act, file.path) {
                        print.isEnabled = true
                        Toast.makeText(
                            print.context,
                            (if (it) "Berhasil" else "Gagal") + " mencetak QR",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            download.setOnClickListener {
                it.isEnabled = false

                if (ContextCompat.checkSelfPermission(
                        act,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        act,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    == PackageManager.PERMISSION_GRANTED
                ) {
//                    if (qrBitmap == null) {
//                        Log.e("PROFF", "BITMAP NULL, MAYBE EXIF ERROR")
//                    } else {
//                        Log.e("PROFF", "BITMAP IS NOT NULL")
//                    }

                    Toast.makeText(
                        act,
                        FileUtils.bitmapToExternalFile(qr_img.context, qrBitmap, "MyQr.jpg")
                            ?.let { "QR tersimpan di ${it.path}" }
                            ?: "Gagal menyimpan QR. Mohon ulangi lagi",
                        Toast.LENGTH_SHORT
                    ).show()
                    it.isEnabled = true
                } else {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        121
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 121) {
            Toast.makeText(
                requireContext(),
                FileUtils.bitmapToExternalFile(qr_img.context, qrBitmap, "MyQr.jpg")
                    ?.let { "QR tersimpan di ${it.path}" }
                    ?: "Gagal menyimpan QR. Mohon ulangi lagi",
                Toast.LENGTH_SHORT
            ).show()
            download.isEnabled = true
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCancel(dialog: DialogInterface) {
        dialog.dismiss()
    }

    private fun saveQrToTemp(fragmentActivity: FragmentActivity, bitmap: Bitmap) {
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        val bytes = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = "${fragmentActivity.cacheDir}${File.separator}qr.jpg"
        val f =  File(path)
        f.createNewFile()
        val fo = FileOutputStream(f)
        fo.write(bytes.toByteArray())
        fo.close()
        qrFilePrint = f
    }
}