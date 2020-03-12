package com.bm.main.pos.feature.manage.supplier.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.supplier.Supplier
import kotlinx.android.synthetic.main.item_list_supplier.view.*

class SupplierListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Supplier>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_supplier, parent, false))
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

    fun setItems(listProduct: List<Supplier>?) {
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
        private val infoTv = view.tv_phone
        private val deleteBtn = view.btn_delete


        fun bindData(data: Supplier, position: Int) {
            nameTv.text = "${data.nama_supplier}"
            infoTv.text = "${data.telpon}"

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

    var callback: ItemClickCallback?= null

    interface ItemClickCallback{
        fun onClick(data: Supplier)
        fun onDelete(data: Supplier)
    }
}