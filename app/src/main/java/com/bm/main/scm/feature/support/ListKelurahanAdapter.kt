package com.bm.main.scm.feature.support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.support.Kelurahan
import kotlinx.android.synthetic.main.list_wilayah.view.*
import java.util.*

class ListKelurahanAdapter(var list:List<Kelurahan>, val onItemClickListener:OnItemClickListener) :
    RecyclerView.Adapter<ListKelurahanAdapter.ViewHolder>(), Filterable {

    var filteredList = mutableListOf<Kelurahan>()

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        fun bind(item:Kelurahan, onItemClickListener: OnItemClickListener){
            view.textViewPlusNamaProduk.text = item.detail
            view.linMainListProduk.setOnClickListener {
                onItemClickListener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_wilayah, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onItemClickListener)
    }

    override fun getItemCount(): Int = list.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList = list.toMutableList()
                } else {
                    val resultList = mutableListOf<Kelurahan>()
                    for (row in list) {
                        if (row.detail!!.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    filteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<Kelurahan>
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(item:Kelurahan)
    }
}