package com.bm.main.pos.feature.transaction.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.activity_transaction_detail_new.*

class DetailSuccessActivity :
    com.bm.main.pos.base.BaseActivity<DetailPresenterNew, DetailContractNew.View>(), DetailContractNew.View{

    val adapter = DetailAdapterNew()

    override fun createPresenter(): DetailPresenterNew {
        return DetailPresenterNew(
            this,
            this
        )
    }

    override fun createLayout(): Int {
        return R.layout.activity_transaction_detail_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        sw_refresh.isRefreshing = false
        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Detail Laporan"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun setProducts(list: List<DetailTransaction.Data>) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        adapter.setItems(list)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun showMessage(code: Int, msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        toast(this, msg!!)
    }

    override fun reloadData() {
        hideLoadingDialog()
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadDetail()
    }

    override fun onClose(status: Int) {
        setResult(status, intent)
        finish()
    }

    @SuppressLint("SetTextI18n")
    override fun setInfo(
        id: String,
        total: String?,
        date: String?,
        payment: String,
        bayar: String?,
        kembalian: String?,
        hutang: String?
    ) {
        tv_id_transaksi.text = id

        when(payment){
            "non tunai"->{
                tv_metode_pembayaran.text = "Non Tunai"
            }
            "tunai"->{
                tv_metode_pembayaran.text = "Tunai"
            }
            "hutang"->{
                tv_metode_pembayaran.text = "Hutang"

            }
        }

        date?.let{
            tv_tanggal.text = it
        }
        total?.let{
            tv_total_item.text = total
        }
        bayar?.let{
            tv_tunai.text = it
        }
        kembalian?.let{
            tv_kembalian.text = it
            ll_cashback.visibility = View.VISIBLE
        }
        hutang?.let{
            tv_hutang.text = it
            ll_hutang.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}