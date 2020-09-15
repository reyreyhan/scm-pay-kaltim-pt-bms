package com.bm.main.scm.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.bm.main.fpl.activities.LoginActivity
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.R
import com.bm.main.scm.feature.drawerscm.DrawerActivity
import com.bm.main.scm.feature.home.HomeActivity
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper


abstract class BaseActivity<P : BasePresenter<V>, V : BaseViewImpl> : com.bm.main.fpl.activities.BaseActivity() {

    private lateinit var presenter: P
    private val progressDialog by lazy {
        AlertDialog.Builder(this).setCancelable(false).setView(R.layout.layout_progress_dialog).create()
    }

    fun setPresenter() {
        presenter = createPresenter()
    }

    fun getPresenter(): P? {
        return presenter
    }

    abstract fun createPresenter(): P

    abstract fun createLayout(): Int

    abstract fun startingUpActivity(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set content view, create layout implementation
        setContentView(createLayout())
        if (this is BaseViewImpl) {
            setPresenter()
            if (getPresenter() != null) {
                getPresenter()?.attachView(this as V)
            }
        }

        // init action
        startingUpActivity(savedInstanceState)
    }

    override fun onDestroy() {
        hideLoadingDialog()
        super.onDestroy()
        if (getPresenter() != null) {
            getPresenter()?.detachView()
        }
    }

    fun showLoadingDialog() {
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    fun hideLoadingDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun showToast(message: String) {
        toast(this, message)
    }

    fun showToast(resInt: Int) {
        showToast(getString(resInt))
    }

    fun hideKeyboard() {
        Helper.hideKeyboard(this)
    }

    fun restartMainActivity() {
        val intent = Intent(this, DrawerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun restartLoginActivity() {
        requestLogout()
        PreferenceClass.setLogOut()
        // val intent = Intent(this, LoginActivity::class.java)
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun restartMainActivity(menu: Int) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(AppConstant.DATA, menu)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun restartMainActivity(menu: Int, position: Int) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(AppConstant.DATA, menu)
        intent.putExtra(AppConstant.POSITION, position)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }


}