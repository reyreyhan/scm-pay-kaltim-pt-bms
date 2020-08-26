package com.bm.main.scm.feature.report.stock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.report.ReportStock
import kotlinx.android.synthetic.main.item_list_report_stok_runout_new.view.*


class StockRunningOutAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<ReportStock>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_report_stok_runout_new, parent, false))
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

    fun setItems(listProduct: List<ReportStock>?) {
        val lastCount = itemCount
        for (data in listProduct!!){
            if (data.stok!!.toInt() < 10){
                this.listProduct.add(data)
            }
        }
        if (itemCount == 0){
            callback?.onItemEmpty()
        }
        notifyItemRangeInserted(lastCount, itemCount)
    }

    fun clearAdapter(){
        listProduct.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.tv_name
        private val countTv = view.tv_count
        private val updateBtn = view.btn_update_stock

        @SuppressLint("SetTextI18n")
        fun bindData(data: ReportStock, position: Int) {
            nameTv.text = data.nama_barang!!
            countTv.text = data.stok!!
            updateBtn.setOnClickListener {
                callback!!.onClick(data)
            }
        }
    }

    var callback: ItemClickCallback ?= null

    interface ItemClickCallback{
        fun onClick(data: ReportStock)
        fun onItemEmpty()
    }
}