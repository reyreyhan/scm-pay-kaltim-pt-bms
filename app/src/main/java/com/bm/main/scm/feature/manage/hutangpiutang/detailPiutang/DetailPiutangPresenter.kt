package com.bm.main.scm.feature.manage.hutangpiutang.detailPiutang

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.Message
import com.bm.main.scm.models.customer.CustomerNew
import com.bm.main.scm.models.hutangpiutang.DetailPiutangNew
import com.bm.main.scm.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.scm.models.transaction.TransactionRestModel
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper

class DetailPiutangPresenter(val context: Context, val view: DetailPiutangContract.View) : BasePresenter<DetailPiutangContract.View>(),
    DetailPiutangContract.Presenter, DetailPiutangContract.InteractorOutput {

    private var interactor = DetailPiutangInteractor(this)
    private val transactionRestModel = TransactionRestModel(context)
    private val hutangRestModel = HutangPiutangRestModel(context)
    private var detailPiutangNew:CustomerNew ?= null

    override fun onViewCreated(intent: Intent) {
        detailPiutangNew = intent.getSerializableExtra(AppConstant.DATA) as CustomerNew
        checkCustomer()
        loadHutang()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun getTitleName(): String {
        return if(detailPiutangNew == null){
            ""
        } else{
            detailPiutangNew?.nama_pelanggan!!
        }
    }

    private fun checkCustomer(){
        if(detailPiutangNew == null){
            view.onClose(Activity.RESULT_CANCELED)
            return
        }

        view.setCustomer(detailPiutangNew?.nama_pelanggan)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code,msg)
    }

    override fun loadHutang() {
        interactor.callGetHutang(context,hutangRestModel,detailPiutangNew?.id_pelanggan!!)
    }

    override fun payHutang(pay:String) {
        interactor.callPayHutang(context,transactionRestModel,detailPiutangNew?.id_pelanggan!!,pay)
    }

    override fun onSuccessGetHutang(data: DetailPiutangNew) {
        if(data == null){
            onFailedAPI(999,"Data tidak ditemukan")
            return
        }
        val piutang = data.datapiutang
        val list = data.history
        view.setPiutang("Rp ${Helper.convertToCurrency(if (piutang!!.jumlah_piutang != null) piutang.jumlah_piutang!! else "0" )}",
            Helper.getDateFormat(context, piutang.tanggal_hutang!!,
                "yyyy-MM-dd", "dd MMMM yyyy"))
        view.setList(list!!)
    }

    override fun onSuccessPayHutang(msg: Message) {
        view.showSuccess(msg.message!!)
    }
}