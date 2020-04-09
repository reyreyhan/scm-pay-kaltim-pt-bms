package com.bm.main.pos.feature.manage.hutangpiutang.piutangCustomer

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.customer.CustomerNew
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.item_list_piutang_customer_new.view.*

class PiutangCustomerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listPiutang = mutableListOf<CustomerNew>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_piutang_customer_new, parent, false))
    }

    override fun getItemCount(): Int {
        return listPiutang.size
    }

    fun getListPiutangCount():Int = listPiutang.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val hutang = listPiutang[position]
            holder.bindData(hutang, position)
        }
    }

    fun setItems(listPiutang: List<CustomerNew>?) {
        //this.listProduct.clear()
        val lastCount = itemCount
        listPiutang?.let { this.listPiutang.addAll(it) }
        notifyItemRangeInserted(lastCount, listPiutang!!.size)
    }

    fun addItem(item: CustomerNew?) {
        //this.listProduct.clear()
        val lastCount = itemCount
        item?.let { this.listPiutang.add(it) }
        notifyItemRangeInserted(lastCount, 1)
    }

    fun clearAdapter(){
              listPiutang.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.tv_name
        private val totalTv = view.tv_total
        private val statusTv = view.tv_status

        @SuppressLint("SetTextI18n")
        fun bindData(data: CustomerNew, position: Int) {
            nameTv.text = data.nama_pelanggan
            totalTv.text = "Rp ${Helper.convertToCurrency(data.total_hutang!!)}"
            statusTv.text = if (data.sisa_hutang == "0") {
                statusTv.setTextColor(itemView.context.resources.getColor(R.color.md_green_500))
                    "Lunas"
            } else {
                statusTv.setTextColor(itemView.context.resources.getColor(R.color.md_red_500))
                "- Rp ${Helper.convertToCurrency(data.sisa_hutang!!)}"
            }
            itemView.setOnClickListener {
                if(callback != null){
                    callback?.onClick(data)
                }
            }
        }
    }

    var callback: ItemClickCallback?= null

    interface ItemClickCallback{
        fun onClick(data: CustomerNew)
    }
}