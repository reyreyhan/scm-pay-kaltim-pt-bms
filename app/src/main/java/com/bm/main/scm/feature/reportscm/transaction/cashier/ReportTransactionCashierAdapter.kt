package com.bm.main.scm.feature.reportscm.transaction.cashier

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.rabbit.QrTransaction
import kotlinx.android.synthetic.main.item_report_mutation_scm.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReportTransactionCashierAdapter (var list:MutableList<QrTransaction>,
                                       var reportTransactionListener: ReportTransactionListener
) : RecyclerView.Adapter<ReportTransactionCashierAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.tv_name
        private val tvDateTime = itemView.tv_datetime
        private val tvTransactionId = itemView.tv_transaction_id
        private val tvAmmount = itemView.tv_ammount
        private val respDateFormat by lazy {
            SimpleDateFormat(
                "EEE, dd-MM-yyyy HH:mm:ss",
                Locale.getDefault()
            )
        }
        private val view = itemView
        fun setContent(item:QrTransaction, reportTransactionListener: ReportTransactionListener){
            tvName.text = item.buyer_reff
            tvDateTime.text = respDateFormat.format(Date(item.time!!))
            tvTransactionId.text = item.id_transaksi
            tvAmmount.text = "Rp ${item.nominal}"
            view.setOnClickListener {
                reportTransactionListener.OnReportTransactionClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_cashier_scm, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(list[position], reportTransactionListener)
    }

    interface ReportTransactionListener{
        fun OnReportTransactionClick(transaction: QrTransaction)
    }
}