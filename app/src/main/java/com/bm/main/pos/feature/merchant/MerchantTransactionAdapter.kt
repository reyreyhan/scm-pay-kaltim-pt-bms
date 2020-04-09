package com.bm.main.pos.feature.merchant

//import com.bm.main.pos.utils.glide.GlideApp
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.item_list_merchant_transaction.view.*

class MerchantTransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Transaction>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_merchant_transaction, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val product = listProduct[position]
            holder.bindData(product, position, getItemViewType(position))
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

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bindData(data: Transaction, position: Int, type:Int) {
            view.tv_status.text = "Terima Pembayaran"
            view.tv_time.text = Helper.getDateFormat(itemView.context,data.tanggal!!,"yyyy-MM-dd","EEE, dd/MM/yyyy")
            view.tv_id_transaction.text = data.no_invoice
            if (data.totalorder!=null){
                view.tv_total.text = "Rp ${Helper.convertToCurrency(data.totalorder!!)}"
            }
            itemView.setOnClickListener {
                callback?.onClick(data)
            }
        }
    }

    var callback: ItemClickCallback?= null

    interface ItemClickCallback{
        fun onClick(data: Transaction)
    }
}