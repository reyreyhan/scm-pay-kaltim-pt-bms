package com.bm.main.pos.feature.sell.edit

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.NumberTextWatcher
import com.bm.main.pos.utils.*
import com.bumptech.glide.Glide
//import com.bm.main.pos.utils.glide.GlideApp
import kotlinx.android.synthetic.main.activity_edit_manual_sell.*


class EditActivity : BaseActivity<EditPresenter, EditContract.View>(), EditContract.View {

    override fun createPresenter(): EditPresenter {
        return EditPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_edit_manual_sell
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView(){
        btn_save.setOnClickListener {
            showLoadingDialog()
            val buy = et_buy.text.toString().trim()
            val sell = et_sell.text.toString().trim()
            val stok = et_stok.text.toString().trim()
            getPresenter()?.onCheck(buy, sell, stok)
        }

        et_sell.addTextChangedListener(NumberTextWatcher(et_sell))
        et_buy.addTextChangedListener(NumberTextWatcher(et_buy))
        et_stok.addTextChangedListener(NumberTextWatcher(et_stok))
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_sell_manual)

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

    override fun onSuccess(data: Product) {
        val newIntent = intent
        newIntent.putExtra(AppConstant.DATA,data)
        setResult(Activity.RESULT_OK,newIntent)
        finish()
    }

    override fun onClose() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun setName(value: String) {
        et_name.text = value
    }

    override fun loadPhoto(path: String?) {
        btn_camera.visibility = View.GONE
        path?.let {
            if(it.isNotBlank() || it.isNotEmpty()){
                btn_camera.visibility = View.VISIBLE
                Glide.with(this)
                    .load(it)
                    .transform(CenterCrop(), RoundedCorners(4))
                    .into(iv_photo)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onBackPressed() {
        onClose()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }
}
