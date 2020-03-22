package com.bm.main.pos.feature.transaction.detail

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.callback.BluetoothCallback
import com.bm.main.pos.feature.dialog.PaymentDialog
import com.bm.main.pos.feature.printer.PrinterActivity
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.*
import com.bm.main.pos.utils.print.PrinterUtil
import kotlinx.android.synthetic.main.activity_transaction_detail_new.*
import timber.log.Timber

class DetailSuccessActivity :
    com.bm.main.pos.base.BaseActivity<DetailPresenter, DetailContract.View>(), DetailContract.View,
    PaymentDialog.Listener {

    val adapter = DetailAdapter()

    override fun createPresenter(): DetailPresenter {
        return DetailPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_transaction_detail_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.statusBarColor = ResourcesCompat.getColor(
//                resources,
//                R.color.bg_header_success,
//                null
//            )
//        }

        sw_refresh.isRefreshing = false
        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

//        btn_cancel.setOnClickListener {
//            showLoadingDialog()
//            getPresenter()?.deleteDetail()
//        }
//
//        btn_printer.setOnClickListener {
//            getPresenter()?.onCheckBluetooth()
//        }
//
//        btn_share_social.setOnClickListener {
//            getPresenter()?.onCheckShare()
//            openDetailPage()
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    private fun setupToolbar() {
//        setSupportActionBar(toolbarx)
//        supportActionBar?.apply {
//            setDisplayHomeAsUpEnabled(true)
//            setDisplayShowHomeEnabled(true)
//            title = ""
//            toolbar_title.text = getString(R.string.menu_detail_transaction)
//
//            setHomeAsUpIndicator(ResourcesCompat.getDrawable(
//                resources,
//                R.drawable.ic_back_pos,
//                null
//            )?.apply {
//                colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
//            })
//        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Detail Laporan"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun takeScreenshot(filename: String) {
//        actions.visibility = View.GONE
//        ImageHelper.takeScreenshot(this, ns_content, filename) {
//            actions.visibility = View.VISIBLE
//            Helper.shareBitmapToApps(this, Uri.parse(it))
//        }
    }

    override fun setProducts(list: List<DetailTransaction.Data>) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        adapter.setItems(list)
//        tv_total_qty.text = list.sumBy { it.jumlah?.toInt() ?: 0 }.toString()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun showMessage(code: Int, msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            toast(this, msg!!)
        }
    }

    override fun reloadData() {
        hideLoadingDialog()
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadDetail()
    }

    override fun onClose(status: Int) {
        setResult(status, intent)
        finish()
    }

    override fun setInfo(
        id: String,
        subtotal: String,
        total: String,
        date: String,
        operator: String?,
        customer: String?,
        supplier: String?,
        payment: String,
        status: String,
        bayar: String?,
        kembalian: String?,
        nama_toko: String,
        nohp: String,
        alamat: String
    ) {
        tv_metode_pembayaran.text = payment
        tv_id_transaksi.text = id
        tv_tanggal.text = date
        tv_total_item.text = total
        tv_tunai.text = bayar
        tv_kembalian.text = kembalian
        //tv_info_toko.htmlText("${nama_toko} / ${nohp}<br>${alamat}")

        //        tv_subtotal.text = subtotal
        //        tv_total_big.text = total

//        tv_operator.text = operator

//        tv_status.text = status

//        ll_operator.visibility = View.GONE
//        operator?.let {
//            ll_operator.visibility = View.GONE
//            tv_operator.text = it
//        }

//        ll_customer.visibility = View.GONE
//        customer?.let {
//            ll_customer.visibility = View.VISIBLE
//            tv_customer.text = it
//        }

//        ll_bayar.visibility = View.GONE
//        bayar?.let {
//            ll_bayar.visibility = View.VISIBLE
//            tv_bayar.text = it
//        }

//        ll_kembalian.visibility = View.GONE
//        kembalian?.let {
//            ll_kembalian.visibility = View.VISIBLE
//            tv_kembalian.text = it
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 666 && BluetoothUtil.isBluetoothOn()) {
            openPrinterPage()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun enableBtn(type: String?) {
        when (type) {
            "batal" -> {
//                btn_cancel.visibility = View.GONE
//                btn_printer.visibility = View.GONE
//                v_space.visibility = View.GONE
            }
            "hutang" -> {
//                btn_cancel.visibility = View.VISIBLE
//                btn_printer.visibility = View.GONE
//                v_space.visibility = View.VISIBLE
            }
            else -> {
//                btn_cancel.visibility = View.VISIBLE
//                btn_printer.visibility = View.VISIBLE
//                v_space.visibility = View.VISIBLE

            }
        }
    }

    private fun getLogo(): Drawable =
        Drawable.createFromStream(assets.open("logo_profit.bmp"), null)

    private val listDevice by lazy { BluetoothUtil.getPairedDevices() }

    override fun openPrinterPage() {
        showLoadingDialog()
        listDevice.apply { this.map { Timber.e("[${it.address}] : ${it.name} - ${it.uuids}") } }.firstOrNull()?.let {
            BluetoothConnectTask(object : BluetoothCallback {
                override fun onConnected(
                    socket: BluetoothSocket?,
                    taskType: Int,
                    device: BluetoothDevice?
                ) {
                    PrinterUtil.print(
                        socket,
                        getPresenter()?.getDataStruk(),
                        null,
                        getString(R.string.app_name),
                        getLogo(),
                        device?.name.orEmpty()
                    )
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

    override fun openPaymentDialog() {
        val dialog = PaymentDialog.newInstance()
        dialog.setData(getPresenter()?.getDataStruk()!!, getPresenter()?.getTypeTRX()!!)
        dialog.show(supportFragmentManager, PaymentDialog.TAG)
    }

    override fun onPay(selected: DetailTransaction, type: Int, value: String) {
        showLoadingDialog()
        getPresenter()?.onPay(value)
    }

    override fun getParentLayout(): NestedScrollView {
//        return ns_content
        return NestedScrollView(this)
    }
}