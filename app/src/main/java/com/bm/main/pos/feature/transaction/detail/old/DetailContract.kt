package com.bm.main.pos.feature.transaction.detail.old

import android.content.Context
import android.content.Intent
import androidx.core.widget.NestedScrollView
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.models.transaction.TransactionRestModel

interface DetailContract {

    interface View : BaseViewImpl {
        fun reloadData()
        fun onClose(status: Int)
        fun showMessage(code: Int, msg: String?)
        fun setInfo(id: String,
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
        )

        fun setProducts(list: List<DetailTransaction.Data>)
        fun enableBtn(type: String?)
        fun openPrinterPage()
        fun openPaymentDialog()
        fun getParentLayout(): NestedScrollView
        fun takeScreenshot(filename: String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun loadDetail()
        fun deleteDetail()
        fun onCheckBluetooth()
        fun getDataStruk(): DetailTransaction
        fun getTypeTRX(): Int
        fun onPay(value: String)
        fun onCheckShare()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetDetailAPI(context: Context, restModel: TransactionRestModel, id: String)
        fun callGetDetailSupplierAPI(context: Context, restModel: TransactionRestModel, id: String)
        fun callDeleteDetailAPI(context: Context, restModel: TransactionRestModel, id: String)
        fun callSupplierDeleteDetailAPI(
            context: Context,
            restModel: TransactionRestModel,
            id: String
        )

        fun callPayPiutangAPI(
            context: Context,
            restModel: TransactionRestModel,
            id: String,
            total: String
        )

        fun callPayHutangAPI(
            context: Context,
            restModel: TransactionRestModel,
            id: String,
            total: String
        )
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetDetail(detail: DetailTransaction?)
        fun onSuccessDeleteDetail(message: String?)
        fun onSuccessPay(message: String?)
        fun onFailedAPI(code: Int, msg: String)
    }


}