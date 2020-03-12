package com.bm.main.pos.feature.manage.supplier.add

import android.os.Bundle
import android.view.MenuItem
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.callback.DialogCallback
import com.bm.main.pos.feature.dialog.PlacesDialog
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.activity_add_supplier.*
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.successDialog


class AddSupplierActivity : BaseActivity<AddSupplierPresenter, AddSupplierContract.View>(),
    AddSupplierContract.View,PlacesDialog.Listener {

    private val placeDialog = PlacesDialog.newInstance()

    override fun createPresenter(): AddSupplierPresenter {
        return AddSupplierPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_add_supplier
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
            val email = et_mail.text.toString().trim()
            val phone = et_phone.text.toString().trim()
            val address = et_address.text.toString().trim()
            val province = et_province.text.toString().trim()
            val city = et_city.text.toString().trim()
            getPresenter()?.onCheck(name,email,phone, address, province, city)
        }

        et_province.setOnClickListener {
            getPresenter()?.onCheckProvince()
        }

        et_city.setOnClickListener {
            getPresenter()?.onCheckCity()
        }

    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_add_supplier)

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
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

    override fun onClose(msg: String?,status: Int) {
        val callback = object: DialogCallback {
            override fun onSuccess() {
                setResult(status,intent)
                finish()
            }

            override fun onFailed() {

            }
        }

        if(msg.isNullOrEmpty() || msg.isNullOrBlank()){
            setResult(status,intent)
            finish()
        }
        else{
            successDialog(this,msg,callback)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun openPlace(title:String, type:Int, list: List<String>, selected: String) {
        if (placeDialog.dialog != null && placeDialog.dialog!!.isShowing) {

        } else {
            placeDialog.setDataProvince(title,type, list,selected)
            placeDialog.show(supportFragmentManager, "place")
        }
    }

    override fun onItemClicked(data: String, type:Int) {
        if(type == AppConstant.Code.CODE_PROVINCE){
            et_province.text = data
            getPresenter()?.setProvince(data)
            getPresenter()?.setCity("")
            et_city.text = ""
        }
        else{
            et_city.text = data
            getPresenter()?.setCity(data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }




}
