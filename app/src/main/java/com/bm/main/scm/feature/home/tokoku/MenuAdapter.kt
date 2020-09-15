package com.bm.main.scm.feature.home.tokoku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.menu.Menu
import kotlinx.android.synthetic.main.item_menu_home.view.*

class MenuAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listMenu = mutableListOf<Menu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_menu_home, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listMenu.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val product = listMenu[position]
            holder.bindData(product, position)
        }
    }

    fun setItems(listMenu: List<Menu>?) {
        this.listMenu.clear()
        val lastCount = itemCount
        listMenu?.let { this.listMenu.addAll(it) }
        notifyItemRangeInserted(lastCount, listMenu!!.size)
    }

    fun clearAdapter() {
        listMenu.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.tv_menu
        private val imageIv = view.iv_menu

        fun bindData(data: Menu, position: Int) {

            nameTv.text = listMenu[position].name
            imageIv.setImageDrawable(itemView.context.resources.getDrawable(listMenu[position].image!!))

//            nameTv.text = "${data.name}"
//
//            if (data.image == null) {
//                Glide.with(itemView.context)
//                    .load(R.drawable.logo)
//                    //.transform(CenterCrop(), RoundedCorners(8))
//                    .into(imageIv)
//
//            } else {
//                Glide.with(itemView.context)
//                    .load(data.image)
//                    .error(R.drawable.logo)
//                    .placeholder(R.drawable.logo)
//                    //.transform(CenterCrop(), RoundedCorners(8))
//                    .into(imageIv)
//            }

            itemView.setOnClickListener {
                callback?.onClick(data)
            }
        }
    }

    var callback: ItemClickCallback? = null

    interface ItemClickCallback {
        fun onClick(data: Menu)
    }
}