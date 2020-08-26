package com.bm.main.scm.feature.sell.main

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
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.R
import com.bm.main.scm.utils.AppSession
import com.bm.main.scm.utils.Helper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.dialog_transaction_qris.*
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NonTunaiDialog : DialogFragment() {
    companion object {
        const val TAG = "NonTunaiDialog"

        fun newInstance(): NonTunaiDialog =
            NonTunaiDialog()

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
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_transaction_qris, container, false)
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

        tv_nama_toko.text = PreferenceClass.getString("nama_toko").toUpperCase()
        tv_store_nmid.text = PreferenceClass.getString("nmid").toUpperCase()
        tv_id.text = PreferenceClass.getString("nmid").toUpperCase()
        tv_total_belanja.text = arguments!!.getString("JumlahPembayaran")
        val now = org.threeten.bp.LocalDate.now()
        val currentTime = LocalDateTime.now()
        val dtf =  DateTimeFormatter.ofPattern("HH:mm")
        tv_date.text = Helper.getDateFormat(requireContext(), now.toString(),"yyyy-MM-dd","EEE, dd MMMM yyyy")
        tv_time.text = dtf.format(currentTime)
        btn_bayar.setOnClickListener {
            val newIntent: Intent = activity!!.intent
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, activity!!.intent)
            dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        val newIntent: Intent = activity!!.intent
        targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, activity!!.intent)
        super.onCancel(dialog)
    }
}