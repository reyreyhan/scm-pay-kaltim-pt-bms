package com.bm.main.scm.feature.report.transaction

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.report.ReportTransaksi
import com.bm.main.scm.utils.Helper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_list_report_transaction_new.view.*

class TransactionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<ReportTransaksi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_report_transaction_new, parent, false))
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

        private val productIv = view.iv_product
        private val nameTv = view.tv_name
        private val profitTv = view.tv_profit
        private val earningTv = view.tv_earning
        private val countTv = view.tv_count

        @SuppressLint("SetTextI18n")
        fun bindData(data: ReportTransaksi, position: Int) {
            nameTv.text = "${data.nama_barang}"
            countTv.text = Helper.convertToCurrency(data.penjualan!!)
            earningTv.text = "Rp ${Helper.convertToCurrency(data.harga_jual!!.toDouble().times(data.penjualan!!.toDouble()))}"
            profitTv.text = "Rp ${Helper.convertToCurrency(data.raba_rugi!!)}"
            if (data.gbr == null) {
                Glide.with(itemView.context)
                    .load(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(productIv)

            } else {
                Glide.with(itemView.context)
                    .load(data.gbr)
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(productIv)
            }
//
//            var day1 = ""
//            var day2 = ""
//            if(!data.tanggal_awal.isNullOrEmpty() && !data.tanggal_awal.isNullOrBlank()){
//                day1 = Helper.getDateFormat(itemView.context,data.tanggal_awal!!,"yyyy-MM-dd","dd MMM yyyy")
//            }
//            if(!data.tanggal_akhir.isNullOrEmpty() && !data.tanggal_akhir.isNullOrBlank()){
//                day2 = Helper.getDateFormat(itemView.context,data.tanggal_akhir!!,"yyyy-MM-dd","dd MMM yyyy")
//            }
//
//            if(day1 == day2){
//                dateTv.text = day1
//            }
//            else{
//                dateTv.text = "$day1 - $day2"
//            }
//
//            showLess()
//
//            moreBtn.setOnClickListener {
//                //showMore()
//                if(detailView.isVisible){
//                    showLess()
//                }
//                else{
//                    showMore()
//                }
//            }
//
//            lessBtn.setOnClickListener {
//                showLess()
//            }

        }

        fun showMore(){
//            priceTv.visibility = View.GONE
//            //moreBtn.visibility = View.GONE
//            sisaTv.visibility = View.VISIBLE
//            detailView.visibility = View.VISIBLE
//
//            val dt = dateTv.text.toString()
//            if(dt.isEmpty() || dt.isBlank()){
//                dateTv.visibility = View.GONE
//            }
//            else{
//                dateTv.visibility = View.VISIBLE
//            }
        }

        fun showLess(){
//            priceTv.visibility = View.VISIBLE
//            //moreBtn.visibility = View.VISIBLE
//            dateTv.visibility = View.GONE
//            sisaTv.visibility = View.GONE
//            detailView.visibility = View.GONE
        }
    }

    var callback: ItemClickCallback ?= null

    interface ItemClickCallback{
        fun onClick(data: ReportTransaksi)
    }
}