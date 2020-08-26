package com.bm.main.scm.feature.setting.store

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.callback.DialogCallback
import com.bm.main.scm.models.store.Store
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.ext.successDialog
import kotlinx.android.synthetic.main.activity_store.*


class StoreActivity : BaseActivity<StorePresenter, StoreContract.View>(), StoreContract.View {

    override fun createPresenter(): StorePresenter {
        return StorePresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_store
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

        sw_refresh.isRefreshing = true
        sw_refresh.setOnRefreshListener {
            reloadData()
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Toko"

            val backArrow = ContextCompat.getDrawable(this@StoreActivity,R.drawable.ic_back_pos)//resources.getDrawable(R.drawable.ic_back_pos)
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



    override fun setInfo(store: Store) {
        sw_refresh.isRefreshing = false
        store.nama_toko?.let {
            et_name.setText(it)
        }

        store.nohp?.let {
            et_phone.setText(it)
        }

        store.email?.let {
            et_email.setText(it)
        }

        store.alamat?.let {
            et_address.setText(it)
        }
    }

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        getPresenter()?.loadStore()
    }

}
