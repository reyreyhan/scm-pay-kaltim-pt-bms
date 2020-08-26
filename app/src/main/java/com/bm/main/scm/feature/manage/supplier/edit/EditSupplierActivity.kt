package com.bm.main.scm.feature.manage.supplier.edit

import android.os.Bundle
import android.view.MenuItem
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.callback.DialogCallback
import com.bm.main.scm.feature.dialog.PlacesDialog
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.utils.AppConstant
import kotlinx.android.synthetic.main.activity_edit_supplier.*
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.ext.successDialog


class EditSupplierActivity : BaseActivity<EditSupplierPresenter, EditSupplierContract.View>(),
    EditSupplierContract.View, PlacesDialog.Listener {

    private val placeDialog = PlacesDialog.newInstance()

    override fun createPresenter(): EditSupplierPresenter {
        return EditSupplierPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_edit_supplier
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
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
            title = getString(R.string.menu_edit_supplier)

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
    override fun setSupplier(name: String?, email: String?, phone: String?, address: String?, province: String?, city: String?)
    {
        name?.let {
            et_name.setText(it)
        }

        email?.let {
            et_mail.setText(it)
        }

        phone?.let {
            et_phone.setText(it)
        }

        address?.let {
            et_address.setText(it)
        }

        province?.let {
            et_province.text = it
        }

        city?.let {
            et_city.text = it
        }
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





}
