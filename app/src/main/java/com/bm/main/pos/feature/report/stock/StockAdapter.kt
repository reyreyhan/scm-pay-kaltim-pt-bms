package com.bm.main.pos.feature.report.stock

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.report.ReportStock
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.item_list_report_stok.view.*
import com.bm.main.pos.models.DialogModel
import kotlinx.android.synthetic.main.item_list_report_stok.view.tv_name
import kotlinx.android.synthetic.main.item_list_report_stok_chart.view.*
import android.view.ViewTreeObserver
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bm.main.pos.models.StockChartModel


class StockAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<ReportStock>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_report_stok, parent, false))
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

    fun setItems(listProduct: List<ReportStock>?) {
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
        private val stokTv = view.tv_stok
        private val infoIv = view.iv_info
//        private val terjualTv = view.tv_terjual
//        private val dateTv = view.tv_date
//        private val detailView = view.ll_detail
//        private val moreBtn = view.btn_more
//        private val lessBtn = view.btn_less
//        private val list = view.list

        @SuppressLint("SetTextI18n")
        fun bindData(data: ReportStock, position: Int) {

            nameTv.text = "${data.nama_barang}"
            stokTv.text = "Stok: ${Helper.convertToCurrency(data.stok_terakhir!!)}"
//            terjualTv.text = "Terjual: ${Helper.convertToCurrency(data.terjual!!)}"
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

            val stok = data.stok_terakhir!!.toDouble()
            val min = data.minimal_stok!!.toDouble()
            if(stok > min){
                infoIv.visibility = View.GONE
            }
            else{
                infoIv.visibility = View.VISIBLE
            }

//            val dataList = ArrayList<ReportStock.Detail>()
//            var max = 0.0
//            data.datastok?.let {datastocks ->
//                if(datastocks.isNotEmpty()){
//                    for (stock in datastocks){
//                        val sisa = stock.sisa_stok!!.toDouble()
//                        dataList.add(stock)
//                        if(max <= sisa){
//                            max = sisa
//                        }
//
//                    }
//
//                }
//            }
//
//            val layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
//            list.layoutManager = layoutManager
//            val adapter = ItemAdapter(itemView.context,dataList,max)
//            list.adapter = adapter
//
//
//            moreBtn.setOnClickListener {
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
//
//            showLess()


        }

//        fun showMore(){
//            //moreBtn.visibility = View.GONE
//            detailView.visibility = View.VISIBLE
//
//            val dt = dateTv.text.toString()
//            if(dt.isEmpty() || dt.isBlank()){
//                dateTv.visibility = View.GONE
//            }
//            else{
//                dateTv.visibility = View.VISIBLE
//            }
//        }
//
//        fun showLess(){
//            //moreBtn.visibility = View.VISIBLE
//            dateTv.visibility = View.GONE
//            detailView.visibility = View.GONE
//        }
//
//        private inner class InnerViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup) :
//            RecyclerView.ViewHolder(inflater.inflate(R.layout.item_list_report_stok_chart, parent, false)) {
//
//            //        internal val text: TextView = itemView.name_tv
////        internal val icon: ImageView = itemView.icon_iv
//            internal val nameTv: TextView = itemView.tv_name
//            internal val bg: View = itemView.bg
//            internal val warningIv: ImageView = itemView.iv_warning
//            internal val flName: FrameLayout = itemView.fl_name
//            internal val fl: FrameLayout = itemView.fl_layout
//            internal val llParent: LinearLayout = itemView.ll_parent
//
//        }
//
//        private inner class ItemAdapter internal constructor(val context: Context, val data: List<ReportStock.Detail>, val max:Double) :
//            RecyclerView.Adapter<InnerViewHolder>() {
//
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
//                return InnerViewHolder(LayoutInflater.from(parent.context), parent)
//            }
//
//            @SuppressLint("SetTextI18n")
//            override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
//
//                val model = data[position]
//                //Helper.getDateFormat(itemView.context,stock.tanggal!!,"yyyy-MM-dd","dd")
//                //holder.countTv.text = Helper.convertToCurrency(model.value!!)
//                holder.nameTv.text = Helper.getDateFormat(context,model.tanggal!!,"yyyy-MM-dd","dd")
//
//                val stock = model.sisa_stok!!.toDouble()
//                val min = model.minimal_stok!!.toDouble()
//                if(stock > 0){
//                    if(stock > min){
//                        holder.warningIv.visibility = View.GONE
//                        holder.flName.background = ContextCompat.getDrawable(context,R.drawable.circle_gray)
//                    }
//                    else{
//                        holder.warningIv.visibility = View.VISIBLE
//                        holder.flName.background = ContextCompat.getDrawable(context,R.drawable.circle_orange)
//                    }
//                    if(max > 0){
//                        val vto = holder.fl.viewTreeObserver
//                        vto.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
//                            override fun onPreDraw(): Boolean {
//                                holder.fl.viewTreeObserver.removeOnPreDrawListener(this)
//                                val maxHeight = holder.fl.measuredHeight
//                                val maxWidth = holder.fl.measuredWidth
//
//                                val height = (stock/max)*maxHeight
//
//                                holder.bg.layoutParams = LinearLayout.LayoutParams(maxWidth,height.toInt())
//
//                                return true
//                            }
//                        })
//                    }
//                }
//                else{
//                    holder.warningIv.visibility = View.VISIBLE
//                    holder.flName.background = ContextCompat.getDrawable(context,R.drawable.circle_orange)
//                }
//
//                holder.llParent.setOnClickListener {
//                    Toast.makeText(context,"Terjual: ${Helper.convertToCurrency(model.sisa_stok!!)}",Toast.LENGTH_SHORT).show()
//                }
//
//
//
//            }
//
//            override fun getItemCount(): Int {
//                return data.size
//            }
//        }
    }

    var callback: ItemClickCallback ?= null

    interface ItemClickCallback{
        fun onClick(data: ReportStock)
    }
}