package com.bm.main.scm.feature.kulakan.addProduct

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.NumberTextWatcher
import com.bm.main.scm.utils.*
import kotlinx.android.synthetic.main.activity_add_manual_kulakan.*

class AddActivity : BaseActivity<AddPresenter, AddContract.View>(),
    AddContract.View {

    override fun createPresenter(): AddPresenter {
        return AddPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_add_manual_kulakan
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        btn_save.setOnClickListener {
            showLoadingDialog()
            val name = et_name.text.toString().trim()
            val buy = et_buy.text.toString().trim()
            val sell = et_sell.text.toString().trim()
            val stok = et_stok.text.toString().trim()
            getPresenter()?.onCheck(name, buy, sell, stok)
        }

        et_sell.addTextChangedListener(NumberTextWatcher(et_sell))
        et_buy.addTextChangedListener(NumberTextWatcher(et_buy))
        et_stok.addTextChangedListener(NumberTextWatcher(et_stok))
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_add_product)

//            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            val backArrow = ContextCompat.getDrawable(this@AddActivity, R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
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

    override fun onSuccess(data: Product) {
        val newIntent = intent
        newIntent.putExtra(AppConstant.DATA, data)
        setResult(Activity.RESULT_OK, newIntent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }
}