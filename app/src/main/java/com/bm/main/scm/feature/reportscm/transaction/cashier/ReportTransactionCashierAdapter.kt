package com.bm.main.scm.feature.reportscm.transaction.cashier

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import kotlinx.android.synthetic.main.item_report_mutation_scm.view.*

class ReportTransactionCashierAdapter (var list:List<Transaction>) : RecyclerView.Adapter<ReportTransactionCashierAdapter.ViewHolder>() {

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val tvName = view.tv_name
        private val tvDateTime = view.tv_datetime
        private val tvTransactionId = view.tv_transaction_id
        private val tvAmmount = view.tv_ammount

        fun setContent(item:Transaction){
            tvName.text = item.title
            tvDateTime.text = item.date
            tvTransactionId.text = item.id
            tvAmmount.text = "Rp ${item.ammount.toString()}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_cashier_scm, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(list[position])
    }
}