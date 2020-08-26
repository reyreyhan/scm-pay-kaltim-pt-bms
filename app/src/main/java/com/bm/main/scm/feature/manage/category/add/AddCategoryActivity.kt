package com.bm.main.scm.feature.manage.category.add

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.callback.DialogCallback
import com.bm.main.scm.ui.ext.toast
import kotlinx.android.synthetic.main.activity_add_category.*
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.ext.successDialog

class AddCategoryActivity : BaseActivity<AddCategoryPresenter, AddCategoryContract.View>(),
    AddCategoryContract.View {

    override fun createPresenter(): AddCategoryPresenter {
        return AddCategoryPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_add_category
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView() {
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
            title = getString(R.string.menu_add_category)

//            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            val backArrow =
                ContextCompat.getDrawable(this@AddCategoryActivity, R.drawable.ic_back_pos)
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
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            msg?.let {
                toast(this, it)
            }

        }

    }

    override fun onClose(msg: String?, status: Int) {
        val callback = object : DialogCallback {
            override fun onSuccess() {
                setResult(status, intent)
                finish()
            }

            override fun onFailed() {

            }
        }

        if (msg.isNullOrEmpty() || msg.isNullOrBlank()) {
            setResult(status, intent)
            finish()
        } else {
            successDialog(this, msg, callback)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }
}
