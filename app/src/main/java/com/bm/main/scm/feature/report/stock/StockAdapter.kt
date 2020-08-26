package com.bm.main.scm.feature.report.stock

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.report.ReportStock
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_list_report_stok_new.view.*


class StockAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<ReportStock>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_report_stok_new, parent, false))
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
        //this.listProduct.clear()
        val lastCount = itemCount
        listProduct?.let { this.listProduct.addAll(it) }
        notifyItemRangeInserted(lastCount,listProduct!!.size)
    }

    fun clearAdapter(){
        listProduct.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val productIv = view.iv_photo
        private val nameTv = view.tv_name
        private val stockTv = view.tv_count
        private val codeIv = view.tv_code
        private val priceTv = view.tv_price

        @SuppressLint("SetTextI18n")
        fun bindData(data: ReportStock, position: Int) {
            nameTv.text = data.nama_barang!!
            stockTv.text = data.stok!!
            codeIv.text = data.id_barang!!
            if (data.gbr == null) {
                Glide.with(itemView.context)
                    .load(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(productIv)

            } else {
                Glide.with(itemView.context)
                    .load(data.gbr)
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(productIv)
            }
            itemView.setOnClickListener {
                callback?.onClick(data)
            }
        }
    }
    var callback: ItemClickCallback ?= null

    interface ItemClickCallback{
        fun onClick(data: ReportStock)
    }
}