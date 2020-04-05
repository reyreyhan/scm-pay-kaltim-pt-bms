package com.bm.main.pos.feature.manage.hutangpiutang.lastHutang

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.events.onReloadTransaction
import com.bm.main.pos.feature.manage.hutangpiutang.hutang.HutangAdapter
import com.bm.main.pos.feature.transaction.detail.old.DetailActivity
import com.bm.main.pos.models.hutangpiutang.Hutang
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.EndlessRecyclerViewScrollListener
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.activity_last_hutang.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class LastHutangActivity : BaseActivity<LastHutangPresenter, LastHutangContract.View>(),
    LastHutangContract.View {

    val adapter = HutangAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener


    override fun createPresenter(): LastHutangPresenter {
        return LastHutangPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_last_hutang
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView() {
        sw_refresh.setOnRefreshListener {
            scrollListener.resetState()
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

        adapter.callback = object : HutangAdapter.ItemClickCallback{
            override fun onClick(data: Hutang.Data) {
                data?.let {
                    openDetailStruk(it.no_invoice!!)
                }
            }
        }

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onFirstItemVisible(isFirstItem: Boolean) {
                sw_refresh.isEnabled = isFirstItem && adapter.itemCount > 0
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

            }
        }
        rv_list.addOnScrollListener(scrollListener)



        et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.clearAdapter()
                sw_refresh.isRefreshing = true
                getPresenter()?.searchHutang(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }


    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_hutang_supplier_all)

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun setData(list: List<Hutang.Data>) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        adapter.setItems(list)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun showErrorMessage(code: Int, msg: String) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{

            toast(this,msg)
        }

    }

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadHutang()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            reloadData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun openDetailStruk(id: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(AppConstant.CODE,AppConstant.Code.CODE_TRANSACTION_SUPPLIER)
        intent.putExtra(AppConstant.DATA,id)
        startActivity(intent)
    }

    @Subscribe
    fun onEvent(event: onReloadTransaction){
        if(event.isReload){
            reloadData()
        }
    }

}
