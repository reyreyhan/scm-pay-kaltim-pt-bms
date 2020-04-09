package com.bm.main.pos.feature.register

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BaseActivity<RegisterPresenter, RegisterContract.View>(), RegisterContract.View {

    override fun createPresenter(): RegisterPresenter {
        return RegisterPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_register
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView(){
        /*btn_login.setOnClickListener {
            finish()
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home) finish()
        return super.onOptionsItemSelected(item!!)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.transparent)))
            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }


}
