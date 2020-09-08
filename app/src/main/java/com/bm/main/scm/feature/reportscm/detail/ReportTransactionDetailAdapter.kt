package com.bm.main.scm.feature.reportscm.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import kotlinx.android.synthetic.main.item_payment_detail_scm.view.*

class ReportTransactionDetailAdapter(var list: MutableList<String>) :
    RecyclerView.Adapter<ReportTransactionDetailAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setContent(pos: Int, item: String) {
            when (pos) {
                0 -> {
                    view.tv_label.text = "Tanggal"
                    view.tv_value.text = item
                }
                1 -> {
                    view.tv_label.text = "Jam"
                    view.tv_value.text = item
                }
                2 -> {
                    view.tv_label.text = "Nama Merchant"
                    view.tv_value.text = item
                }
                3 -> {
                    view.tv_label.text = "Total Bayar"
                    view.tv_value.text = "Rp $item"
                }
                4 -> {
                    view.tv_label.text = "Status"
                    view.tv_value.text = item
                }
                5 -> {
                    view.tv_label.text = "No. Resi"
                    view.tv_value.text = item
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_payment_detail_scm, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(position, list[position])
    }
}