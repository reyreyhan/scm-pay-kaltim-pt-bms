package com.bm.main.scm.feature.cashout

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_cashout_bank_scm.*

class CashoutActivity : BaseActivity<CashoutPresenter, CashoutContract.View>(),
    CashoutContract.View {

    private var merchantLogin = true

    override fun createPresenter(): CashoutPresenter {
        return CashoutPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_cashout_bank_scm
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
            title = "Cashout ke Bank"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.white)))
            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                backArrow.setTint(resources.getColor(android.R.color.black))
            }
            setHomeAsUpIndicator(backArrow)
        }
    }

    private fun renderView() {
        initForm()
        initButton()
        initHelp()
    }

    private fun initButton() {
        btn_cashout.setOnClickListener { }

    }

    private fun initHelp() {
        val ss = SpannableString(getString(R.string.login_activity_lbl_register))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

            override fun onClick(p0: View) {

            }
        }
        ss.setSpan(clickableSpan, ss.length - 11, ss.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_lbl_2.text = ss
        tv_lbl_2.movementMethod = LinkMovementMethod.getInstance()
        tv_lbl_2.highlightColor = Color.TRANSPARENT
    }

    private fun initForm() {
        et_withdraw_ammount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_withdraw_information.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_pin.addTextChangedListener(object : TextWatcher {
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
