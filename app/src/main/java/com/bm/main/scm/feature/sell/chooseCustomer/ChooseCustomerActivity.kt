package com.bm.main.scm.feature.sell.chooseCustomer

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.manage.customer.detail.CustomerDetailActivity
import com.bm.main.scm.feature.sell.addCustomer.AddCustomerActivity
import com.bm.main.scm.models.customer.Customer
import com.bm.main.scm.ui.EndlessRecyclerViewScrollListener
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.utils.AppConstant
import kotlinx.android.synthetic.main.activity_choose_customer_new.*

class ChooseCustomerActivity : BaseActivity<ChooseCustomerPresenter, ChooseCustomerContract.View>(),
    ChooseCustomerContract.View {

    val adapter = ChooseCustomerAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun createPresenter(): ChooseCustomerPresenter {
        return ChooseCustomerPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_choose_customer_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
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

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onFirstItemVisible(isFirstItem: Boolean) {
                sw_refresh.isEnabled = isFirstItem && adapter.itemCount > 0
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

            }
        }
        rv_list.addOnScrollListener(scrollListener)

        adapter.callback = object : ChooseCustomerAdapter.ItemClickCallback{
            override fun onClick(data: Customer) {
                data?.let {
                    onSelected(it)
                }
            }
        }

        et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.clearAdapter()
                sw_refresh.isRefreshing = true
                getPresenter()?.search(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        btn_pelanggan_baru.setOnClickListener {
            val intent = Intent(this, AddCustomerActivity::class.java)
            startActivityForResult(intent, 100)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item!!)
    }


    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_choose_customer)

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun setData(list: List<Customer>) {
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
//        if(code == RestException.CODE_USER_NOT_FOUND){
//            restartLoginActivity()
//        }
//        else{
            toast(this,msg)
//        }
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
        getPresenter()?.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onSelected(data: Customer) {
//        val newintent:Intent = intent
//        newintent.putExtra(AppConstant.DATA,data)
//        setResult(RESULT_OK,newintent)
        val intent = Intent(this, CustomerDetailActivity::class.java)
        intent.putExtra("isTransaction", getPresenter()?.isTransaction)
        intent.putExtra(AppConstant.DATA, data)
        startActivityForResult(intent, 101)
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            reloadData()
        }
        else if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val customer = data!!.getSerializableExtra(AppConstant.DATA) as Customer
            val newintent: Intent = intent
            newintent.putExtra(AppConstant.DATA, customer)
            setResult(RESULT_OK, newintent)
            finish()
        }else{
            reloadData()
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun setBackgroundButtonAddCustomer(){
        btn_pelanggan_baru.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
    }
}
