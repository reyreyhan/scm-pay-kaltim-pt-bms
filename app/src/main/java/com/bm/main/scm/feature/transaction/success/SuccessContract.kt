package com.bm.main.scm.feature.transaction.success

import android.content.Context
import android.content.Intent
import androidx.core.widget.NestedScrollView
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.Message
import com.bm.main.scm.models.transaction.DetailTransaction
import com.bm.main.scm.models.transaction.TransactionRestModel

interface SuccessContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun showSuccessMessage(msg: String?)
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
        fun sendStruk(email:String?)
        fun getInvoice():String
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetDetailAPI(context: Context, restModel: TransactionRestModel, id: String)
        fun callSendStruk(context: Context, restModel: TransactionRestModel, invoice:String, email:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessSendStruk(msg: Message)
        fun onSuccessGetDetail(detail: DetailTransaction?)
        fun onFailedAPI(code: Int, msg: String)
    }
}