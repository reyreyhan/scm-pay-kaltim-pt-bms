package com.bm.main.pos.feature.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.activities.HomeActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.fpl.utils.DetectConnection
import com.bm.main.fpl.utils.Device
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseFragment
import com.bm.main.pos.feature.manage.hutangpiutang.piutangCustomer.PiutangCustomerActivity
import com.bm.main.pos.feature.manage.product.list.ProductListActivity
import com.bm.main.pos.feature.manage.product.main.AddProductMainActivity
import com.bm.main.pos.feature.report.main.ReportActivity
import com.bm.main.pos.feature.sell.chooseCustomer.ChooseCustomerActivity
import com.bm.main.pos.feature.setting.main.SettingActivity
import com.bm.main.pos.feature.transaction.historyTransaction.TransactionHistoryActivity
import com.bm.main.pos.models.menu.Menu
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.GridItemOffsetDecoration
import com.bm.main.pos.ui.ext.toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.fragment_home_new.view.*

class HomeFragment : BaseFragment<HomePresenter, HomeContract.View>(), HomeContract.View {

    private lateinit var _view: View
    val adapter = MenuAdapter()

    private val CODE_LOGIN = 1001
    private val CODE_ACCOUNT = 1002

    //private var onMenu: MenuClick? = null

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun createPresenter(): HomePresenter {
        return HomePresenter(activity as Context, this)
    }

