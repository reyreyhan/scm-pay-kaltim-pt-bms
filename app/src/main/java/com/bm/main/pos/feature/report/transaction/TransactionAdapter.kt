package com.bm.main.pos.feature.report.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.report.ReportTransaksi
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.item_list_report_transaction.view.*

class TransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<ReportTransaksi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_report_transaction, parent, false))
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

    fun setItems(listProduct: List<ReportTransaksi>?) {
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
        private val priceTv = view.tv_sell
        private val dateTv = view.tv_date
        private val terjualTv = view.tv_terjual
        private val sisaTv = view.tv_last_stock
        private val penjualanTv = view.tv_penjualan
        private val beliTv = view.tv_buy_price
        private val jualTv = view.tv_sell_price
        private val profitTv = view.tv_profit
        private val detailView = view.ll_detail
        private val moreBtn = view.btn_more
        private val lessBtn = view.btn_less

        @SuppressLint("SetTextI18n")
        fun bindData(data: ReportTransaksi, position: Int) {
            nameTv.text = "${data.nama_barang}"
            priceTv.text = "Rp ${Helper.convertToCurrency(data.harga_jual!!)}"
            terjualTv.text = "Terjual : ${Helper.convertToCurrency(data.penjualan!!)}"
            sisaTv.text = "Stok Terakhir : ${Helper.convertToCurrency(data.stok_terakhir!!)}"
            penjualanTv.text = Helper.convertToCurrency(data.penjualan!!)
            beliTv.text = "Rp ${Helper.convertToCurrency(data.harga_beli!!)}"
            jualTv.text = "Rp ${Helper.convertToCurrency(data.harga_jual!!)}"
            profitTv.text = "Rp ${Helper.convertToCurrency(data.raba_rugi!!)}"

            var day1 = ""
            var day2 = ""
            if(!data.tanggal_awal.isNullOrEmpty() && !data.tanggal_awal.isNullOrBlank()){
                day1 = Helper.getDateFormat(itemView.context,data.tanggal_awal!!,"yyyy-MM-dd","dd MMM yyyy")
            }
            if(!data.tanggal_akhir.isNullOrEmpty() && !data.tanggal_akhir.isNullOrBlank()){
                day2 = Helper.getDateFormat(itemView.context,data.tanggal_akhir!!,"yyyy-MM-dd","dd MMM yyyy")
            }

            if(day1 == day2){
                dateTv.text = day1
            }
            else{
                dateTv.text = "$day1 - $day2"
            }

            showLess()

            moreBtn.setOnClickListener {
                //showMore()
                if(detailView.isVisible){
                    showLess()
                }
                else{
                    showMore()
                }
            }

            lessBtn.setOnClickListener {
                showLess()
            }

        }

        fun showMore(){
            priceTv.visibility = View.GONE
            //moreBtn.visibility = View.GONE
            sisaTv.visibility = View.VISIBLE
            detailView.visibility = View.VISIBLE

            val dt = dateTv.text.toString()
            if(dt.isEmpty() || dt.isBlank()){
                dateTv.visibility = View.GONE
            }
            else{
                dateTv.visibility = View.VISIBLE
            }
        }

        fun showLess(){
            priceTv.visibility = View.VISIBLE
            //moreBtn.visibility = View.VISIBLE
            dateTv.visibility = View.GONE
            sisaTv.visibility = View.GONE
            detailView.visibility = View.GONE
        }
    }

    var callback: ItemClickCallback ?= null

    interface ItemClickCallback{
        fun onClick(data: ReportTransaksi)
    }
}