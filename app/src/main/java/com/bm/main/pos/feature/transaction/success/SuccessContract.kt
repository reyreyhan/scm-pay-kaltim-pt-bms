package com.bm.main.pos.feature.transaction.success

import android.content.Context
import android.content.Intent
import androidx.core.widget.NestedScrollView
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.models.transaction.TransactionRestModel

interface SuccessContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onSuccessCash(total: String, pay: String, kembalian: String?, date: String, id: String)
        fun onSuccessCash(detail: DetailTransaction, id: String)
        fun onSuccessPiutang(total: String, date: String, id: String)
        fun onSuccessPiutang(detail: DetailTransaction, id: String)
        fun onSuccess(total: String, date: String, id: String)
        fun onClose()
        fun onErrorView(msg: String)
        fun onSuccessView()
        fun openDetailPage()
        fun openPrinterPage()
        fun getParentLayout(): NestedScrollView
        fun setProducts(list: List<DetailTransaction.Data>)
        fun takeScreenshot(filename: String, isShare:Boolean)
        fun shareToWhatsapp(contact:String, filename: String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun loadDetail()
        fun onCheckBluetooth()
        fun onCheckShare()
        fun getDataStruk(): DetailTransaction
        fun getTabPosition(): Int
        fun getStrukImageFileName():String
        fun onCheckDownload()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetDetailAPI(context: Context, restModel: TransactionRestModel, id: String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetDetail(detail: DetailTransaction?)
        fun onFailedAPI(code: Int, msg: String)
    }
}