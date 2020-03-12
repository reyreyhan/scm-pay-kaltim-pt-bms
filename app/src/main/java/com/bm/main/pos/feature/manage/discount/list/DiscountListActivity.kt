package com.bm.main.pos.feature.manage.discount.list

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.models.discount.Discount
import com.bm.main.pos.ui.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_list_discount.*

class DiscountListActivity : BaseActivity<DiscountListPresenter, DiscountListContract.View>(),
    DiscountListContract.View {

    val adapter = DiscountListAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun createPresenter(): DiscountListPresenter {
        return DiscountListPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_list_discount
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView() {
        sw_refresh.isRefreshing = false
        sw_refresh.setOnRefreshListener {
            sw_refresh.isRefreshing = false
            scrollListener.resetState()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
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
            title = getString(R.string.lbl_manage_discount_title)
        }

    }

    override fun setData(list: List<Discount>) {
        adapter.setItems(list)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

}
