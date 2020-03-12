package com.bm.main.pos.feature.setting.staff.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.staff.Staff
import kotlinx.android.synthetic.main.item_list_staff.view.*

class StaffListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Staff>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_staff, parent, false))
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

    fun setItems(listProduct: List<Staff>?) {
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


        fun bindData(data: Staff, position: Int) {
            nameTv.text = "${data.nama_lengkap}"
            infoTv.text = "${data.no_telp}"

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
        fun onClick(data: Staff)
        fun onDelete(data: Staff)
    }
}