package com.bm.main.scm.feature.qris

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.R
import com.bm.main.scm.rabbit.RabbitMqPrint
import com.bm.main.scm.utils.FileUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.my_qr_fragment.*
import java.io.File

class MyQrFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? = inflater.inflate(R.layout.my_qr_fragment, container, false)

    private var qrBitmap: Bitmap? = null
    private var qrFile: File? = null
    private var qrFilePrint: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(qr_img).asBitmap().load(PreferenceClass.getString("url_qr")).into(object: CustomTarget<Bitmap>(){
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                qrBitmap = resource
                qrFile = FileUtils.bitmapToExternalFile(qr_img.context, resource, "MyQr.jpg")
                qrFilePrint = FileUtils.bitmapToCacheFile(qr_img.context, FileUtils.resizeBitmap(resource, 300, 300), "MyQrSmall.jpg")
                qr_img.setImageBitmap(qrBitmap)
            }
        })

        store_name.text = PreferenceClass.getString("nama_toko").toUpperCase()
        store_nmid.text = PreferenceClass.getString("nmid").toUpperCase()

        print.setOnClickListener {
            it.isEnabled = false
            Toast.makeText(it.context, "Mencetak QR", Toast.LENGTH_SHORT).show()
            RabbitMqPrint.printStrukRabbit("", requireActivity(), qrFilePrint?.path) {
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
}