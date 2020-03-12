package com.bm.main.pos.feature.setting.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bm.main.pos.base.BaseFragment
import com.bm.main.pos.R
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.fragment_setting.view.*
import com.bm.main.pos.feature.setting.account.AccountActivity
import com.bm.main.pos.feature.setting.store.StoreActivity
import com.bm.main.pos.feature.setting.staff.list.StaffListActivity


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class SettingFragment : BaseFragment<SettingPresenter, SettingContract.View>(),
    SettingContract.View {

    private val TAG = SettingFragment::class.java.simpleName

    private val ARGUMENT_PARAM = "ARGUMENT_PARAM"

    private lateinit var _view: View

    private val CODE_LOGIN = 1001
    private val CODE_ACCOUNT = 1002
    private var listener: Listener? = null

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param intros ArrayList Intro.
         * @return A new instance of fragment ContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
                SettingFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    override fun createPresenter(): SettingPresenter {
        return SettingPresenter(activity as Context, this)
    }

    override fun onCreateLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.fragment_setting, container, false)
    }


    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView(){
        _view.btn_account.setOnClickListener {
            openAccountPage()
        }

        _view.btn_store.setOnClickListener {
            openStorePage()
        }

        _view.btn_printer.setOnClickListener {
            openPrinterPage()
        }

        _view.btn_staff.setOnClickListener {
            openStaffPage()
        }

        _view.btn_privacy.setOnClickListener {
            openPrivacyPage()
        }
        _view.btn_term.setOnClickListener {
            openTermsPage()
        }
        _view.btn_help.setOnClickListener {
            openHelpPage()
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
        val intent = Intent(activity,AccountActivity::class.java)
        startActivityForResult(intent,CODE_ACCOUNT)
    }

    override fun openPrinterPage() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        startActivity(intent)
    }

    override fun openStorePage() {
        val browserIntent = Intent(activity,StoreActivity::class.java)
        startActivity(browserIntent)
    }

    override fun openStaffPage() {
        val browserIntent = Intent(activity,StaffListActivity::class.java)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement Listener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}
