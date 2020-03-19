package com.bm.main.pos.feature.sell.addCustomer

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.toast
import kotlinx.android.synthetic.main.activity_add_customer_new.*


class AddCustomerActivity : BaseActivity<AddCustomerPresenter, AddCustomerContract.View>(), AddCustomerContract.View {

    override fun createPresenter(): AddCustomerPresenter {
        return AddCustomerPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_add_customer_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView(){
        btn_tambah.setOnClickListener {
            hideKeyboard()
            showLoadingDialog()
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            val phone = et_no_hp.text.toString().trim()
            checkIfAllFieldFilled(name, email, phone)
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_add_customer)

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
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
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    private fun checkIfAllFieldFilled(name:String, email:String, phone:String){
        if (name.isEmpty() && name == ""){
            et_name.error = "Nama Pelanggan Tidak Boleh Kosong"
            return
        }

        if (email.isEmpty() && email == ""){
            et_no_hp.error = "Nomor Handphone Pelanggan Tidak Boleh Kosong"
            return
        }

        if (phone.isEmpty() && phone == ""){
            et_email.error = "Alamat Email Pelanggan Tidak Boleh Kosong"
            return
        }

        getPresenter()?.onCheck(name,email,phone)
    }
}
