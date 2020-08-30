package com.bm.main.scm.feature.registermerchant

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import kotlinx.android.synthetic.main.item_register_scm_criteria_help_layout.view.*


class HelpBottomSheetAdapter(private val listItem:List<String>) : RecyclerView.Adapter<HelpBottomSheetAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_register_criteria_help_layout,parent,false))
    }

    override fun getItemCount(): Int = listItem.size

    class ViewHolder(val v: View) : RecyclerView.ViewHolder(v){
        fun bind(item:String){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.tv_item.text = Html.fromHtml(item, Html.FROM_HTML_MODE_COMPACT);
            } else {
                v.tv_item.text = Html.fromHtml(item);
            }
        }
    }
}