package com.bm.main.pos.feature.manage.hutangpiutang.hutang

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.hutangpiutang.Hutang
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.item_list_hutang.view.*

class HutangAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Hutang.Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_hutang, parent, false))
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

    fun setItems(listProduct: List<Hutang.Data>?) {
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



        fun bindData(data: Hutang.Data, position: Int) {
            nameTv.text = "${data.nama_supplier}"
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
        fun onClick(data: Hutang.Data)
    }
}