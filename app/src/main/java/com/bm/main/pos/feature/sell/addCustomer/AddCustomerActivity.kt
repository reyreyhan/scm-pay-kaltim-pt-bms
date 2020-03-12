package com.bm.main.pos.feature.sell.addCustomer

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.activity_add_customer_sell.*


class AddCustomerActivity : BaseActivity<AddCustomerPresenter, AddCustomerContract.View>(), AddCustomerContract.View {

    override fun createPresenter(): AddCustomerPresenter {
        return AddCustomerPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_add_customer_sell
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView(){

        btn_save.setOnClickListener {
            hideKeyboard()
            showLoadingDialog()
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            val phone = et_phone.text.toString().trim()
            val address = et_address.text.toString().trim()
            getPresenter()?.onCheck(name,email,phone,address)
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_add_customer)

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
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

    override fun showMessage(code: Int, msg: String?) {
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

    override fun onSuccess(data: Customer) {
        val newIntent = intent
        newIntent.putExtra(AppConstant.DATA,data)
        setResult(Activity.RESULT_OK,newIntent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }


}
