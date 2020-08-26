package com.bm.main.scm.feature.manage.customer.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.transaction.Transaction
import com.bm.main.scm.utils.Helper
import kotlinx.android.synthetic.main.item_list_customer_transaction.view.*

class CustomerTransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Transaction>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_customer_transaction, parent, false))
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
        private val statusTv = view.tv_status


        @SuppressLint("SetTextI18n")
        fun bindData(data: Transaction, position: Int) {
            idTv.text = "${data.no_invoice}"
            dateTv.text = Helper.getDateFormat(itemView.context,data.tanggal!!,"yyyy-MM-dd","dd MMMM yyyy")
            totalTv.text = "Rp ${Helper.convertToCurrency(data.totalorder!!)}"
            statusTv.text = "${data.status}"

            if("batal" == data.status){
                statusTv.background =  ContextCompat.getDrawable(itemView.context,R.drawable.rounded_black_4dp)//itemView.context.resources.getDrawable(R.drawable.rounded_black_4dp)
            }
            else{
                statusTv.background = ContextCompat.getDrawable(itemView.context,R.drawable.rounded_orange_4dp)//itemView.context.resources.getDrawable(R.drawable.rounded_orange_4dp)
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
        fun onClick(data: Transaction)
    }
}