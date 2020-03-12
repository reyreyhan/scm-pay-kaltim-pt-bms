package com.bm.main.pos.feature.manage.product.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bm.main.pos.R
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.utils.Helper
import com.bumptech.glide.Glide
//import com.bm.main.pos.utils.glide.GlideApp
import kotlinx.android.synthetic.main.item_list_product.view.*

class ProductListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_product, parent, false))
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val product = listProduct[position]
            holder.bindData(product, position)
        }
    }

    fun setItems(listProduct: List<Product>?) {
        //this.listProduct.clear()
        val lastCount = itemCount
        listProduct?.let { this.listProduct.addAll(it) }
        notifyItemRangeInserted(lastCount,listProduct!!.size)
    }

    fun clearAdapter(){
        listProduct.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.tv_name
        private val priceTv = view.tv_price
        private val stockTv = view.tv_stok
        private val imageIv = view.iv_photo
        private val infoTv = view.tv_info
        private val deleteBtn = view.btn_delete

        fun bindData(data: Product, position: Int) {
            nameTv.text = "${data.nama_barang}"
            var desc = data.deskripsi
            if(desc.isNullOrEmpty() || desc.isNullOrBlank()){
                desc = "-"
            }

            infoTv.text = "$desc"
            priceTv.text = "Rp ${Helper.convertToCurrency(data.hargajual!!)}"
            stockTv.text = "Stok : ${Helper.convertToCurrency(data.stok!!)}"

            if(data.gbr == null){
                Glide.with(itemView.context)
                    .load(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(imageIv)

            }
            else{
                Glide.with(itemView.context)
                    .load(data.gbr)
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(imageIv)
            }

            itemView.setOnClickListener {
                if(callback != null){
                    callback?.onClick(data)
                }
            }

            deleteBtn.setOnClickListener {
                if(callback != null){
                    callback?.onDelete(data)
                }
            }

        }
    }

    var callback: ItemClickCallback ?= null

    interface ItemClickCallback{
        fun onClick(data: Product)
        fun onDelete(data: Product)
    }
}