package com.bm.main.scm.feature.sell.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.models.cart.Cart
import com.bm.main.scm.utils.Helper
import kotlinx.android.synthetic.main.item_list_sell_new.view.*

class SellAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Cart>()
    private val tempList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_sell_new, parent, false)
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

    fun getList():List<Cart>{
        return listProduct
    }

    fun clearAdapter() {
        listProduct.clear()
        tempList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName = view.tv_name
        private val tvTotal = view.tv_total
        private val tvProductCount = view.tv_product_count

        fun bindData(data: Cart, position: Int) {

            val product = data.product
            val count = "${Helper.convertToCurrency(data.count!!)}"
            tvName.text = "${product?.nama_barang}"
            tvTotal.text = "Harga : $count x Rp ${Helper.convertToCurrency(product?.hargajual!!)}"
            tvProductCount.text = count

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