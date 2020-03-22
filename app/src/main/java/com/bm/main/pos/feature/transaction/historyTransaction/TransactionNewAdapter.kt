package com.bm.main.pos.feature.transaction.historyTransaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.utils.Helper
import com.zhukic.sectionedrecyclerview.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.item_list_aktivitas_header.view.*
import kotlinx.android.synthetic.main.item_list_aktivitas_item.view.*


class TransactionNewAdapter:
    SectionedRecyclerViewAdapter<TransactionNewAdapter.HeaderViewHolder, TransactionNewAdapter.TransactionViewHolder>() {

    var listTransaction = arrayListOf<Transaction>()
    var itemClick: OnItemClick? = null

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTitle = itemView.findViewById<TextView>(R.id.subheaderText)
    }

    class TransactionViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val total = itemView.findViewById<TextView>(R.id.title)
        val content = itemView.findViewById<TextView>(R.id.subtitle)
        val date = itemView.findViewById<TextView>(R.id.date)
    }

    override fun getItemSize(): Int = listTransaction.size

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_aktivitas_item, parent, false))
    }

    override fun onCreateSubheaderViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_aktivitas_header, parent, false))
    }

    interface OnItemClick {
        fun onItemClick(
            transaction: Transaction
        )
    }

    override fun onBindItemViewHolder(holder: TransactionViewHolder, itemPosition: Int) {
        val transaction = listTransaction[itemPosition]
        holder.total.text = transaction.totalorder
        holder.content.text = transaction.nama_barang
        holder.itemView.setOnClickListener {
            itemClick?.onItemClick(transaction)
        }
    }

    override fun onBindSubheaderViewHolder(
        subheaderHolder: HeaderViewHolder,
        nextItemPosition: Int
    ) {
        val nextTransaction = listTransaction[nextItemPosition]
        val date = Helper.getDateFormat(subheaderHolder.itemView.context,
            nextTransaction.tanggal!!,"yyyy-MM-dd","EEE, dd MMMM yyyy")
        subheaderHolder.mTitle.text = date;
    }

    override fun onPlaceSubheaderBetweenItems(position: Int): Boolean {
        val transactionDate: String = listTransaction[position].tanggal!!
        val nextTransactionDate: String = listTransaction[position + 1].tanggal!!
//        return transactionDate != nextTransactionDate
        return true
    }

    fun setList(list: ArrayList<Transaction>) {
        listTransaction = list
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<Transaction> {
        return listTransaction
    }

    fun clearAdapter(){
        listTransaction.clear()
        notifyDataSetChanged()
    }
}
