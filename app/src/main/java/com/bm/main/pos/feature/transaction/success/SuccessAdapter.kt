package com.bm.main.pos.feature.transaction.success

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.transaction.DetailTransaction
import kotlinx.android.synthetic.main.item_list_transaction_2.view.*
import com.bm.main.pos.utils.Helper

class SuccessAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<DetailTransaction.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_transaction_2, parent, false))
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

    fun setItems(listProduct: List<DetailTransaction.Data>?) {
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

        private val nameTv = view.tv_name
        private val countTv = view.tv_count
        private val priceTv = view.tv_harga
        private val subtotalTv = view.tv_subtotal

        fun bindData(data: DetailTransaction.Data, position: Int) {
            nameTv.text = "${data.nama_barang}"
            countTv.text = "${data.jumlah}x"
            priceTv.text = "${Helper.convertToCurrency(data.harga!!)}"
            subtotalTv.text = "${Helper.convertToCurrency(data.totalharga!!)}"
        }
    }
}