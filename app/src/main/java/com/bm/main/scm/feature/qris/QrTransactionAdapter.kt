package com.bm.main.scm.feature.qris

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import java.text.SimpleDateFormat
import java.util.*

class QrTransactionAdapter : PagedListAdapter<QrTransactionItem, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<QrTransactionItem>() {
        override fun areItemsTheSame(
            oldItem: QrTransactionItem,
            newItem: QrTransactionItem
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: QrTransactionItem,
            newItem: QrTransactionItem
        ): Boolean = false
    }
) {
    private val itemTimeFormat by lazy { SimpleDateFormat("hh:mm:ss", Locale.getDefault()) }

    override fun getItemViewType(position: Int): Int = getItem(position)?.type ?: 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        if (viewType == 0) HeadViewHolder(parent) else ItemViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.item?.let { item ->
            if (holder is HeadViewHolder) {
                holder.text.text = item.time_request
            } else {
                holder as ItemViewHolder
                holder.trx_id.text = "#" + item.id_transaksi
                holder.trx_time.text = itemTimeFormat.format(item.time)
                holder.trx_name.text = item.buyer_reff
                holder.trx_reff.text = item.issuer_reff
                holder.trx_nominal.text =
                    if (item.minus) "-" else "" + "Rp " + item.nominal
                holder.trx_nominal.setTextColor(
                    if (item.minus) Color.parseColor("#b40b3a") else Color.parseColor(
                        "#139943"
                    )
                )
                if (item.fee > 0) {
                    holder.trx_tips.visibility = View.VISIBLE
                    holder.trx_tips.text = "tips: Rp ${item.fee}"
                } else {
                    holder.trx_tips.visibility = View.GONE
                }
            }
        }
    }

    inner class HeadViewHolder(
        val parent: ViewGroup,
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_qr_trx_head,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(view) {
        val text = view.findViewById<TextView>(R.id.text)
    }

    inner class ItemViewHolder(
        val parent: ViewGroup,
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_qr_trx,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(view) {
        val trx_id = view.findViewById<TextView>(R.id.trx_id)
        val trx_nominal = view.findViewById<TextView>(R.id.trx_nominal)
        val trx_name = view.findViewById<TextView>(R.id.trx_name)
        val trx_reff = view.findViewById<TextView>(R.id.trx_reff)
        val trx_tips = view.findViewById<TextView>(R.id.tips)
        val trx_time = view.findViewById<TextView>(R.id.trx_time)
    }
}