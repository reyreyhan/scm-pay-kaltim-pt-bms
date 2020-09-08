package com.bm.main.scm.feature.reportscm.mutation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.rabbit.QrMutation
import kotlinx.android.synthetic.main.item_report_mutation_scm.view.*
import org.jetbrains.anko.textColor
import java.text.SimpleDateFormat
import java.util.*

class ReportMutationAdapter (var list:MutableList<QrMutation>) : RecyclerView.Adapter<ReportMutationAdapter.ViewHolder>() {

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val tvName = view.tv_name
        private val tvDateTime = view.tv_datetime
        private val tvTransactionId = view.tv_transaction_id
        private val tvAmmount = view.tv_ammount
        private val respDateFormat by lazy {
            SimpleDateFormat(
                "EEE, dd-MM-yyyy HH:mm:ss",
                Locale.getDefault()
            )
        }
        private val context = view.context
        fun setContent(item:QrMutation){
            tvName.text = item.merchant_reff
            tvDateTime.text = respDateFormat.format(item.time)
            tvTransactionId.text = item.trx_reff
            val credit = item.credit.toFloat()
            val debit = item.debit.toFloat()
            if (credit > 0.0){
                tvAmmount.text = "Rp ${credit.toInt()}"
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    tvAmmount.textColor = context.getColor(R.color.md_red_500)
                }
            }
            if (debit > 0.0){
                tvAmmount.text = "Rp ${debit.toInt()}"
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    tvAmmount.textColor = context.getColor(R.color.md_green_500)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_report_mutation_scm, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(list[position])
    }
}