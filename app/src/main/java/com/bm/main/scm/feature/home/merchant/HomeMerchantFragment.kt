package com.bm.main.scm.feature.home.merchant

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseFragment
import com.bm.main.scm.feature.qrisscm.QrisSCMActivity
import com.bm.main.scm.feature.reportscm.detail.ReportTransactionDetailActivity
import com.bm.main.scm.feature.reportscm.transaction.merchant.ReportTransactionActivity
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
import kotlinx.android.synthetic.main.activity_report_merchant_scm.*
import kotlinx.android.synthetic.main.fragment_home_merchant_scm.view.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeMerchantFragment : BaseFragment<HomeMerchantPresenter, HomeMerchantContract.View>(),
    HomeMerchantContract.View, HomeTransactionAdapter.OnItemClickListener {

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

    companion object {

        @JvmStatic
        fun newInstance() = HomeMerchantFragment()
    }

    override fun createPresenter(): HomeMerchantPresenter {
        return HomeMerchantPresenter(activity as Context, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context!!.theme.applyStyle(R.style.SBFTheme, true)
        return inflater.inflate(R.layout.fragment_home_merchant_scm, container, false)
    }

    override fun initAction(view: View) {
        _view = view
        renderView()
        loadData(dateNow, dateNow)
    }

    private fun renderView() {
        setupDatePicker()
        initRecyclerView()
        _view.ll_menu_report.setOnClickListener {
            startActivity(Intent(activity, ReportTransactionActivity::class.java))
        }
        _view.ll_menu_qris.setOnClickListener {
            startActivity(
                Intent(activity, QrisSCMActivity::class.java).putExtra(
                    "IsMerchant",
                    true
                )
            )
        }
    }

    private fun initRecyclerView() {
        val listTransaction = mutableListOf<QrTransaction>()
        val adapter = HomeTransactionAdapter(listTransaction, this)
        _view.rv_transactions.adapter = adapter
        _view.rv_transactions.layoutManager =
            LinearLayoutManager(_view.context, RecyclerView.VERTICAL, false)
    }

    fun enableQrMenu(enable: Boolean) {
        /*if (::_view.isInitialized)
            _view.btn_grocery.isEnabled = enable*/
    }

    fun setLengkapiButtonVisibility(visible: Int) {
        /*if (::_view.isInitialized)
            _view.btn_lengkapi.visibility = visible*/
    }

    fun setBottomCounter(list:List<QrTransaction>){
        var sumOmzet = 0.0
        list.forEach { qr ->
            sumOmzet+= qr.nominal.toFloat()
        }
        tv_value_total_transaction.text = "${list.size} transaksi"
        tv_value_total_ammount.text = "Rp ${sumOmzet.toInt()}"
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

    }


    override fun onDetach() {
        super.onDetach()
        disposable?.dispose()
    }

    override fun onResume() {
        super.onResume()
    }


    override fun reloadData() {
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

    override fun showErrorMessage(code: Int, msg: String) {
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            toast(msg)
        }
    }

    override fun openAccountPage() {
    }

    override fun openPPOBPage() {
    }

    override fun openKulakanPage() {
    }

    override fun onItemClicked(trx: QrTransaction) {
        startActivity(Intent(requireContext(), ReportTransactionDetailActivity::class.java).apply {
            putExtra("QrTransaction", trx)
        })
    }
}
