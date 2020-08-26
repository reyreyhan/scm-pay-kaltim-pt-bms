package com.bm.main.scm.feature.transaction.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.transaction.DetailTransaction
import com.bm.main.scm.models.transaction.TransactionRestModel
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper

class DetailPresenterNew(val context: Context, val view: DetailContractNew.View) :
    BasePresenter<DetailContractNew.View>(), DetailContractNew.Presenter,
    DetailContractNew.InteractorOutput {

    private var interactor =
        DetailInteractorNew(this)
    private var restModel = TransactionRestModel(context)
    private var data: DetailTransaction? = null
    private var id: String? = null
    private var typeTRX = AppConstant.Code.CODE_TRANSACTION_CUSTOMER

    override fun onViewCreated(intent: Intent) {
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
        interactor.callGetDetailAPI(context, restModel, id!!)
    }

    override fun onSuccessGetDetail(detail: DetailTransaction?) {
        this.data = detail

        if (detail == null) {
            onFailedAPI(999, "Tidak ada data")
            return
        }

        val struk = detail.struk
        val data = detail.data
        val status = struk?.status
        val invoice = struk?.no_invoice
        val date = struk?.tanggal?.let {
            Helper.getDateFormat(context,
                it, "yyyy-MM-dd", "dd MMMM yyyy")
        }
        val paymentMethod = struk?.pembayaran
        var paymentAmount = struk?.totalbayar
        var orderAmount = struk?.totalorder
        var cashback = struk?.kembalian
        var debt:String? = null
        when (status) {
            "batal" -> {
                paymentAmount = null
                orderAmount = null
            }
            "hutang" -> {
                paymentAmount?.let{pay->
                    orderAmount?.let {
                        val order = it.toDouble()
                        debt = "Rp ${Helper.convertToCurrency(order.minus(pay.toDouble()))}"
                    }
                }
                paymentAmount = if (paymentAmount.isNullOrEmpty() || paymentAmount.isNullOrBlank() || paymentAmount == "0") {
                    null
                } else {
                    "Rp ${Helper.convertToCurrency(paymentAmount)}"
                }
                cashback = null
            }
            else -> {
                paymentAmount = "Rp ${Helper.convertToCurrency(struk?.totalbayar!!)}"
                cashback = if (cashback.isNullOrEmpty() || cashback.isNullOrBlank() || cashback == "0") {
                    null
                } else {
                    "Rp ${Helper.convertToCurrency(cashback)}"
                }
            }
        }
        view.setInfo(
            invoice!!,
            "Rp ${orderAmount?.let { Helper.convertToCurrency(it) }}",
            date,
            paymentMethod!!,
            paymentAmount,
            cashback,
            debt
        )
        view.setProducts(data!!)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code, msg)
    }

    override fun getDataStruk(): DetailTransaction {
        return data!!
    }

    override fun getTypeTRX(): Int {
        return typeTRX
    }
}