package com.bm.main.pos.feature.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.R
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.models.transaction.Transaction
import kotlinx.android.synthetic.main.fragment_pay_dialog.*
import org.threeten.bp.LocalDate
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import com.bm.main.pos.ui.NumberTextWatcher
import kotlinx.android.synthetic.main.fragment_cart_count_dialog.*
import kotlinx.android.synthetic.main.fragment_pay_dialog.btn_save
import kotlinx.android.synthetic.main.fragment_pay_dialog.close_btn
import kotlinx.android.synthetic.main.fragment_pay_dialog.et_count


class PaymentDialog : BottomSheetDialogFragment() {
    private var mListener: Listener? = null
    private var selected: DetailTransaction?= null
    private var type = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay_dialog, container, false)
    }

    fun setListener(listener: Listener){
        mListener = listener
    }

    fun setData(selected:DetailTransaction,type:Int){
        this.selected = selected
        this.type = type
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (context == null) return

        et_count.addTextChangedListener(NumberTextWatcher(et_count))
        var tagihan = 0.0
        selected?.let {trx ->
            trx.struk?.let {struk ->
                struk.no_invoice?.let {
                    tv_id.text = it
                }

                if(type == AppConstant.Code.CODE_TRANSACTION_SUPPLIER){
                    lbl_name.text = "Supplier"
                    struk.nama_supplier?.let {
                        tv_name.text = it
                    }
                }
                else{
                    lbl_name.text = "Pelanggan"
                    struk.nama_pelanggan?.let {
                        tv_name.text = it
                    }
                }

                struk.kembalian?.let {
                    tagihan = it.toDouble()*-1
                    tv_total.text = "Rp ${Helper.convertToCurrency(tagihan)}"
                }
            }
        }

        btn_save.setOnClickListener {
            val text = et_count.text.toString().trim().replace(".","")
            if(text.isBlank() || text.isEmpty() || text == "0"){
                Toast.makeText(activity!!,"Jumlah pembayaran harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val value = text.toDouble()
            if(value > tagihan){
                Toast.makeText(activity!!,"Jumlah pembayaran melebihi tagihan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mListener?.onPay(selected!!,type,value.toString())
            dismiss()
        }

        close_btn.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        mListener = if (parent != null) {
            parent as Listener
        } else {
            context as Listener
        }
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        return dialog
    }

    interface Listener {
        fun onPay(selected:DetailTransaction,type:Int, value:String)
    }

    companion object {
        const val TAG = "PaymentDialog"

        fun newInstance(): PaymentDialog =
           PaymentDialog().apply {
                arguments = Bundle().apply {
                }
            }

    }
}
