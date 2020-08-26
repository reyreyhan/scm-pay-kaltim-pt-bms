package com.bm.main.scm.feature.report.stock

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bm.main.scm.R
import com.bm.main.scm.models.report.ReportStock
import com.bm.main.scm.utils.AppConstant
import kotlinx.android.synthetic.main.dialog_report_stock_update_new.*


class StockProductDialog : DialogFragment() {
    private var mListener: Listener? = null

    var stock = ""

    companion object {
        const val TAG = "StockProductDialog"

        fun newInstance(): StockProductDialog =
            StockProductDialog()

    }

    interface Listener {
        fun onUpdateStock(id:String, stock:String)
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
        return inflater.inflate(R.layout.dialog_report_stock_update_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments!!.getSerializable(AppConstant.DATA) as ReportStock
        tv_title_product.text = data.nama_barang!!
        tv_current_stock.text = data.stok!!
        et_add_stock.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                var additional = 0
                var newValue = 0
                if (!s.isNullOrEmpty()){
                    additional = s.toString().trim().toInt()
                    newValue = tv_current_stock.text!!.toString().toInt() + additional
                    stock = newValue.toString()
                }
                et_total_stock.text = "$newValue"
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        btn_update_stock.setOnClickListener {
            mListener?.onUpdateStock(data.id_barang!!, stock)
            dismiss()
        }
        btn_close.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent != null) {
            mListener = parent as Listener
        } else {
            mListener = context as Listener
        }
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }
}