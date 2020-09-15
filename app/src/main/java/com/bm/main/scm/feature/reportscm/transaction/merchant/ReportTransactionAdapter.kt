package com.bm.main.scm.feature.reportscm.transaction.merchant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.bm.main.scm.R
import com.bm.main.scm.rabbit.QrTransaction
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.item_report_merchant_scm_content.view.*
import kotlinx.android.synthetic.main.item_report_merchant_scm_header.view.*
import java.text.SimpleDateFormat
import java.util.*


class ReportTransactionAdapter(groups: List<ExpandableGroup<*>?>?,
var reportTransactionListener: ReportTransactionListener) :
    ExpandableRecyclerViewAdapter<ReportTransactionAdapter.MutationGroupViewHolder,
            ReportTransactionAdapter.MutationViewHolder>(groups) {

    val list = getGroups()!!

    override fun onCreateGroupViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MutationGroupViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report_merchant_scm_header, parent, false)
        return MutationGroupViewHolder(
            view
        )
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MutationViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report_merchant_scm_content, parent, false)
        return MutationViewHolder(
            view
        )
    }

    override fun onBindChildViewHolder(
        holder: MutationViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>,
        childIndex: Int
    ) {
        val transaction: QrTransaction = (group as TransactionGroup).items[childIndex]
        holder.setContent(transaction, reportTransactionListener)
    }

    override fun onBindGroupViewHolder(
        holder: MutationGroupViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        holder.setGroupTitle(group!!)
        var sum = 0.0
        for (i in 0 until group.items.size){
            var item = group.items[i] as QrTransaction
            sum+= item.nominal.toFloat()
        }
        val subtitle = "${group.itemCount} transaksi - Omzet Rp ${sum.toInt()}"
        holder.setGroupSubtitle(subtitle)
    }

    class MutationGroupViewHolder(itemView: View) : GroupViewHolder(itemView) {
        private val groupTitle = itemView.tv_cashier
        private val groupSubtitle = itemView.tv_transactions
        private val icon = itemView.iv_dropdown

        fun setGroupTitle(group: ExpandableGroup<*>) {
            groupTitle.text = group.title
        }

        fun setGroupSubtitle(subtitle: String) {
            groupSubtitle.text = subtitle
        }

        override fun expand() {
            super.expand()
            icon.startAnimation(animate(false))
        }

        override fun collapse() {
            super.collapse()
            icon.startAnimation(animate(true))
        }

        private fun animate(up: Boolean): Animation? {
            val anim: Animation =
                AnimationUtils.loadAnimation(itemView.context, if (up) R.anim.rotate_up else R.anim.rotate_down)
            anim.interpolator = LinearInterpolator() // for smooth animation
            return anim
        }
    }

    class MutationViewHolder(itemView: View) : ChildViewHolder(itemView) {
        private val tvName = itemView.tv_name
        private val tvDateTime = itemView.tv_datetime
        private val tvTransactionId = itemView.tv_transaction_id
        private val tvAmmount = itemView.tv_ammount
        private val respDateFormat by lazy {
            SimpleDateFormat(
                "ddd, dd-MM-yyyy HH:mm:ss",
                Locale.getDefault()
            )
        }
        private val context = itemView.context
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

    interface ReportTransactionListener{
        fun OnReportTransactionClick(transaction: QrTransaction)
    }
}