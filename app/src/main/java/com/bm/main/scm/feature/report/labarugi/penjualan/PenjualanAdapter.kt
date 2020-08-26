package com.bm.main.scm.feature.report.labarugi.penjualan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.report.ReportLabaRugi
import com.bm.main.scm.utils.Helper
import kotlinx.android.synthetic.main.item_list_report_penjualan_new.view.*

class PenjualanAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<ReportLabaRugi.Penjualan>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_report_penjualan_new, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val product = listProduct[position]
            holder.bindData(product, position)
        }
    }

    fun setItems(listProduct: List<ReportLabaRugi.Penjualan>?) {
        //this.listProduct.clear()
        val lastCount = itemCount
        listProduct?.let { this.listProduct.addAll(it) }
        notifyItemRangeInserted(lastCount, listProduct!!.size)
    }

    fun clearAdapter() {
        listProduct.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.tv_name
        private val totalTv = view.tv_total
        private val infoTv = view.tv_info
//        private val statusTv = view.tv_status
        private val root = view.root
        @SuppressLint("SetTextI18n")
        fun bindData(data: ReportLabaRugi.Penjualan, position: Int) {
            nameTv.text = "${data.nama_barang}"
            totalTv.text = "Rp ${Helper.convertToCurrency(data.totalharga!!)}"
            var jual = data.harga
            if (jual == null) {
                jual = "0"
            }
            val info = "Rp ${Helper.convertToCurrency(jual)} x${Helper.convertToCurrency(data.jumlah!!)}"
            infoTv.text = info

            if (data.status?.toLowerCase() == "batal") {
//                statusTv.visibility = View.VISIBLE
                root.setBackgroundResource(R.drawable.rounded_gray_4dp_stroke_gray)
            } else {
//                statusTv.visibility = View.GONE
                root.setBackgroundResource(R.drawable.rounded_white_4dp_stroke_gray)
            }
        }
    }

    var callback: ItemClickCallback? = null

    interface ItemClickCallback {
        fun onClick(data: ReportLabaRugi.Penjualan)
    }
}