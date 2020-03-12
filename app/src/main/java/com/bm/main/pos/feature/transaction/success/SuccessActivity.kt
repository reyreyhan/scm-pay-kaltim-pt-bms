package com.bm.main.pos.feature.transaction.success

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.fpl.constants.EventParam
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.callback.BluetoothCallback
import com.bm.main.pos.feature.drawer.DrawerActivity
import com.bm.main.pos.feature.printer.PrinterActivity
import com.bm.main.pos.feature.transaction.detail.DetailActivity
import com.bm.main.pos.feature.transaction.detail.DetailSuccessActivity
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.LinearItemDecoration
import com.bm.main.pos.ui.ext.htmlText
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.*
import com.bm.main.pos.utils.print.PrinterUtil
import kotlinx.android.synthetic.main.activity_transaction_success.*
import java.lang.Integer.parseInt

class SuccessActivity : BaseActivity<SuccessPresenter, SuccessContract.View>(),
    SuccessContract.View {

    override fun setProducts(list: List<DetailTransaction.Data>) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        adapter.setItems(list)
    }

    val adapter = SuccessAdapter()

    override fun createPresenter(): SuccessPresenter {
        return SuccessPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_transaction_success
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ResourcesCompat.getColor(resources, R.color.bg_header_success, null)
        }

        setSupportActionBar(toolbarx)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            toolbar_title.text = "Pembayaran Sukses!"
            title = ""

            setHomeAsUpIndicator(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_back_pos, null)?.apply { colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN) }
            )
            toolbarx.setNavigationOnClickListener {
                startActivity(Intent(this@SuccessActivity, DrawerActivity::class.java))
                finishAffinity()
            }
        }

        sw_refresh.isRefreshing = false
        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
        rv_list.addItemDecoration(LinearItemDecoration(space = resources.getDimensionPixelSize(R.dimen._4sdp)))

        btn_print.setOnClickListener {
            getPresenter()?.onCheckBluetooth()
        }

        btn_share_social.setOnClickListener {
            getPresenter()?.onCheckShare()
//            openDetailPage()
        }

        btn_end.setOnClickListener {
            onClose()
        }
    }

    private val filterWhite by lazy { PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN) }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        menu?.findItem(R.id.action_share)?.apply { icon = icon?.apply { colorFilter = filterWhite } }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> getPresenter()?.onCheckShare()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun takeScreenshot(filename: String) {
        actions.visibility = View.GONE
        ImageHelper.takeScreenshot(this, ns_content, filename) {
            actions.visibility = View.VISIBLE
            Helper.shareBitmapToApps(this, Uri.parse(it))
        }
    }

    override fun showMessage(code: Int, msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            onErrorView(msg.toString())
        }
    }

    private fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadDetail()
    }

    override fun onClose() {
        restartMainActivity()
    }

    @SuppressLint("SetTextI18n")
    var totQty: Int = 0

    override fun onSuccessCash(detail: DetailTransaction, id: String) {
        logEventFireBase(
            "Penjualan",
            "Tunai " + id,
            EventParam.EVENT_ACTION_SELL_PRODUCT,

            EventParam.EVENT_SUCCESS,
            SuccessActivity::class.java.getSimpleName()
        )

        tv_info_toko.htmlText("${detail.struk?.nama_toko} / ${detail.struk?.nohp}<br>${detail.struk?.alamat}")

        val kembalianString = detail.struk?.kembalian
        var kembalian = 0.0
        var kembalianValue: String? = null
        if (!kembalianString.isNullOrEmpty() && !kembalianString.isNullOrBlank()) {
            kembalian = kembalianString.toDouble()
            kembalianValue = "Rp ${Helper.convertToCurrency(kembalian)}"
        }
        tv_id.text = id
//        if(!detail.struk?.status.equals("tunai")) {
//            ll_pay.visibility = View.GONE
//            ll_bayar.visibility=View.GONE
//            tv_pay.text = "tunai"
//        }else{
        ll_pay.visibility = View.GONE
        ll_bayar.visibility = View.VISIBLE
        tv_pay.text = detail.struk?.status
        // tv_pay.text = pay
//        }
//        for (i in 0 until detail.data.size){
//            totQty.plus(Int)= detail.data[i].jumlah
//        }

//        for (i in 0 until detail.data.sumBy { data: DetailTransaction.Data ->  }) {
//            totQty += detail.data[i].jumlah.toInt()
//        }
//
//        detail.data.forEach {
//            totQty += parseInt(values)
//        }
        totQty = detail.data?.sumBy { parseInt(it.jumlah!!) } ?: 0

        tv_total_qty.text = totQty.toString()
        tv_total.text = "Rp ${Helper.convertToCurrency(detail.struk?.totalorder!!)}"
        tv_bayar.text = "Rp ${Helper.convertToCurrency(detail.struk?.totalbayar!!)}"
        tv_date.text =
            Helper.getDateFormat(this, detail.struk?.tanggal!!, "yyyy-MM-dd", "EEE, dd MMMM yyyy")
        ll_kembalian.visibility = View.GONE
        kembalian?.let {
            tv_kembalian.text = kembalianValue
            ll_kembalian.visibility = View.VISIBLE
        }
        onSuccessView()
    }

    override fun onSuccessCash(
            total: String,
            pay: String,
            kembalian: String?,
            date: String,
            id: String
    ) {
        tv_id.text = id
        // tv_order.text = total
        tv_date.text = date
//        ll_info.visibility = View.VISIBLE
        if (!pay.equals("tunai")) {
            ll_pay.visibility = View.VISIBLE
            tv_pay.text = pay
        } else {
            ll_pay.visibility = View.GONE
            tv_pay.text = pay
        }
//        ll_piutang.visibility = View.GONE
        ll_kembalian.visibility = View.GONE
        kembalian?.let {
            tv_kembalian.text = kembalian
            ll_kembalian.visibility = View.VISIBLE
        }
        onSuccessView()
    }

    override fun onSuccessPiutang(total: String, date: String, id: String) {
        println("hutang")
        tv_id.text = id
//        tv_order.text = total
        tv_date.text = date
//        ll_info.visibility = View.VISIBLE
//        ll_piutang.visibility = View.VISIBLE
        ll_pay.visibility = View.GONE
        ll_kembalian.visibility = View.GONE
//        tv_piutang.text = total
        onSuccessView()
    }

    override fun onSuccessPiutang(detail: DetailTransaction, id: String) {
        logEventFireBase(
            "Penjualan",
            "Hutang " + id,
            EventParam.EVENT_ACTION_SELL_PRODUCT,

            EventParam.EVENT_SUCCESS,
            SuccessActivity::class.java.getSimpleName()
        )
        tv_id.text = id
        ll_pay.visibility = View.VISIBLE
        ll_bayar.visibility = View.GONE
        tv_pay.text = detail.struk?.status
        // tv_pay.text = pay
        tv_jatuh_tempo.text =
            Helper.getDateFormat(this, detail.struk?.jatuh_tempo!!, "yyyy-MM-dd", "dd-MM-yyyy")
        ll_jatuh_tempo.visibility = View.VISIBLE
        totQty = detail.data?.sumBy { parseInt(it.jumlah!!) } ?: 0

        tv_total_qty.text = totQty.toString()
        tv_total.text = "Rp ${Helper.convertToCurrency(detail.struk?.totalorder!!)}"
//        tv_bayar.text="Rp ${Helper.convertToCurrency(detail.struk?.totalbayar!!)}"
        tv_date.text =
            Helper.getDateFormat(this, detail.struk?.tanggal!!, "yyyy-MM-dd", "EEE, dd MMMM yyyy")
        ll_kembalian.visibility = View.GONE

        onSuccessView()
    }

    override fun onSuccess(total: String, date: String, id: String) {
        tv_id.text = id
//        tv_order.text = total
        tv_date.text = date
//        ll_info.visibility = View.GONE
        onSuccessView()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onBackPressed() {
        onClose()
    }

    override fun onErrorView(msg: String) {
        sw_refresh.isRefreshing = false
        fl_content.visibility = View.GONE
        ll_error.visibility = View.VISIBLE
        tv_error.text = msg
        toolbar_title.text = "Pembayaran Gagal!"
        toolbarx.setBackgroundColor(Color.WHITE)
    }

    override fun onSuccessView() {
        sw_refresh.isRefreshing = false
        fl_content.visibility = View.VISIBLE
        ll_error.visibility = View.GONE
    }

    private fun getLogo(): Drawable = Drawable.createFromStream(assets.open("logo_profit.bmp"), null)

    private val listDevice by lazy { BluetoothUtil.getPairedDevices() }
    override fun openPrinterPage() {
        showLoadingDialog()
        listDevice.firstOrNull()?.let {
            BluetoothConnectTask(object : BluetoothCallback {
                override fun onConnected(socket: BluetoothSocket?,
                                         taskType: Int,
                                         device: BluetoothDevice?) {
                    PrinterUtil.print(socket,
                            getPresenter()?.getDataStruk(),
                            null,
                            getString(R.string.app_name),
                            getLogo(),
                            device?.name.orEmpty())
                    hideLoadingDialog()
                    showToast("Sedang mencetak struk")
                    listDevice.union(BluetoothUtil.getPairedDevices())
                }

                override fun onError(msg: String?) {
                    listDevice.remove(it)
                    openPrinterPage()
                }

                override fun onPowerOn(intent: Intent?) {}
                override fun onPowerOff(intent: Intent?) {}
            }, 1, "").execute(it)
        } ?: run {
            dialogSelectPrinter()
        }
    }

    fun dialogSelectPrinter() {
        hideLoadingDialog()
        DialogUtils.showDialog(this,
                getString(R.string.btn_print),
                "Gagal menghubungkan printer, buka halaman pilih printer?",
                false,
                buttonOkLabel = "Ya",
                buttonOkAction = {
                    startActivity(Intent(this,
                            PrinterActivity::class.java).putExtra(AppConstant.DATA,
                            getPresenter()?.getDataStruk()))
                })
    }

    override fun openDetailPage() {
        val intent = Intent(this, DetailSuccessActivity::class.java)
        intent.putExtra(AppConstant.DATA, getPresenter()?.getDataStruk()?.struk?.no_invoice)
        startActivity(intent)
    }

    override fun getParentLayout(): NestedScrollView {
        return ns_content
    }
}
