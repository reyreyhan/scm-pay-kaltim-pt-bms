package com.bm.main.scm.feature.reportscm.detail

import android.content.Context
import android.content.Intent
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.callback.PermissionCallback
import com.bm.main.scm.rabbit.QrTransaction
import com.bm.main.scm.utils.BluetoothUtil
import com.bm.main.scm.utils.PermissionUtil
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ReportTransactionDetailPresenter(
    val context: Context,
    val view: ReportTransactionDetailContract.View
) : BasePresenter<ReportTransactionDetailContract.View>(),
    ReportTransactionDetailContract.Presenter, ReportTransactionDetailContract.InteractorOutput {

    private var interactor: ReportTransactionDetailInteractor =
        ReportTransactionDetailInteractor(this)
    private var qrTransaction: QrTransaction? = null
    private val respDateFormat by lazy {
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    }
    private val respTimeFormat by lazy {
        SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    }

    private val permissionUtil = PermissionUtil(context)
    private lateinit var bluetoothPermission: PermissionCallback
    private lateinit var shareStruk: PermissionCallback
    private lateinit var downloadStruk: PermissionCallback

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onViewCreated(intent: Intent) {
        val item = intent.getParcelableExtra<QrTransaction>("QrTransaction")
        qrTransaction = item
        Timber.d("Date Long: %d", item.time)

        bluetoothPermission = object : PermissionCallback {
            override fun onSuccess() {

                if (BluetoothUtil.isBluetoothOn()) {
                    view.openPrinterPage()
                } else {
                    BluetoothUtil.openBluetooth(context)
                }
            }

            override fun onFailed() {
                view.showToast(/*999, */context.getString(R.string.reason_permission_bluetooth))
            }
        }

        shareStruk = object : PermissionCallback {
            override fun onSuccess() {
                view.takeScreenshot(
                    "Struk_${qrTransaction!!.buyer_reff}.jpg",
                    true
                )
            }

            override fun onFailed() {
                view.showToast(/*999,*/ context.getString(R.string.reason_permission_write_external))
            }
        }

        downloadStruk = object : PermissionCallback {
            override fun onSuccess() {
                view.takeScreenshot(
                    "Struk_${qrTransaction!!.buyer_reff}.jpg",
                    false
                )
            }

            override fun onFailed() {
                view.showToast(/*999,*/ context.getString(R.string.reason_permission_write_external))
            }
        }
    }

    override fun onCheckBluetooth() {
        permissionUtil.checkBluetoothPermission(bluetoothPermission)
    }

    override fun onCheckShare() {
        permissionUtil.checkWriteExternalPermission(shareStruk)
    }

    override fun onCheckDownload() {
        permissionUtil.checkWriteExternalPermission(downloadStruk)
    }

    override fun setAdapterList(adapter: ReportTransactionDetailAdapter) {
        val list = mutableListOf<String>()
        list.add(respDateFormat.format(Date(qrTransaction!!.time!!)))
        list.add(respTimeFormat.format(Date(qrTransaction!!.time!!)))
        list.add(qrTransaction!!.merchant_repo)
        list.add(qrTransaction!!.nominal)
        list.add("BERHASIL")
        list.add(qrTransaction!!.nmid)
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

}
