package com.bm.main.scm.feature.transaction.success

import android.content.Context
import android.content.Intent
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.callback.PermissionCallback
import com.bm.main.scm.models.Message
import com.bm.main.scm.models.transaction.DetailTransaction
import com.bm.main.scm.models.transaction.TransactionRestModel
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.BluetoothUtil
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.utils.PermissionUtil

class SuccessPresenter(val context: Context, val view: SuccessContract.View) : BasePresenter<SuccessContract.View>(),
    SuccessContract.Presenter,
    SuccessContract.InteractorOutput {

    private var interactor = SuccessInteractor(this)
    private var restModel = TransactionRestModel(context)
    private var invoice: String? = null
    private var detail: DetailTransaction? = null
    private val permissionUtil = PermissionUtil(context)
    private lateinit var bluetoothPermission: PermissionCallback
    private lateinit var shareStruk: PermissionCallback
    private lateinit var downloadStruk: PermissionCallback
    private var position: Int? = 0


    override fun onViewCreated(intent: Intent) {
        bluetoothPermission = object : PermissionCallback {
            override fun onSuccess() {

                if (BluetoothUtil.isBluetoothOn()) {
                    view.openPrinterPage()
                } else {
                    BluetoothUtil.openBluetooth(context)
                }
            }

            override fun onFailed() {
                onFailedAPI(999, context.getString(R.string.reason_permission_bluetooth))
            }
        }

        shareStruk = object : PermissionCallback {
            override fun onSuccess() {
                view.takeScreenshot("Struk_${ detail?.struk?.no_invoice ?: System.currentTimeMillis() }.jpg", true)
            }

            override fun onFailed() {
                onFailedAPI(999, context.getString(R.string.reason_permission_write_external))
            }
        }

        downloadStruk = object : PermissionCallback {
            override fun onSuccess() {
                view.takeScreenshot("Struk_${ detail?.struk?.no_invoice ?: System.currentTimeMillis() }.jpg", false)
            }

            override fun onFailed() {
                onFailedAPI(999, context.getString(R.string.reason_permission_write_external))
            }
        }

        invoice = intent.getStringExtra(AppConstant.DATA)
        if (invoice.isNullOrBlank() || invoice.isNullOrEmpty()) {
            view.showMessage(999, "Nomor invoice tidak ditemukan")
            return
        }
        loadDetail()
    }

    override fun loadDetail() {
        interactor.callGetDetailAPI(context, restModel, invoice!!)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code, msg)
    }

    override fun onSuccessSendStruk(msg: Message) {
        view.showSuccessMessage(msg.message)
    }

    override fun onSuccessGetDetail(detail: DetailTransaction?) {
        this.detail = detail

        if (detail == null) {
            view.showMessage(999, "Nomor invoice tidak ditemukan")
            return
        }
        when {
            "Hutang".equals(detail.struk?.status,
                true)
            -> {
                //  view.onSuccessPiutang("Rp ${Helper.convertToCurrency(detail.struk?.totalorder!!)}",Helper.getDateFormat(context,detail.struk?.tanggal!!,"yyyy-MM-dd","EEE, dd MMMM yyyy"),invoice!!)
                view.onSuccessPiutang(detail, invoice!!)
                position = 1
            }
            else -> {
                position = 0
                val kembalianString = detail.struk?.kembalian
                var kembalian = 0.0
                var kembalianValue: String? = null
                if (!kembalianString.isNullOrEmpty() && !kembalianString.isNullOrBlank()) {
                    kembalian = kembalianString.toDouble()
                    kembalianValue = "Rp ${Helper.convertToCurrency(kembalian)}"
                }
                //view.onSuccessCash("Rp ${Helper.convertToCurrency(detail.struk?.totalorder!!)}","Rp ${Helper.convertToCurrency(detail.struk?.totalbayar!!)}",kembalianValue,Helper.getDateFormat(context,detail.struk?.tanggal!!,"yyyy-MM-dd","EEE, dd MMMM yyyy"),invoice!!)
                view.onSuccessCash(detail, invoice!!)
            }

        }
        val data = detail.data
        view.setProducts(data!!)
    }

    override fun onCheckBluetooth() {
        permissionUtil.checkBluetoothPermission(bluetoothPermission)
    }

    override fun onCheckShare() {
        permissionUtil.checkWriteExternalPermission(shareStruk)
    }

    override fun onCheckDownload(){
        permissionUtil.checkWriteExternalPermission(downloadStruk)
    }

    override fun sendStruk(email: String?) {
        interactor.callSendStruk(context, restModel, invoice!!, email!!)
    }

    override fun getInvoice():String {
        return invoice!!
    }

    override fun getDataStruk(): DetailTransaction {
        return detail!!
    }

    override fun getTabPosition(): Int {
        return position!!
    }

    override fun getStrukImageFileName():String{
        return "Struk_${ detail?.struk?.no_invoice ?: System.currentTimeMillis() }.jpg"
    }
}