    override fun onCreateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home_new, container, false)
    }

    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated()
        reloadData()
    }

    private fun renderView() {
        val spaceItem = resources.getDimensionPixelSize(R.dimen.standard_margin)
        _view.rv_list.addItemDecoration(GridItemOffsetDecoration(spaceItem))
        val layoutManager = GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
        _view.rv_list.layoutManager = layoutManager
        _view.rv_list.adapter = adapter
        //val isCoverGrosir = PreferenceClass.getUser().isCoverGrosir
        /*if (isCoverGrosir == "0") {
            //            frameTabGroupHome.setVisibility(View.GONE);
            _view.btn_grocery.setText("Pembayaran QR")
//            _view.btn_grocery.setText(getString(R.string.btn_grosir))
        }*/
        adapter.callback = object : MenuAdapter.ItemClickCallback {
            override fun onClick(data: Menu) {
                when (data.id) {
                    0 -> {
                        val intent = Intent(requireContext(), AddProductMainActivity::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(requireContext(), TransactionHistoryActivity::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(requireContext(), ReportActivity::class.java)
                        startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent(requireContext(), ProductListActivity::class.java)
                        startActivity(intent)
                    }
                    4 -> {
                        val intent = Intent(requireContext(), PiutangCustomerActivity::class.java)
                        startActivity(intent)
                    }
                    5 -> {
                        val intent = Intent(requireContext(), ChooseCustomerActivity::class.java)
                        intent.putExtra("isTransaction", false)
                        startActivity(intent)
                    }
                    6->{
                        val url =
                            "https://profit.fastpay.co.id/"
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(browserIntent)
                    }
                }
                //EventBus.getDefault().post(onMenuClicked(data.id!!))
                //onMenu?.selectMenu(data.id!!)
            }
        }

        _view.sw_refresh.setOnRefreshListener {
            _view.sw_refresh.isRefreshing = true
            reloadData()
        }

        _view.ll_info.setOnClickListener {
            openAccountPage()
        }
        /*_view.btn_ppob.setOnClickListener {
            openPPOBPage()
        }
        _view.btn_grocery.setOnClickListener {
            onMenu?.selectMenu(R.id.nav_qris)
//            openKulakanPage()
        }

        _view.btn_lengkapi.setOnClickListener {
            startActivity(
                Intent(requireActivity(), WebViewActivity::class.java).putExtra(
                    "url",
                    "http://api-salesforce.fastpay.co.id/index.php/profit/lengkapinik/${PreferenceClass.getId()}"
                ).putExtra("title", "Lengkapi NIK")
            )
        }*/
    }

    fun enableQrMenu(enable: Boolean) {
        /*if (::_view.isInitialized)
            _view.btn_grocery.isEnabled = enable*/
    }

    fun setLengkapiButtonVisibility(visible: Int) {
        /*if (::_view.isInitialized)
            _view.btn_lengkapi.visibility = visible*/
    }

//    fun enableMenu(id: Int, enable: Boolean) {
//        val menus = getPresenter()?.menus
//        menus?.firstOrNull { it.id == id }?.let {
//            if (it.enabled != enable) {
//                it.enabled = enable
//                adapter.notifyItemChanged(menus.indexOf(it))
//            }
//        }
//    }

    override fun setMenu(list: List<Menu>) {
        adapter.setItems(list)
    }

    override fun setProfile(
        name: String,
        address: String,
        city: String,
        phone: String,
        url: String,
        omset: String
    ) {
        _view.sw_refresh.isRefreshing = false
        PreferenceClass.putString("alamat_toko", address)
        //onMenu?.setProfile(name, address, city, phone, url)
        _view.tv_nama.text = name
        _view.tv_alamat.text = address
        _view.tv_no_hp.text = phone
        _view.tv_kota.text = city
        _view.tv_omzet.text = omset
        if (url.isBlank() || url.isEmpty()) {
            Glide.with(activity!!).load(R.drawable.logo).transform(CenterCrop(), CircleCrop())
                .into(_view.iv_photo)
            //_view.tv_caption.visibility = View.VISIBLE
        } else {
            Glide.with(activity!!).load(url).error(R.drawable.logo).placeholder(R.drawable.logo)
                .transform(CenterCrop(), CircleCrop()).into(_view.iv_photo)
            //_view.tv_caption.visibility = View.GONE
        }

    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is MenuClick) {
//            onMenu = context
//        } else {
//            throw RuntimeException("$context must implement MenuClick")
//        }
//    }

    override fun onDetach() {
        super.onDetach()
        //onMenu = null
        getPresenter()?.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        reloadData()
        //context?.let { (activity as DrawerActivity).openShowCaseHomeFragment(it) }
    }

//    interface MenuClick {
//        fun selectMenu(resId: Int)
//        fun setProfile(name: String, address: String, city: String, phone: String, url: String)
//    }

    override fun reloadData() {
        _view.sw_refresh.isRefreshing = true
        getPresenter()?.loadProfile()

    }

    override fun showErrorMessage(code: Int, msg: String) {
        _view.sw_refresh.isRefreshing = false
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            toast(msg)
        }
    }

    override fun openKulakanPage() {
        if (!DetectConnection.isOnline(activity as Context)) {
            (context as BaseActivity).new_popup_alert_failure(
                activity as Context,
                "Cek Koneksi Internet Anda"
            )
        } else {
            val apps = "com.android.chrome"
            val installed = Device.checkAppInstall(context, apps)
            if (installed) {
                (context as BaseActivity).let {
                    it.logEventFireBase(
                        "Home",
                        PreferenceClass.getUser().id_outlet,
                        "New FMCG",
                        EventParam.EVENT_SUCCESS,
                        HomeFragment::class.java.simpleName
                    )
                    it.buildCustomTabsIntent().launchUrl(
                        it,
                        Uri.parse(PreferenceClass.getUser().grosir + PreferenceClass.getAuth()!!)
                    )
                }
            } else {
                Toast.makeText(
                    context,
                    "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu",
                    Toast.LENGTH_LONG
                ).show()
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$apps")
                )
                startActivity(webIntent)
            }
        }
    }

    override fun openPPOBPage() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent);
    }

    override fun openAccountPage() {
        val intent = Intent(activity, SettingActivity::class.java)
        startActivityForResult(intent, CODE_ACCOUNT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_ACCOUNT && resultCode == Activity.RESULT_OK) {
            reloadData()
        }
    }
}
