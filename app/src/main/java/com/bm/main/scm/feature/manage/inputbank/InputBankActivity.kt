package com.bm.main.scm.feature.manage.inputbank

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.cashout.CashoutActivity
import kotlinx.android.synthetic.main.activity_input_bank_scm.*

class InputBankActivity : BaseActivity<InputBankPresenter, InputBankContract.View>(), InputBankContract.View {
    override fun createPresenter(): InputBankPresenter {
        return InputBankPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_input_bank_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
//        getPresenter()?.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Input Data Bank"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.white)))
            titleColor = resources.getColor(R.color.primaryText)
            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }
    }

    private fun renderView() {
        initForm()
        initButton()
    }

    private fun initButton() {
        btn_add_bank.setOnClickListener {
            startActivity(Intent(this, CashoutActivity::class.java))
        }
    }

    private fun initForm() {
        et_bank_account.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_bank_owner.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }
}
