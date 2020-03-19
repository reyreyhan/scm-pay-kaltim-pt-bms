package com.bm.main.pos.feature.sell.main

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bm.main.pos.R
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.dialog_penjualan_edit_product.*
import org.jetbrains.anko.windowManager


class ProductDialog : DialogFragment() {
    companion object {
        const val TAG = "ProductDialog"

        fun newInstance(): ProductDialog =
            ProductDialog()

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
        return inflater.inflate(R.layout.dialog_penjualan_edit_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cart = arguments!!.getSerializable(AppConstant.DATA) as Cart
        val position = arguments!!.getInt("CartPosition")
        tv_title_product.text = cart.product!!.nama_barang
        tv_product_count.text = cart.count!!.toInt().toString()
        btn_product_decrease.setOnClickListener {
            val count = cart.count!!.minus(1)
            if(count > 0){
                cart.count = count
            }
            tv_product_count.text = cart.count!!.toInt().toString()
            setTotalHargaText(cart)
        }
        btn_product_increase.setOnClickListener {
            val stok = cart.product!!.stok.toDouble()
            val count = cart.count!!.plus(1)
            if(count <= stok){
                cart.count = count
            }
            tv_product_count.text = cart.count!!.toInt().toString()
            setTotalHargaText(cart)
        }
        setTotalHargaText(cart)
        btn_update_barang.setOnClickListener {
            val newIntent: Intent = activity!!.intent
            newIntent.putExtra(AppConstant.DATA, cart)
            newIntent.putExtra("CartPosition", position)
            targetFragment!!.onActivityResult(targetRequestCode, 1001, activity!!.intent)
            dismiss()
        }
        btn_delete.setOnClickListener {
            val newIntent: Intent = activity!!.intent
            newIntent.putExtra(AppConstant.DATA, cart)
            newIntent.putExtra("CartPosition", position)
            targetFragment!!.onActivityResult(targetRequestCode, 1002, activity!!.intent)
            dismiss()
        }
        btn_close.setOnClickListener {
            dismiss()
        }
    }

    fun setTotalHargaText(cart:Cart){
        tv_total_harga.text = "Jumlah ${cart!!.count!!.toInt()} x ${cart.product!!.hargajual} = ${cart.product!!.hargajual.toDouble().times(
            cart.count!!).toInt()}"
    }
}