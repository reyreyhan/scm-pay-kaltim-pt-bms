package com.bm.main.pos.feature.sell.chooseCustomer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.customer.Customer
import kotlinx.android.synthetic.main.item_list_choose_customer.view.*

class ChooseCustomerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listCustomer = mutableListOf<Customer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_choose_customer, parent, false))
    }

    override fun getItemCount(): Int {
        return listCustomer.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val product = listCustomer[position]
            holder.bindData(product, position)
        }
    }

    fun setItems(listProduct: List<Customer>?) {
        this.listCustomer.clear()
        val lastCount = itemCount
        listProduct?.let { this.listCustomer.addAll(it) }
        notifyItemRangeInserted(lastCount,listProduct!!.size)
    }

    fun clearAdapter(){
        listCustomer.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.tv_name
        private val infoTv = view.tv_phone



        fun bindData(data: Customer, position: Int) {
            nameTv.text = "${data.nama_pelanggan}"
            infoTv.text = "${data.telpon}"

            itemView.setOnClickListener {
                if(callback != null){
                    callback?.onClick(data)
                }
            }
        }
    }

    var callback: ItemClickCallback?= null

    interface ItemClickCallback{
        fun onClick(data: Customer)
    }
}