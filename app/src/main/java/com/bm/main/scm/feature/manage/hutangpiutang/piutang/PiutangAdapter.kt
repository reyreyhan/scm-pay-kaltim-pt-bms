package com.bm.main.scm.feature.manage.hutangpiutang.piutang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.hutangpiutang.Piutang
import com.bm.main.scm.utils.Helper
import kotlinx.android.synthetic.main.item_list_piutang.view.*

class PiutangAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Piutang.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_piutang, parent, false))
    }

    override fun getItemCount(): Int {
        if(listProduct.size == 0){
            return 0
        }
        if(limit!! == -1){
            return listProduct.size
        }
        if(listProduct.size > limit!!){
            return limit!!
        }
        return listProduct.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val product = listProduct[position]
            holder.bindData(product, position)
        }
    }

    fun setItems(listProduct: List<Piutang.Data>?) {
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
        private val dateTv = view.tv_date
        private val totalTv = view.tv_total



        fun bindData(data: Piutang.Data, position: Int) {
            nameTv.text = "${data.nama_pelanggan}"
            dateTv.text = "${Helper.getDateFormat(itemView.context,data.tanggal!!,"yyyy-MM-dd","dd MMMM yyyy")}"
            totalTv.text = "Rp ${Helper.convertToCurrency(data.totalorder!!)}"

            itemView.setOnClickListener {
                if(callback != null){
                    callback?.onClick(data)
                }
            }


        }
    }

    var callback: ItemClickCallback?= null
    var limit: Int ?= -1

    interface ItemClickCallback{
        fun onClick(data: Piutang.Data)
    }
}