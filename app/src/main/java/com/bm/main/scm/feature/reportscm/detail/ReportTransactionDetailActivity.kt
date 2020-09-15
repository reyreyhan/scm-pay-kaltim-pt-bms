package com.bm.main.scm.feature.reportscm.detail

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.callback.BluetoothCallback
import com.bm.main.scm.feature.dialog.SuccessDialog
import com.bm.main.scm.feature.printer.PrinterActivity
import com.bm.main.scm.utils.*
import com.bm.main.scm.utils.print.PrinterUtil
import kotlinx.android.synthetic.main.activity_detail_transaction_scm.*

class ReportTransactionDetailActivity :
    BaseActivity<ReportTransactionDetailPresenter, ReportTransactionDetailContract.View>(),
    ReportTransactionDetailContract.View, SuccessDialog.SuccessDialogListener {

    override fun createPresenter(): ReportTransactionDetailPresenter {
        return ReportTransactionDetailPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_detail_transaction_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        getPresenter()?.onViewCreated(intent)
        renderView()
    }

    private fun renderView() {
        initRecyclerView()
        tv_lbl_download.setOnClickListener {
            getPresenter()?.onCheckDownload()
        }
        tv_lbl_share.setOnClickListener {
            getPresenter()?.onCheckShare()
        }
        btn_print_receipt.setOnClickListener {
            getPresenter()?.onCheckBluetooth()
        }
    }

    private fun initRecyclerView() {
        rv_payment_detail.adapter = ReportTransactionDetailAdapter(mutableListOf<String>())
        rv_payment_detail.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        getPresenter()?.setAdapterList(rv_payment_detail.adapter as ReportTransactionDetailAdapter)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home) finish()
        return super.onOptionsItemSelected(item!!)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Detail Transaksi"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.white)))
            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                backArrow.setTint(resources.getColor(android.R.color.black))
            }
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onPositiveButtonDialog() {

    }

    override fun takeScreenshot(filename: String, isShare:Boolean) {
        if (isShare){
            ImageHelper.takeScreenshotLinearLayout(this, ll_content, filename) {
                //container_action.visibility = View.VISIBLE
                Helper.shareBitmapToApps(this, Uri.parse(it))
                //fileStruk = it
            }
        }else{
            ImageHelper.takeScreenshotLinearLayout(this, ll_content, filename)
            showToast("Berhasil mengunduh Struk")
        }
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
                       /* getPresenter()?.getDataStruk()*/
                        null,
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
                startActivity(
                    Intent(this,
                    PrinterActivity::class.java)/*.putExtra(
                        AppConstant.DATA,
                    getPresenter()?.getDataStruk())*/)
            })
    }
}