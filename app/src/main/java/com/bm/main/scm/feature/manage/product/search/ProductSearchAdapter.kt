package com.bm.main.scm.feature.manage.product.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.callback.AdapterItemCallback
import com.bm.main.scm.models.product.Product
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_search.view.*

class ProductSearchAdapter(val itemHandler: AdapterItemCallback<Product>? = null, val imgPlaceholder: String = "") :
    PagedListAdapter<Product, ProductSearchAdapter.Viewholder>(object :
        DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean = oldItem.id_barang == newItem.id_barang

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean = oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder =
        Viewholder(parent)

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }

    inner class Viewholder(
        parent: ViewGroup,
        view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product_search,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Product, pos: Int) {
            itemView.apply {
                Glide.with(image).load(item.gbr)
                    .error(Glide.with(image).load(imgPlaceholder))
                    .into(image)

                name.text = item.nama_barang
                desc.text = item.deskripsi
                itemHandler?.let { h -> action.setOnClickListener { h.onItemClick(item, pos) } }
            }
        }
    }
}