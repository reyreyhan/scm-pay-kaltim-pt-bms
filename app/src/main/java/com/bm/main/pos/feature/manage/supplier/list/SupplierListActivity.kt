package com.bm.main.pos.feature.manage.supplier.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.manage.supplier.add.AddSupplierActivity
import com.bm.main.pos.feature.manage.supplier.edit.EditSupplierActivity
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.EndlessRecyclerViewScrollListener
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.activity_list_supplier.*

class SupplierListActivity : BaseActivity<SupplierListPresenter, SupplierListContract.View>(),
    SupplierListContract.View {

    val adapter = SupplierListAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val CODE_OPEN_ADD = 1001
    private val CODE_OPEN_EDIT = 1002

    override fun createPresenter(): SupplierListPresenter {
        return SupplierListPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_list_supplier
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
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

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onFirstItemVisible(isFirstItem: Boolean) {
                sw_refresh.isEnabled = isFirstItem && adapter.itemCount > 0
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

            }
        }
        rv_list.addOnScrollListener(scrollListener)

        adapter.callback = object : SupplierListAdapter.ItemClickCallback{
            override fun onClick(data: Supplier) {
                data?.let {
                    openEditPage(data)
                }
            }

            override fun onDelete(data: Supplier) {
                showLoadingDialog()
                getPresenter()?.deleteSupplier(data.id_supplier!!)
            }
        }

        et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.clearAdapter()
                sw_refresh.isRefreshing = true
                getPresenter()?.searchSupplier(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        btn_add.setOnClickListener {
            openAddPage()
        }

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
            title = getString(R.string.lbl_manage_supplier_title)

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun setData(list: List<Supplier>) {
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

    override fun showSuccessMessage(msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        msg?.let {
            Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
        }
        reloadData()

    }

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadSuppliers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            reloadData()
        }
    }

    override fun openAddPage() {
        val intent = Intent(this,AddSupplierActivity::class.java)
        startActivityForResult(intent,CODE_OPEN_ADD)
    }

    override fun openEditPage(data: Supplier) {
        val intent = Intent(this,EditSupplierActivity::class.java)
        intent.putExtra(AppConstant.DATA,data)
        startActivityForResult(intent,CODE_OPEN_EDIT)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

}
