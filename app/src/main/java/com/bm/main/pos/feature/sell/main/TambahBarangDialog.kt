package com.bm.main.pos.feature.sell.main

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bm.main.pos.R
import com.bm.main.pos.ui.ext.htmlText
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.dialog_tambah_barang.*

class TambahBarangDialog : DialogFragment() {
    companion object {
        const val TAG = "TambahBarangDialog"

        fun newInstance(): TambahBarangDialog =
            TambahBarangDialog()

    }

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
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_tambah_barang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_caution.htmlText("Lakukan pendaftaran barang dahulu,\n" +
                "melalui Menu <b>Tokoku -> Tambah Barang</b>")
        btn_daftar_barang.setOnClickListener {
            val newIntent: Intent = activity!!.intent
            newIntent.putExtra(AppConstant.DATA, arguments!!.getString(AppConstant.DATA))
            targetFragment!!.onActivityResult(targetRequestCode, 101, activity!!.intent)
            dismiss()
        }
        btn_scan_barang.setOnClickListener {
            val newIntent: Intent = activity!!.intent
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, activity!!.intent)
            dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        val newIntent: Intent = activity!!.intent
        targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, activity!!.intent)
        super.onCancel(dialog)
    }
}