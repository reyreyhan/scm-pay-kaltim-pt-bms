package com.bm.main.scm.feature.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.register.RegisterMerchantActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_login_scm.*

class LoginActivity : BaseActivity<LoginPresenter, LoginContract.View>(), LoginContract.View {

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_login_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    override fun enableLoginBtn(isLogin: Boolean) {
        btn_login.isEnabled = isLogin
    }

    private fun renderView() {
        initTab()
        initRegisterLabelText()
        initForm()
    }

    private fun initForm() {
        et_num_hp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val pwd = et_pin.text.toString()
                val hp = p0.toString()
                getPresenter()?.onBtnLoginCheck(hp, pwd)
            }
        })

        et_pin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val hp = et_num_hp.toString()
                val pwd = p0.toString()
                getPresenter()?.onBtnLoginCheck(hp, pwd)
            }
        })

//        btn_show_password.setOnCheckedChangeListener {view, isChecked ->
//            if(isChecked){
//                et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
//            }
//            else{
//                et_password.transformationMethod = PasswordTransformationMethod.getInstance()
//            }
//        }

        btn_login.setOnClickListener {
            val mail = et_num_hp.text.toString()
            val pwd = et_pin.text.toString()
            getPresenter()?.onLogin(mail, pwd)
        }
    }

    override fun showLoginSuccess() {
        restartMainActivity()
    }

    override fun openRegisterPage() {
        startActivity(Intent(this, RegisterMerchantActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    private fun initTab() {
        tab_login.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(p0: TabLayout.Tab?) {
                switchLoginTab(p0!!.position)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }
        })
    }

    private fun initRegisterLabelText() {
        val ss = SpannableString(getString(R.string.login_activity_lbl_register))
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }

            override fun onClick(p0: View) {
                startActivity(Intent(this@LoginActivity, RegisterMerchantActivity::class.java))
            }
        }
        ss.setSpan(clickableSpan, 21, ss.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        lbl_register.text = ss
        lbl_register.movementMethod = LinkMovementMethod.getInstance()
        lbl_register.highlightColor = Color.TRANSPARENT
    }

    fun switchLoginTab(selected: Int) {
        when (selected) {
            0 -> {
                lbl_et_pin.text = "PIN Owner"
                lbl_register.visibility = View.VISIBLE
            }
            1 -> {
                lbl_et_pin.text = "PIN Kasir"
                lbl_register.visibility = View.GONE
            }
        }
    }

}
