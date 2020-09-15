package com.bm.main.scm.feature.homescm.cashier

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.bm.main.scm.feature.dialog.QrisDinamisDialog
import com.bm.main.scm.feature.homescm.merchant.HomeTransactionAdapter
import com.bm.main.scm.feature.qrisscm.QrisSCMActivity
import com.bm.main.scm.feature.reportscm.detail.ReportTransactionDetailActivity
import com.bm.main.scm.feature.reportscm.transaction.cashier.ReportTransactionCashierActivity
import com.bm.main.scm.feature.setting.main.SettingActivity
import com.bm.main.scm.models.menu.Menu
import com.bm.main.scm.rabbit.QrTransaction
import com.bm.main.scm.rabbit.QrisService
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.ext.toast
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home_cashier_scm.view.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeCashierFragment : BaseFragment<HomeCashierPresenter, HomeCashierContract.View>(),
    HomeCashierContract.View, HomeTransactionAdapter.OnItemClickListener {

    private val dateFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    private val itemDateFormat by lazy { SimpleDateFormat("d MMMM yyyy", Locale.getDefault()) }
    private val respDateFormat by lazy {
        SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS",
            Locale.getDefault()
        )
    }
    private val listDates by lazy { ArrayList<String>() }
    val dateNow by lazy {
        Calendar.getInstance().time
    }

    @Inject
    lateinit var qrisService: QrisService

    private var disposable: Disposable? = null


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
    }

    private fun renderView() {
        setupDatePicker()
        initRecyclerView()
        _view.ll_menu_qris.setOnClickListener {
            startActivity(Intent(activity, QrisSCMActivity::class.java))
        }
        _view.ll_menu_report.setOnClickListener {
            startActivity(Intent(activity, ReportTransactionCashierActivity::class.java))
        }
        _view.btn_process.setOnClickListener {
            if (_view.et_payment_ammount.text.isNotEmpty()) {
                QrisDinamisDialog.newInstance(_view.et_payment_ammount.text.toString())
                    .show(requireActivity().supportFragmentManager, QrisDinamisDialog.TAG)
            }
        }
        loadData(dateNow, dateNow)
    }

    private fun initRecyclerView() {
        val listTransaction = mutableListOf<QrTransaction>()
        val adapter = HomeTransactionAdapter(listTransaction, this)
        _view.rv_transactions.adapter = adapter
        _view.rv_transactions.layoutManager =
            LinearLayoutManager(_view.context, RecyclerView.VERTICAL, false)
    }

    fun setBottomCounter(list:List<QrTransaction>){
        var sumOmzet = 0.0
        list.forEach { qr ->
            sumOmzet+= qr.nominal.toFloat()
        }
        _view.tv_value_total_transaction.text = "${list.size} transaksi"
        _view.tv_value_total_ammount.text = "Rp ${sumOmzet.toInt()}"
    }

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
            loadData(Date(it.first!!), Date(it.second!!))
        }
        _view.tv_date_range.setOnClickListener {
            picker.show(activity?.supportFragmentManager!!, picker.toString())
        }
    }

    private fun loadData(dateStart: Date, dateEnd: Date) {
        val adapter = _view.rv_transactions.adapter as HomeTransactionAdapter

        disposable?.dispose()
        disposable = qrisService
            .getTransaksiRange(
                "1247628",
                dateFormat.format(dateStart),
                dateFormat.format(dateEnd)
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
//                swipe.isRefreshing = false
                if (result.rc == "00" && result.data.isNotEmpty()) {
                    result.data
                        .sortedByDescending { it.time_request }
                        .forEach {
                            val timeStr = it.time_request.replace("T", " ")
                            val timeFormatted = respDateFormat.parse(timeStr)
                            Timber.d("Time: %s", timeStr)
                            Timber.d("Time Long: %d", timeFormatted.time)
                            Timber.d("Time Str: %s", itemDateFormat.format(timeFormatted))
                            it.time = timeFormatted.time
                            it.time_request = itemDateFormat.format(timeFormatted)
                            adapter.list.add(it)
                        }
                    setBottomCounter(adapter.list)
                    adapter.notifyDataSetChanged()
                }
            }, { error ->
                Log.d("Error: %s", error.toString())
            })
    }

    override fun onItemClicked(trx: QrTransaction) {
        startActivity(Intent(requireContext(), ReportTransactionDetailActivity::class.java).apply {
            putExtra("QrTransaction", trx)
        })
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
