package com.bm.main.pos.feature.manage.customer.credit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.item_list_customer_credit.view.*

class CustomerCreditAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Transaction>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_customer_credit, parent, false))
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

    fun setItems(listProduct: List<Transaction>?) {
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

        private val idTv = view.tv_id
        private val dateTv = view.tv_date
        private val totalTv = view.tv_total



        fun bindData(data: Transaction, position: Int) {
            idTv.text = "${data.no_invoice}"
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

    interface ItemClickCallback{
        fun onClick(data: Transaction)
    }
}