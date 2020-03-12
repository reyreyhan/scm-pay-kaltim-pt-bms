package com.bm.main.pos.feature.setting.password

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MenuItem
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.callback.DialogCallback
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.successDialog
import kotlinx.android.synthetic.main.activity_change_password.*

class PasswordActivity : BaseActivity<PasswordPresenter, PasswordContract.View>(), PasswordContract.View {

    override fun createPresenter(): PasswordPresenter {
        return PasswordPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_change_password
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView(){
        btn_save.setOnClickListener {
            hideKeyboard()
            showLoadingDialog()
            val lama = et_password.text.toString().trim()
            val baru = et_new_password.text.toString().trim()
            val konfirmasi = et_confirm_password.text.toString().trim()
            getPresenter()?.onCheck(lama,baru,konfirmasi)
        }

        btn_password.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked){
                et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else{
                et_password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        btn_new_password.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked){
                et_new_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else{
                et_new_password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        btn_confirm_password.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked){
                et_confirm_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else{
                et_confirm_password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Ubah Password"

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


}
