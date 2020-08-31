package com.bm.main.scm.feature.home.cashier

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.activities.HomeActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.fpl.utils.DetectConnection
import com.bm.main.fpl.utils.Device
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseFragment
import com.bm.main.scm.feature.reportscm.transaction.cashier.ReportTransactionCashierActivity
import com.bm.main.scm.feature.setting.main.SettingActivity
import com.bm.main.scm.models.menu.Menu
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.ext.toast
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_home_cashier_scm.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeCashierFragment : BaseFragment<HomeCashierPresenter, HomeCashierContract.View>(),
    HomeCashierContract.View {

    private lateinit var _view: View
    val adapter = MenuAdapter()
//
//    private val CODE_LOGIN = 1001
//    private val CODE_ACCOUNT = 1002

    //private var onMenu: MenuClick? = null

    companion object {

        @JvmStatic
        fun newInstance() = HomeCashierFragment()
    }

    override fun createPresenter(): HomeCashierPresenter {
        return HomeCashierPresenter(activity as Context, this)
    }

    override fun onCreateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home_cashier_scm, container, false)
    }

    override fun initAction(view: View) {
        _view = view
        renderView()
//        getPresenter()?.onViewCreated()
//        reloadData()
    }

    private fun renderView() {
        setupDatePicker()
        initRecyclerView()
        _view.ll_menu_report.setOnClickListener {
            startActivity(Intent(activity, ReportTransactionCashierActivity::class.java))
        }
//        val spaceItem = resources.getDimensionPixelSize(R.dimen.standard_margin)
//        _view.rv_list.addItemDecoration(GridItemOffsetDecoration(spaceItem))
//        val layoutManager = GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
//        _view.rv_list.layoutManager = layoutManager
//        _view.rv_list.adapter = adapter

        //val isCoverGrosir = PreferenceClass.getUser().isCoverGrosir
        /*if (isCoverGrosir == "0") {
            //            frameTabGroupHome.setVisibility(View.GONE);
            _view.btn_grocery.setText("Pembayaran QR")
//            _view.btn_grocery.setText(getString(R.string.btn_grosir))
        }*/

//        adapter.callback = object : MenuAdapter.ItemClickCallback {
//            override fun onClick(data: Menu) {
//                when (data.id) {
//                    0 -> {
//                        val intent = Intent(requireContext(), AddProductMainActivity::class.java)
//                        startActivity(intent)
//                    }
//                    1 -> {
//                        val intent = Intent(requireContext(), TransactionHistoryActivity::class.java)
//                        startActivity(intent)
//                    }
//                    2 -> {
//                        val intent = Intent(requireContext(), ReportActivity::class.java)
//                        startActivity(intent)
//                    }
//                    3 -> {
//                        val intent = Intent(requireContext(), ProductListActivity::class.java)
//                        startActivity(intent)
//                    }
//                    4 -> {
//                        val intent = Intent(requireContext(), PiutangCustomerActivity::class.java)
//                        startActivity(intent)
//                    }
//                    5 -> {
//                        val intent = Intent(requireContext(), ChooseCustomerActivity::class.java)
//                        intent.putExtra("isTransaction", false)
//                        startActivity(intent)
//                    }
//                    6->{
//                        val url =
//                            "https://profit.fastpay.co.id/"
//                        val browserIntent =
//                            Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                        startActivity(browserIntent)
//                    }
//                }
//                //EventBus.getDefault().post(onMenuClicked(data.id!!))
//                //onMenu?.selectMenu(data.id!!)
//            }
//        }

//        _view.sw_refresh.setOnRefreshListener {
//            _view.sw_refresh.isRefreshing = true
//            reloadData()
//        }
//
//        _view.ll_info.setOnClickListener {
//            openAccountPage()
//        }

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

    private fun initRecyclerView() {
        val listTransaction = mutableListOf<TransactionHomeCashier>()
        listTransaction.add(TransactionHomeCashier("OVO - Rizqy Ali Syaifurrahman", "Senin, 27 Agustus 2020 - 16:17", "12341231235", 25000.00))
        listTransaction.add(TransactionHomeCashier("Link Aja - Rizqy Ali Syaifurrahman", "Senin, 28 Agustus 2020 - 16:17", "5435324223", 50000.00))
        listTransaction.add(TransactionHomeCashier("OVO - Rizqy Ali Syaifurrahman", "Senin, 27 Agustus 2020 - 16:17", "12341231235", 25000.00))
        listTransaction.add(TransactionHomeCashier("Link Aja - Rizqy Ali Syaifurrahman", "Senin, 28 Agustus 2020 - 16:17", "5435324223", 50000.00))
        listTransaction.add(TransactionHomeCashier("OVO - Rizqy Ali Syaifurrahman", "Senin, 27 Agustus 2020 - 16:17", "12341231235", 25000.00))
        listTransaction.add(TransactionHomeCashier("Link Aja - Rizqy Ali Syaifurrahman", "Senin, 28 Agustus 2020 - 16:17", "5435324223", 50000.00))
        val adapter = HomeTransactionAdapter(listTransaction)
        _view.rv_transactions.adapter = adapter
        _view.rv_transactions.layoutManager = LinearLayoutManager(_view.context, RecyclerView.VERTICAL, false)
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

    @SuppressLint("SimpleDateFormat")
    private fun setupDatePicker() {
        val now = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateNow = formatter.format(now.timeInMillis)
        _view.tv_date_range.text = "${dateNow} - ${dateNow}"
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
        val picker = builder.build()
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.addOnPositiveButtonClickListener {
            val date1 = formatter.format(it.first?.let { it1 -> Date(it1) })
            val date2 = formatter.format(it.second?.let { it1 -> Date(it1) })
            _view.tv_date_range.text = "${date1} - ${date2}"
        }
        _view.tv_date_range.setOnClickListener {
            picker.show(activity?.supportFragmentManager!!, picker.toString())
        }
    }

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
//        _view.sw_refresh.isRefreshing = false
//        PreferenceClass.putString("alamat_toko", address)
//        //onMenu?.setProfile(name, address, city, phone, url)
//        _view.tv_nama.text = name
//        _view.tv_alamat.text = address
//        _view.tv_no_hp.text = phone
//        _view.tv_kota.text = city
//        _view.tv_omzet.text = omset
//        if (url.isBlank() || url.isEmpty()) {
//            Glide.with(activity!!).load(R.drawable.logo).transform(CenterCrop(), CircleCrop())
//                .into(_view.iv_photo)
//            //_view.tv_caption.visibility = View.VISIBLE
//        } else {
//            Glide.with(activity!!).load(url).error(R.drawable.logo).placeholder(R.drawable.logo)
//                .transform(CenterCrop(), CircleCrop()).into(_view.iv_photo)
//            //_view.tv_caption.visibility = View.GONE
//        }

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
//        getPresenter()?.onDestroy()
    }

    override fun onResume() {
        super.onResume()
//        reloadData()
        //context?.let { (activity as DrawerActivity).openShowCaseHomeFragment(it) }
    }

//    interface MenuClick {
//        fun selectMenu(resId: Int)
//        fun setProfile(name: String, address: String, city: String, phone: String, url: String)
//    }

    override fun reloadData() {
//        _view.sw_refresh.isRefreshing = true
//        getPresenter()?.loadProfile()

    }

    override fun showErrorMessage(code: Int, msg: String) {
//        _view.sw_refresh.isRefreshing = false
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
                        HomeCashierFragment::class.java.simpleName
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
//        startActivityForResult(intent, CODE_ACCOUNT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == CODE_ACCOUNT && resultCode == Activity.RESULT_OK) {
//            reloadData()
//        }
    }
}

data class TransactionHomeCashier(
    var title: String? = null,
    var date: String? = null,
    var id: String? = null,
    var ammount: Double? = null
)
