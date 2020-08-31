package com.bm.main.scm.feature.manage.cashier.list

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.ui.ext.htmlText
import kotlinx.android.synthetic.main.item_cashier_manage_scm.view.*

class CashierListAdapter(private var list: ArrayList<CashierObject>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cashier_manage_scm, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(list[position], position)
            holder.btnActivate.setOnClickListener {
                var active = list[position].active
                if (!active) {
                    active = true
                    notifyItemChanged(position)
                } else {
                    active = false
                    notifyItemChanged(position)
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCashier = view.tv_cashier
        val tvStatus = view.tv_status
        val btnEdit = view.btn_edit
        val btnActivate = view.iv_qris
        val context = view.context

        @SuppressLint("ResourceType")
        fun bind(item: CashierObject, position: Int) {
            tvCashier.text = "Kasir ${item.id} - ${item.name}"
            if (item.active) {
                tvStatus.htmlText(
                    "<span>Status: </span><span style=\"color:${context.resources.getString(
                        R.color.md_green_500
                    )}\">Aktif</span>"
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnActivate.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.colorAccent))
                }
            } else {
                tvStatus.text = "Status: Menunggu Konfirmasi"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ColorStateList.valueOf(context.resources.getColor(R.color.md_grey_400))
                }
            }
        }
    }
}