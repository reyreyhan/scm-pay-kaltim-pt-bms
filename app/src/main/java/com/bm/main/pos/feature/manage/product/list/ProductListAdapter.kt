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
import kotlinx.android.synthetic.main.item_list_choose_product_2.view.*

class ProductListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_choose_product_2, parent, false))
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
        private val kodeTv = view.tv_kode
        private val hargaTv = view.tv_harga
        private val countTv = view.tv_count
        private val imageIv = view.iv_photo

        fun bindData(data: Product, position: Int) {
            nameTv.text = "${data.nama_barang}"
            var desc = data.deskripsi
            if(desc.isNullOrEmpty() || desc.isNullOrBlank()){
                desc = "-"
            }
            countTv.text = Helper.convertToCurrency(data.stok)
            hargaTv.text = "Rp Rp ${Helper.convertToCurrency(data.hargabeli)} - Rp ${Helper.convertToCurrency(data.hargajual)}"
//            infoTv.text = "$desc"
//            priceTv.text = "Rp ${Helper.convertToCurrency(data.hargajual!!)}"
//            stockTv.text = "Stok : ${Helper.convertToCurrency(data.stok!!)}"

            kodeTv.text = data.kodebarang

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

//            deleteBtn.setOnClickListener {
//                if(callback != null){
//                    callback?.onDelete(data)
//                }
//            }

        }
    }

    var callback: ItemClickCallback ?= null

    interface ItemClickCallback{
        fun onClick(data: Product)
//        fun onDelete(data: Product)
    }
}