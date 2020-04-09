package com.bm.main.pos.feature.manage.hutangpiutang.detailPiutang

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.hutangpiutang.DetailPiutang
import com.bm.main.pos.models.hutangpiutang.DetailPiutangNew
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.item_list_piutang_detail_new.view.*

class DetailPiutangAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<DetailPiutangNew.Data>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_piutang_detail_new, parent, false))
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

    fun setItems(listProduct: List<DetailPiutangNew.Data>?) {
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

        private val tvDate = view.tv_date
        private val tvNominal = view.tv_total
        private val tvStatus = view.tv_status


        @RequiresApi(Build.VERSION_CODES.M)
        @SuppressLint("SetTextI18n")
        fun bindData(data: DetailPiutangNew.Data, position: Int) {
            tvDate.text =
                Helper.getDateFormat(itemView.context, data.tanggal!!,"yyyy-MM-dd","dd MMMM yyyy")
            tvNominal.text = "Rp ${Helper.convertToCurrency(data.nominal!!)}"
            tvStatus.text = if (data.status.equals("hutang")) {
                tvStatus.setTextColor(itemView.context.getColor(R.color.md_red_500))
                "Hutang"
            }
            else {
                tvStatus.setTextColor(itemView.context.getColor(R.color.md_green_500))
                "Lunas"
            }
        }
    }

    var callback: ItemClickCallback?= null

    interface ItemClickCallback{
        fun onClick(data: DetailPiutang.Data)
    }
}