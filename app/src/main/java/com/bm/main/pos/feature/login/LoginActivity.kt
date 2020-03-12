package com.bm.main.pos.feature.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.CompoundButton
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login_pos.*


class LoginActivity : BaseActivity<LoginPresenter, LoginContract.View>(), LoginContract.View {

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_login_pos
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    override fun enableLoginBtn(isLogin: Boolean) {
        btn_login.isEnabled = isLogin
    }

    private fun renderView(){
        et_email.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val pwd = et_password.text.toString()
                val mail = p0.toString()
                getPresenter()?.onBtnLoginCheck(mail,pwd)
            }
        })

        et_password.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val mail = et_email.text.toString()
                val pwd = p0.toString()
                getPresenter()?.onBtnLoginCheck(mail,pwd)
            }
        })

        btn_password.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked){
                et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
            else{
                et_password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        btn_login.setOnClickListener {
            val mail = et_email.text.toString()
            val pwd = et_password.text.toString()
            getPresenter()?.onLogin(mail,pwd)
        }

        btn_register.setOnClickListener {
            openRegisterPage()
        }
    }

    override fun showLoginSuccess() {
        restartMainActivity()
    }

    override fun openRegisterPage() {
        startActivity(Intent(this, RegisterActivity::class.java))

    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }


}
