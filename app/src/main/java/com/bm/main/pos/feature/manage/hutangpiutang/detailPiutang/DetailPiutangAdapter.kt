package com.bm.main.pos.feature.manage.hutangpiutang.detailPiutang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.hutangpiutang.DetailPiutang
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.item_list_piutang_detail.view.*

class DetailPiutangAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<DetailPiutang.Data>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_piutang_detail, parent, false))
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

    fun setItems(listProduct: List<DetailPiutang.Data>?) {
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

        private val tvDate = view.tv_date
        private val tvNominal = view.tv_price


        fun bindData(data: DetailPiutang.Data, position: Int) {
            tvDate.text = "${Helper.getDateFormat(itemView.context,data.tanggal!!,"yyyy-MM-dd","dd MMMM yyyy")}"
            tvNominal.text = "Rp ${Helper.convertToCurrency(data.nominal!!)}"


        }
    }

    var callback: ItemClickCallback?= null

    interface ItemClickCallback{
        fun onClick(data: DetailPiutang.Data)
    }
}