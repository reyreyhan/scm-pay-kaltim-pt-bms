package com.bm.main.scm.feature.profilescm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import kotlinx.android.synthetic.main.item_profile_merchant_scm.view.*

class ProfileSCMAdapter(private var list: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_profile_merchant_scm, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(list[position], position)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("ResourceType")
        fun bind(item: String, position: Int) {
            when(position){
                0->view.tv_label.text = "NIK"
                1->view.tv_label.text = "Nomor Handphone"
                2->view.tv_label.text = "Email"
                3->view.tv_label.text = "Kategori Usaha"
                4->view.tv_label.text = "Kriteria Usaha"
                5->view.tv_label.text = "NPWP"
                6->view.tv_label.text = "Alamat"
                7->view.tv_label.text = "Provinsi"
                8->view.tv_label.text = "Kota/Kabupaten"
                9->view.tv_label.text = "Kecamatan"
                10->view.tv_label.text = "Kelurahan"
            }
            view.tv_value.text = item
        }
    }
}