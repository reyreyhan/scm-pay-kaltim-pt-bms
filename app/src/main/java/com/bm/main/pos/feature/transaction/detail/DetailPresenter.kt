package com.bm.main.pos.feature.transaction.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.events.onReloadTransaction
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.models.transaction.TransactionRestModel
import com.bm.main.pos.utils.*
import com.google.firebase.analytics.FirebaseAnalytics
import org.greenrobot.eventbus.EventBus

class DetailPresenter(val context: Context, val view: DetailContract.View) :
    BasePresenter<DetailContract.View>(), DetailContract.Presenter,
    DetailContract.InteractorOutput {

    private var interactor = DetailInteractor(this)
    private var restModel = TransactionRestModel(context)
    private val permissionUtil = PermissionUtil(context)
    private var data: DetailTransaction? = null
    private var id: String? = null
    private lateinit var bluetoothPermission: PermissionCallback
    private lateinit var storagePermission: PermissionCallback
    private var typeTRX = AppConstant.Code.CODE_TRANSACTION_CUSTOMER
    private var detail: DetailTransaction? = null

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

        storagePermission = object : PermissionCallback {
            override fun onSuccess() {
                view.takeScreenshot("Struk_${ detail?.struk?.no_invoice ?: System.currentTimeMillis() }.jpg")

//                ImageHelper.takeScreenshot(
//                    context,
//                    view.getParentLayout(),
//                    "struk_${data?.struk?.no_invoice ?: System.currentTimeMillis()}.jpg"
//                ) {
//                    Helper.shareBitmapToApps(context, Uri.parse(it))
//                }
            }

            override fun onFailed() {
                onFailedAPI(999, context.getString(R.string.reason_permission_write_external))
            }
        }

        id = intent.getStringExtra(AppConstant.DATA)
        if (id.isNullOrBlank() || id.isNullOrEmpty()) {
            view.onClose(Activity.RESULT_CANCELED)
            return
        }
        typeTRX = intent.getIntExtra(AppConstant.CODE, AppConstant.Code.CODE_TRANSACTION_CUSTOMER)
        loadDetail()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun loadDetail() {
        if (AppConstant.Code.CODE_TRANSACTION_CUSTOMER == typeTRX) {
            interactor.callGetDetailAPI(context, restModel, id!!)
        } else {
            interactor.callGetDetailSupplierAPI(context, restModel, id!!)
        }

    }

    override fun onSuccessGetDetail(detail: DetailTransaction?) {
        this.detail = detail

        if (detail == null) {
            onFailedAPI(999, "Tidak ada data")
            return
        }
        this.data = detail
        val struk = detail.struk
        val data = detail.data
        val pelanggan = struk?.nama_pelanggan
        val supplier = struk?.nama_supplier
        val operator = struk?.operator
        var bayar = struk?.totalbayar
        var kembalian = struk?.kembalian
        when {
            "batal" == struk?.status -> {
                bayar = null
                kembalian = null
            }
            "hutang" == struk?.status -> {
                if (bayar.isNullOrEmpty() || bayar.isNullOrBlank() || bayar == "0") {
                    bayar = null
                } else {
                    bayar = "Rp ${Helper.convertToCurrency(bayar)}"
                }
                kembalian = null
            }
            else -> {
                if (typeTRX == AppConstant.Code.CODE_TRANSACTION_SUPPLIER) {
                    bayar = null
                    kembalian = null
                } else {
                    bayar = "Rp ${Helper.convertToCurrency(struk?.totalbayar!!)}"
                    if (kembalian.isNullOrEmpty() || kembalian.isNullOrBlank() || kembalian == "0") {
                        kembalian = null
                    } else {
                        kembalian = "Rp ${Helper.convertToCurrency(kembalian)}"
                    }
                }

            }
        }

        view.setInfo(
            struk?.no_invoice!!,
            "Rp ${Helper.convertToCurrency(struk.totalorder!!)}",
            "Rp ${Helper.convertToCurrency(struk.totalorder!!)}",
            Helper.getDateFormat(context, struk.tanggal!!, "yyyy-MM-dd", "dd MMMM yyyy"),
            operator,
            pelanggan,
            supplier,
            struk.pembayaran!!,
            struk.status!!,
            bayar,
            kembalian,
            struk.nama_toko.orEmpty(),
            struk.nohp.orEmpty(),
            struk.alamat.orEmpty()
        )

        view.enableBtn(struk.status)

        view.setProducts(data!!)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code, msg)
    }

    override fun onSuccessDeleteDetail(message: String?) {
        view.showMessage(999, message)
        EventBus.getDefault().post(onReloadTransaction(true))
        view.onClose(Activity.RESULT_OK)
    }

    override fun deleteDetail() {
        if (AppConstant.Code.CODE_TRANSACTION_CUSTOMER == typeTRX) {
            interactor.callDeleteDetailAPI(context, restModel, id!!)
        } else {
            interactor.callSupplierDeleteDetailAPI(context, restModel, id!!)
        }

    }

    override fun onCheckBluetooth() {
        permissionUtil.checkBluetoothPermission(bluetoothPermission)
    }

    override fun getDataStruk(): DetailTransaction {
        return data!!
    }

    override fun getTypeTRX(): Int {
        return typeTRX
    }

    override fun onPay(value: String) {
        val struk = data?.struk
        if (AppConstant.Code.CODE_TRANSACTION_CUSTOMER == typeTRX) {
            interactor.callPayPiutangAPI(context, restModel, struk?.no_invoice!!, value)
        } else {
            interactor.callPayHutangAPI(context, restModel, struk?.no_invoice!!, value)
        }
    }

    override fun onSuccessPay(message: String?) {
        view.showMessage(999, message)
        EventBus.getDefault().post(onReloadTransaction(true))
        view.reloadData()
    }

    override fun onCheckShare() {
        (context as BaseActivity).logEventFireBase(
            "SHARED",
            "Struk " + id,
            EventParam.EVENT_ACTION_SEND,
            FirebaseAnalytics.Event.SHARE,
            EventParam.EVENT_SUCCESS,
            DetailActivity::class.java.getSimpleName()
        )
        permissionUtil.checkWriteExternalPermission(storagePermission)
    }
}