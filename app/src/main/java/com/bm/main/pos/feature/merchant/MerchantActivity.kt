package com.bm.main.pos.feature.merchant

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.transaction.detail.DetailSuccessActivity
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.EndlessRecyclerViewScrollListener
import com.bm.main.pos.utils.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_merchant_new.*

class MerchantActivity : BaseActivity<MerchantPresenter, MerchantContract.View>(), MerchantContract.View,
MerchantTransactionAdapter.ItemClickCallback{

    val adapter = MerchantTransactionAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun createPresenter(): MerchantPresenter {
        return MerchantPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_merchant_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Merchant"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    private fun renderView() {
        sw_refresh.isRefreshing = false
        sw_refresh.setOnRefreshListener {
            scrollListener.resetState()
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
        adapter.callback = object : MerchantTransactionAdapter.ItemClickCallback {
            override fun onClick(data: Transaction) {
                openDetail(data.no_invoice!!)
            }
        }

        tv_name_merchant.text = PreferenceClass.getString("nama_toko").toUpperCase()
        tv_no_id.text = PreferenceClass.getString("nmid").toUpperCase()
    }

    override fun showErrorMessage(code: Int, msg: String) {
        sw_refresh.isRefreshing = false
        if (code == RestException.CODE_USER_NOT_FOUND) {
//            restartLoginActivity()
        } else {
            rv_list.visibility = View.GONE
            showToast(msg)
        }
    }


    override fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadTransaction()
    }

    override fun setList(list: List<Transaction>) {
        sw_refresh.isRefreshing = false
        rv_list.visibility = View.VISIBLE
        adapter.setItems(list)
    }

    override fun openDetail(id: String) {
        val intent = Intent(this, DetailSuccessActivity::class.java)
        intent.putExtra(AppConstant.DATA, id)
        startActivity(intent)
    }

    override fun setProfile(
        name: String,
        address: String,
        city: String,
        phone: String,
        url: String,
        omset: String
    ) {
        sw_refresh.isRefreshing = false
        PreferenceClass.putString("alamat_toko", address)
        tv_nama.text = name
        tv_alamat.text = address
        tv_no_hp.text = phone
        tv_kota.text = city
        tv_omzet.text = omset
        if (url.isBlank() || url.isEmpty()) {
            Glide.with(this).load(R.drawable.logo).transform(CenterCrop(), CircleCrop())
                .into(iv_photo)
        } else {
            Glide.with(this).load(url).error(R.drawable.logo).placeholder(R.drawable.logo)
                .transform(CenterCrop(), CircleCrop()).into(iv_photo)
        }
    }

    override fun onClick(data: Transaction) {
        if (data.no_invoice!=null){
            openDetail(data.no_invoice!!)
        }
    }
}