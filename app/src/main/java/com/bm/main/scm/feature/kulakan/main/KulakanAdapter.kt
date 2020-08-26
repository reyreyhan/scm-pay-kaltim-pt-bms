package com.bm.main.scm.feature.kulakan.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bm.main.scm.R
import com.bm.main.scm.models.cart.Cart
import com.bm.main.scm.utils.Helper
import com.bumptech.glide.Glide
//import com.bm.main.pos.utils.glide.GlideApp
import kotlinx.android.synthetic.main.item_list_kulakan.view.*

class KulakanAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Cart>()
    private val tempList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_kulakan, parent, false)
        )
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

    fun updateItem(cart: Cart, position: Int) {
        listProduct[position] = cart
        notifyItemChanged(position)
    }

    fun deleteItem(position: Int) {
        listProduct.removeAt(position)
        tempList.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(cart: Cart) {
        val pos = tempList.indexOf(cart.product?.id_barang)
        if (pos > -1) {
            listProduct[pos] = cart
            tempList[pos] = cart.product?.id_barang!!
            notifyItemChanged(pos)
        } else {
            listProduct.add(cart)
            tempList.add(cart.product?.id_barang!!)
            notifyItemInserted(itemCount - 1)
        }

    }

    fun setItems(listProduct: List<Cart>?) {
        //this.listProduct.clear()
        val lastCount = itemCount
        listProduct?.let {
            it.forEach { cart ->
                this.listProduct.add(cart)
                tempList.add(cart.product?.id_barang!!)
            }

        }
        notifyItemRangeInserted(lastCount, listProduct!!.size)
    }

    fun clearAdapter() {
        listProduct.clear()
        tempList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.tv_name
        private val priceTv = view.tv_price
        private val stockTv = view.tv_stok
        private val imageIv = view.iv_photo
        private val countTv = view.tv_count
        private val decreaseBtn = view.btn_minus
        private val increaseBtn = view.btn_plus
        private val deleteBtn = view.btn_delete


        @SuppressLint("SetTextI18n")
        fun bindData(data: Cart, position: Int) {

            val product = data.product
            nameTv.text = "${product?.nama_barang}"
            priceTv.text = "Rp ${Helper.convertToCurrency(product?.hargabeli!!)}"
            var stock = product.stok!!.toDouble()
            stockTv.text = "Stok : ${Helper.convertToCurrency(stock)}"
            countTv.text = Helper.convertToCurrency(data.count!!)


            if (product?.gbr == null) {
                Glide.with(itemView.context)
                    .load(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(imageIv)

            } else {
                Glide.with(itemView.context)
                    .load(product?.gbr)
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(imageIv)
            }

            increaseBtn.setOnClickListener {
                callback?.onIncrease(data, position)
            }


            decreaseBtn.setOnClickListener {
                callback?.onDecrease(data, position)

            }

            deleteBtn.setOnClickListener {
                callback?.onDelete(data, position)
            }

            countTv.setOnClickListener {
                callback?.onCountDialog(data, position)
            }

        }
    }

    var callback: ItemClickCallback? = null

    interface ItemClickCallback {
        fun onDecrease(data: Cart, position: Int)
        fun onIncrease(data: Cart, position: Int)
        fun onDelete(data: Cart, position: Int)
        fun onCountDialog(data: Cart, position: Int)
    }
}