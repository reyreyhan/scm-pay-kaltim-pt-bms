package com.bm.main.scm.feature.manage.cashier.add

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.dialog.SuccessDialog
import com.bm.main.scm.feature.manage.cashier.list.CashierListActivity
import kotlinx.android.synthetic.main.activity_qris_cashier_add.*

class CashierAddActivity : BaseActivity<CashierAddPresenter, CashierAddContract.View>(), CashierAddContract.View, SuccessDialog.SuccessDialogListener {
    override fun createPresenter(): CashierAddPresenter {
        return CashierAddPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_qris_cashier_add
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }


    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Tambah QRIS Kasir"
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun renderView() {
        initForm()
        initButton()
    }

    private fun initButton() {
        btn_add_cashier.setOnClickListener {
            getPresenter()?.addCashier(et_cashier_num_hp.text.toString(), et_cashier_name.text.toString(), et_pin.text.toString())
        }
    }

    private fun initForm() {
        et_cashier_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                getPresenter()?.checkForm(et_cashier_num_hp.text.toString(), p0.toString(), et_pin.text.toString())
            }
        })

        et_cashier_num_hp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                getPresenter()?.checkForm(p0.toString(), et_cashier_name.text.toString(), et_pin.text.toString())
            }
        })

        et_pin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                getPresenter()?.checkForm(et_cashier_num_hp.text.toString(), et_cashier_name.text.toString(), p0.toString())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onPositiveButtonDialog() {
        startActivity(Intent(this, CashierListActivity::class.java))
        finish()
    }

    override fun toastError(msg: String) {
        showToast(msg)
    }

    override fun dialogSuccess(title: String, msg: String) {
        SuccessDialog.newInstance(
            title,
            msg
        ).show(supportFragmentManager, SuccessDialog.TAG)
    }

    override fun enableAddButton(enable: Boolean) {
        btn_add_cashier.isEnabled = enable
    }
}
