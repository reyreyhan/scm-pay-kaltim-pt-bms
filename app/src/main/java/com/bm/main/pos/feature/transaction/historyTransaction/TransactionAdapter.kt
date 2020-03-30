package com.bm.main.pos.feature.transaction.historyTransaction

//import com.bm.main.pos.utils.glide.GlideApp
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.item_list_aktivitas_header.view.*
import kotlinx.android.synthetic.main.item_list_aktivitas_item.view.*

class TransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Transaction>()
    private val HEADER = 1
    private val DATA = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = if (viewType == HEADER) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_aktivitas_header, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_aktivitas_item, parent, false)
        }

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

    override fun getItemViewType(position: Int): Int {
        val transaction = listProduct[position]

        return if ("header" == transaction.type) {
            HEADER
        } else {
            DATA
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

//        private val headerDateTv = view.tv_header_date
//        private val headerTotalTv = view.tv_header_total
//        private val headerOmsetTv = view.tv_header_omset
//        private val headerProfitTv = view.tv_header_profit

//        private val photoIv = view.iv_photo
//        private val idTv = view.tv_id
//        private val totalTv = view.tv_total
//        private val methodTv = view.tv_method
//        private val statusTv = view.tv_status


        @SuppressLint("SetTextI18n")
        fun bindData(data: Transaction, position: Int, type:Int) {

            if(HEADER == type){
                itemView.subheaderText.text = Helper.getDateFormat(itemView.context,data.tanggal!!,"yyyy-MM-dd","EEE, dd MMMM yyyy")
//                headerTotalTv.text = Helper.convertToCurrency(data.totalorder!!)
//                headerOmsetTv.text = "Rp ${Helper.convertToCurrency(data.totalomset!!)}"
//                headerProfitTv.text = "Rp ${Helper.convertToCurrency(data.totalprofit!!)}"
            }
            else{
                itemView.title.text = "Rp ${Helper.convertToCurrency(data.totalorder!!)}"
                itemView.subtitle.text = data.nama_barang
//                itemView.date.text = Helper.getDateFormat(itemView.context,data.tanggal!!,"yyyy-MM-dd HH:mm:ss","HH:mm")
                itemView.date.text = ""
                itemView.layout_parent.setOnClickListener {
                    callback?.onClick(data)
                // idTv.text = data.no_invoice
//                when(data.pos){
//                    0 -> idTv.text = data.no_invoice
//                    else -> idTv.text = "Penjualan ke ${data.pos!!}"
//                }

//                totalTv.text = "Rp ${Helper.convertToCurrency(data.totalorder!!)}"
//                methodTv.text = data.pembayaran
//                statusTv.text = data.status

//                var img = R.drawable.ic_cash
//                if("hutang" == data.pembayaran){
//                    img = R.drawable.ic_banking
//                }
//                Glide.with(itemView.context)
//                    .load(img)
//                    .error(R.drawable.logo)
//                    .transform(CenterCrop(), RoundedCorners(8))
//                    .into(photoIv)
//
//                when {
//                    "hutang" == data.status -> {
//                        statusTv.background = ContextCompat.getDrawable(itemView.context,R.drawable.rounded_vermilion_4dp)
//                    }
//                    "batal" == data.status -> {
//                        statusTv.background =  ContextCompat.getDrawable(itemView.context,R.drawable.rounded_black_4dp)
//                    }
//                    else -> {
//                        statusTv.background = ContextCompat.getDrawable(itemView.context,R.drawable.rounded_orange_4dp)
//                    }
//                }
                }
            }

        }
    }

    var callback: ItemClickCallback?= null

    interface ItemClickCallback{
        fun onClick(data: Transaction)
    }
}