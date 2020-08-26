package com.bm.main.scm.feature.setting.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.setting.account.AccountActivity
import com.bm.main.scm.feature.setting.staff.list.StaffListActivity
import com.bm.main.scm.feature.setting.store.StoreActivity
import com.bm.main.scm.utils.AppConstant
import kotlinx.android.synthetic.main.fragment_setting.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class SettingActivity : BaseActivity<SettingPresenter, SettingContract.View>(),
    SettingContract.View {

    private val TAG = SettingActivity::class.java.simpleName

    private val ARGUMENT_PARAM = "ARGUMENT_PARAM"

    private val CODE_LOGIN = 1001
    private val CODE_ACCOUNT = 1002
    private var listener: Listener? = null


    override fun createPresenter(): SettingPresenter {
        return SettingPresenter(this, this)
    }

    private fun renderView(){
        btn_account.setOnClickListener {
            openAccountPage()
        }

        btn_printer.setOnClickListener {
            openPrinterPage()
        }

        btn_staff.setOnClickListener {
            openStaffPage()
        }

        btn_privacy.setOnClickListener {
            openPrivacyPage()
        }
        btn_term.setOnClickListener {
            openTermsPage()
        }
        btn_help.setOnClickListener {
            openHelpPage()
        }

        btn_logout.setOnClickListener {
            restartLoginActivity()
        }
    }

    override fun openPrivacyPage() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(AppConstant.URL.PRIVACY))
        startActivity(browserIntent)
    }

    override fun openTermsPage() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(AppConstant.URL.TERM))
        startActivity(browserIntent)
    }

    override fun openHelpPage() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(AppConstant.URL.HELP))
        startActivity(browserIntent)
    }

    override fun openAccountPage() {
        val intent = Intent(this,AccountActivity::class.java)
        startActivityForResult(intent,CODE_ACCOUNT)
    }

    override fun openPrinterPage() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        startActivity(intent)
    }

    override fun openStorePage() {
        val browserIntent = Intent(this,StoreActivity::class.java)
        startActivity(browserIntent)
    }

    override fun openStaffPage() {
        val browserIntent = Intent(this,StaffListActivity::class.java)
        startActivity(browserIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CODE_ACCOUNT && resultCode == Activity.RESULT_OK){
            listener?.onReloadProfile()
        }
    }

    interface Listener {
        fun onReloadProfile()
    }


    override fun createLayout(): Int {
        return R.layout.fragment_setting
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
//        if (context is Listener) {
//            listener = context
//        } else {
//            throw RuntimeException("$context must implement Listener")
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        listener = null
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Pengaturan"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }
}
