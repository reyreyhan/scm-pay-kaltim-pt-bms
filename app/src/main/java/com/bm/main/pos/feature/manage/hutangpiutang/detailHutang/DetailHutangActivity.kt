package com.bm.main.pos.feature.manage.hutangpiutang.detailHutang

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.appbar.AppBarLayout
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.models.hutangpiutang.DetailHutang
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.toast
import com.bumptech.glide.Glide
//import com.bm.main.pos.utils.glide.GlideApp
import kotlinx.android.synthetic.main.activity_hutang_detail.*


class DetailHutangActivity : BaseActivity<DetailHutangPresenter, DetailHutangContract.View>(), DetailHutangContract.View {

    val adapter = DetailHutangAdapter()

    override fun createPresenter(): DetailHutangPresenter {
        return DetailHutangPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_hutang_detail
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView(){
        setupToolbar()
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {

                var title = ""
                getPresenter()?.getTitleName()?.let {
                    title = it
                }
                ctl.title = title

            } else {
                ctl.title = ""

            }
        })
        ctl.title = ""

        sw_refresh.isRefreshing = true
        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun onClose(status: Int) {
        setResult(status,intent)
        finish()
    }

    override fun setSupplier(name: String?, email: String?, phone: String?, address: String?, url: String?) {
        name?.let {
            tv_name.text = it
        }

        email?.let {
            tv_email.text = it
        }

        phone?.let {
            tv_phone.text = it
        }

        address?.let {
            tv_address.text = it
        }

        url?.let {
            Glide.with(this)
                .load(it)
                .error(R.drawable.ic_user_pos)
                .transform(CenterCrop(), CircleCrop())
                .into(iv_photo)
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun showMessage(code: Int, msg: String?) {
        sw_refresh.isRefreshing = false
        hideLoadingDialog()
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            msg?.let {
                toast(this,it)
            }

        }

    }

    private fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadDetailSupplier()
    }

    override fun setPiutang(tagihan: String, piutang: String, total: String, jatuhTempo: String) {
        sw_refresh.isRefreshing = false
        tv_tagihan.text = tagihan
        tv_piutang.text = piutang
        tv_total.text = total
        tv_jatuh_tempo.text = jatuhTempo
    }

    override fun setList(list: List<DetailHutang.Data>) {
        adapter.setItems(list)
    }

}
