package com.bm.main.scm.feature.manage.hutangpiutang.detailHutang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.hutangpiutang.DetailHutang
import com.bm.main.scm.utils.Helper
import kotlinx.android.synthetic.main.item_list_hutang_detail.view.*

class DetailHutangAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<DetailHutang.Data>()


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

    fun setItems(listProduct: List<DetailHutang.Data>?) {
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


        fun bindData(data: DetailHutang.Data, position: Int) {
            tvDate.text = "${Helper.getDateFormat(itemView.context,data.tanggal!!,"yyyy-MM-dd","dd MMMM yyyy")}"
            tvNominal.text = "Rp ${Helper.convertToCurrency(data.nominal!!)}"


        }
    }

    var callback: ItemClickCallback?= null

    interface ItemClickCallback{
        fun onClick(data: DetailHutang.Data)
    }
}