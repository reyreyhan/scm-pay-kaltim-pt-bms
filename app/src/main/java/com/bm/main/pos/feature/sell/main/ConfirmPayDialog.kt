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
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.dialog_penjualan_confirm.*

class ConfirmPayDialog : DialogFragment() {
    companion object {
        const val TAG = "ConfirmPayDialog"

        fun newInstance(): ConfirmPayDialog =
            ConfirmPayDialog()

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
        return inflater.inflate(R.layout.dialog_penjualan_confirm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_total_belanja.text = arguments!!.getString("JumlahBarang")
        tv_uang_diterima.text = arguments!!.getString("JumlahPembayaran")
        tv_uang_kembalian.text = arguments!!.getString("Cashback")
        btn_update_barang.setOnClickListener {
            val newIntent: Intent = activity!!.intent
            targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, activity!!.intent)
            dismiss()
        }
        btn_cancel.setOnClickListener {
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