package com.bm.main.scm.feature.homescm.merchant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.rabbit.QrTransaction
import kotlinx.android.synthetic.main.item_report_mutation_scm.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeTransactionAdapter(
    var list: MutableList<QrTransaction>,
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HomeTransactionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName = view.tv_name
        private val tvDateTime = view.tv_datetime
        private val tvTransactionId = view.tv_transaction_id
        private val tvAmmount = view.tv_ammount
        private val _view = view
        private val respDateFormat by lazy {
            SimpleDateFormat(
                "EEE, dd-MM-yyyy HH:mm:ss",
                Locale.getDefault()
            )
        }

        fun setContent(item: QrTransaction, clickListener: OnItemClickListener) {
            tvName.text = item.buyer_reff
            tvDateTime.text = respDateFormat.format(Date(item.time!!))
            tvTransactionId.text = item.id_transaksi
            tvAmmount.text = "Rp ${item.nominal}"
            _view.setOnClickListener {
                clickListener.onItemClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_transaction_merchant_scm, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(list[position], itemClickListener)
    }

    interface OnItemClickListener {
        fun onItemClicked(trx: QrTransaction)
    }
}