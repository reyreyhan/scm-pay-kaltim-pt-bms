package com.bm.main.scm.feature.qrisscm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.utils.Helper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.item_qris_product_scm.view.*

class QrisProductAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_qris_product_scm, parent, false)
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

    fun setItems(listProduct: List<Product>?) {
        this.listProduct.clear()
        val lastCount = itemCount
        listProduct?.let { this.listProduct.addAll(it) }
        notifyItemRangeInserted(lastCount, listProduct!!.size)
    }

    fun addItems(listProduct: List<Product>?) {
        val lastCount = itemCount
        listProduct?.let { this.listProduct.addAll(it) }
        notifyItemRangeInserted(lastCount, listProduct!!.size)
    }

    fun clearAdapter() {
        listProduct.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.tv_product_name
        private val hargaTv = view.tv_price
        private val imageIv = view.iv_product
        private val editBtn = view.ll_btn_edit
        private val qrisBtn = view.ll_btn_qris

        fun bindData(data: Product, position: Int) {
            nameTv.text = "${data.nama_barang}"
            hargaTv.text =
                "Rp ${Helper.convertToCurrency(data.hargajual)}"

            if (data.gbr == null) {
                Glide.with(itemView.context)
                    .load(R.drawable.logo)
                    .transform(CenterCrop())
                    .into(imageIv)

            } else {
                Glide.with(itemView.context)
                    .load(data.gbr)
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .transform(CenterCrop())
                    .into(imageIv)
            }

            editBtn.setOnClickListener {
                if (callback != null) {
                    callback?.onClickEdit(data)
                }
            }
            qrisBtn.setOnClickListener {
                if (callback != null) {
                    callback?.onClickQris(data)
                }
            }
        }
    }

    var callback: ItemClickCallback? = null

    interface ItemClickCallback {
        fun onClickEdit(data: Product)
        fun onClickQris(data: Product)
    }
}