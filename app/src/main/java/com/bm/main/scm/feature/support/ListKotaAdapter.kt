package com.bm.main.scm.feature.support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.support.Kota
import kotlinx.android.synthetic.main.list_wilayah.view.*
import java.util.*

class ListKotaAdapter(var list:List<Kota>, val onItemClickListener:OnItemClickListener) :
    RecyclerView.Adapter<ListKotaAdapter.ViewHolder>(), Filterable {

    var filteredList = mutableListOf<Kota>()

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        fun bind(item:Kota, onItemClickListener: OnItemClickListener){
            view.textViewPlusNamaProduk.text = item.city_name
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
                    val resultList = mutableListOf<Kota>()
                    for (row in list) {
                        if (row.city_name!!.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
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
                filteredList = results?.values as ArrayList<Kota>
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(item:Kota)
    }
}