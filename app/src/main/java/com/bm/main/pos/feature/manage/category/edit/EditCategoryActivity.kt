package com.bm.main.pos.feature.manage.category.edit

import android.os.Bundle
import android.view.MenuItem
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.callback.DialogCallback
import com.bm.main.pos.ui.ext.toast
import kotlinx.android.synthetic.main.activity_edit_category.*
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.successDialog


class EditCategoryActivity : BaseActivity<EditCategoryPresenter, EditCategoryContract.View>(), EditCategoryContract.View {

    override fun createPresenter(): EditCategoryPresenter {
        return EditCategoryPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_edit_category
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
            getPresenter()?.onCheck(name)
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_edit_category)

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun showMessage(code:Int, msg:String?) {
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

    override fun setCategoryName(name: String?) {
        name?.let {
            et_name.setText(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }


}
