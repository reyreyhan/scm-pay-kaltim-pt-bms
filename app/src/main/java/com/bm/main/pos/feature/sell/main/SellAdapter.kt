package com.bm.main.pos.feature.sell.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.utils.Helper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_list_sell.view.*

class SellAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Cart>()
    private val tempList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_sell, parent, false)
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
        private val countLayout = view.ll_count
        private val countTv = view.tv_count
        private val decreaseBtn = view.btn_minus
        private val increaseBtn = view.btn_plus
        private val deleteBtn = view.btn_delete
        private val noteTv = view.tv_note


        fun bindData(data: Cart, position: Int) {

            val product = data.product
            nameTv.text = "${product?.nama_barang}"
            priceTv.text = "Rp ${Helper.convertToCurrency(product?.hargajual!!)}"
            var stock = product?.stok!!.toDouble()
            stockTv.text = "Stok : ${Helper.convertToCurrency(stock!!)}"
            countTv.text = "${Helper.convertToCurrency(data.count!!)}"
            noteTv.text = "${data.note}"

            if (product?.gbr == null) {
                Glide.with(itemView.context)
                    .load(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(imageIv)
            } else {
                Glide.with(itemView.context)
                    .load(product?.gbr)
                    .error(Glide.with(imageIv).load("https://api-pos.fastpay.co.id/api/images/small_no_product.jpg"))
//                    .placeholder(R.drawable.logo)
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

            noteTv.setOnClickListener {
                callback?.onNote(data, position)
            }

            itemView.setOnClickListener {
                callback?.onCountDialog(data, position)
            }
        }
    }

    var callback: ItemClickCallback? = null

    interface ItemClickCallback {
        fun onDecrease(data: Cart, position: Int)
        fun onIncrease(data: Cart, position: Int)
        fun onDelete(data: Cart, position: Int)
        fun onNote(data: Cart, position: Int)
        fun onCountDialog(data: Cart, position: Int)
    }
}