package com.bm.main.pos.feature.sell.chooseProduct

////import com.bm.main.pos.utils.glide.GlideApp
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.fpl.models.KabupatenModel
import com.bm.main.pos.R
import com.bm.main.pos.models.product.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_list_choose_product.view.*
import java.util.*

class ChooseProductAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listProduct = mutableListOf<Product>()
    private var checkStock = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_choose_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val product = listProduct!![position]
            holder.bindData(product, position)
        }
    }

    fun setItems(listProduct: List<Product>?) {
        this.listProduct.clear()
        val lastCount = itemCount
        listProduct?.let { this.listProduct.addAll(it) }
        notifyItemRangeInserted(lastCount, listProduct!!.size)
    }

    fun clearAdapter() {
        listProduct.clear()
        notifyDataSetChanged()
    }

    fun setCheckStok(isCheck: Boolean) {
        checkStock = isCheck
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val nameTv = view.tv_name
        private val kodeTv = view.tv_kode
        private val imageIv = view.iv_photo
        private val wrapper = view.ll_wrapper

        @SuppressLint("SetTextI18n")
        fun bindData(data: Product, position: Int) {
            nameTv.text = "${data.nama_barang}"
            kodeTv.text = "Kode: ${data.kodebarang}"

            if (checkStock) {
                wrapper.isEnabled = data.stok.toDouble() > 0
                wrapper.isClickable = data.stok.toDouble() > 0
            } else {
                wrapper.isEnabled = true
                wrapper.isClickable = true
            }

            if (data.gbr == null) {
                Glide.with(itemView.context)
                    .load(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(imageIv)

            } else {
                Glide.with(itemView.context)
                    .load(data.gbr)
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .into(imageIv)
            }

            wrapper.setOnClickListener {
                if (callback != null) {
                    callback?.onClick(data)
                }
            }

        }
    }

    var callback: ItemClickCallback? = null

    interface ItemClickCallback {
        fun onClick(data: Product)
    }
}
