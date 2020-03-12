package com.bm.main.pos.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bm.main.pos.R
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.Helper
import com.bm.main.pos.utils.AppConstant
//import com.bm.main.pos.feature.login.LoginActivity
import com.bm.main.fpl.activities.LoginActivity
import com.bm.main.pos.feature.drawer.DrawerActivity


abstract class BaseFragment<P : BasePresenter<V>, V: BaseViewImpl> : Fragment() {

    private lateinit var presenter: P
    private lateinit var progressDialog: AlertDialog


    abstract fun createPresenter(): P

    fun setPresenter() {
        presenter = createPresenter()
    }

    fun getPresenter(): P? {
        return presenter
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // load layout
        val view = onCreateLayout(inflater, container, savedInstanceState)
        setupProgressDialog()
        if (this is BaseViewImpl) {
            setPresenter()
            if (getPresenter() != null) {
                getPresenter()!!.attachView(this as V)
            }
        }

        // call init action
        initAction(view)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (getPresenter() != null) {
            getPresenter()!!.detachView()
        }
    }

    protected abstract fun onCreateLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    protected abstract fun initAction(view: View)

    private fun setupProgressDialog() {
        val builder = AlertDialog.Builder(activity!!)
        builder.setCancelable(false)
        builder.setView(R.layout.layout_progress_dialog)
        progressDialog = builder.create()
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

    fun showToast(message: String) {
        toast(message)
    }

    fun showToast(resInt: Int) {
        showToast(getString(resInt))
    }

    fun hideKeyboard() {
        Helper.hideKeyboard(activity!!)
    }

    fun restartMainActivity() {
        val intent = Intent(activity, DrawerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun restartLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun restartMainActivity(menu:Int) {
        val intent = Intent(activity, DrawerActivity::class.java)
        intent.putExtra(AppConstant.DATA,menu)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun restartMainActivity(menu:Int,position:Int) {
        val intent = Intent(activity, DrawerActivity::class.java)
        intent.putExtra(AppConstant.DATA,menu)
        intent.putExtra(AppConstant.POSITION,position)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}