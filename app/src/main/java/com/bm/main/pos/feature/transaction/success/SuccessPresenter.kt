package com.bm.main.pos.feature.transaction.success

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.widget.NestedScrollView
import com.google.gson.Gson
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.models.cart.CartRestModel
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.models.transaction.TransactionRestModel
import com.bm.main.pos.utils.*

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