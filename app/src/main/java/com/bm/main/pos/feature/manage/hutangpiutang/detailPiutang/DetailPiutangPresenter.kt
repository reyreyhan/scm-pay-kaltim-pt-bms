package com.bm.main.pos.feature.manage.hutangpiutang.detailPiutang

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.hutangpiutang.DetailPiutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper

class DetailPiutangPresenter(val context: Context, val view: DetailPiutangContract.View) : BasePresenter<DetailPiutangContract.View>(),
    DetailPiutangContract.Presenter, DetailPiutangContract.InteractorOutput {

    private var interactor = DetailPiutangInteractor(this)
    private val customerRestModel = CustomerRestModel(context)
    private val hutangRestModel = HutangPiutangRestModel(context)
    private var customer:Customer ?= null

    override fun onViewCreated(intent: Intent) {
        customer = intent.getSerializableExtra(AppConstant.DATA) as Customer
        checkCustomer()
        loadDetailCustomer()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun getTitleName(): String {
        return if(customer == null){
            ""
        } else{
            customer?.nama_pelanggan!!
        }

    }

    private fun checkCustomer(){
        if(customer == null){
            view.onClose(Activity.RESULT_CANCELED)
            return
        }

        view.setCustomer(customer?.nama_pelanggan,customer?.email,customer?.telpon,customer?.alamat,customer?.gbr)
    }

    override fun loadDetailCustomer() {
        interactor.callGetDetailCustomer(context,customerRestModel,customer?.id_pelanggan!!)
    }

    override fun onSuccessGetDetailCustomer(data: Customer) {
        if(data == null){
            onFailedAPI(999,"Data tidak ditemukan")
            return
        }
        customer = data
        checkCustomer()
        loadHutang()
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code,msg)
    }

    override fun loadHutang() {
        interactor.callGetHutang(context,hutangRestModel,customer?.id_pelanggan!!)
    }

    override fun onSuccessGetHutang(data: DetailPiutang) {
        if(data == null){
            onFailedAPI(999,"Data tidak ditemukan")
            return
        }
        val piutang = data.datapiutang
        val list = data.sudah_bayar
        view.setPiutang("Rp ${Helper.convertToCurrency(piutang?.total_tagihan!!)}","Rp ${Helper.convertToCurrency(piutang?.jumlah_piutang!!)}",
            "Rp ${Helper.convertToCurrency(piutang?.total_dibayar!!)}","${Helper.getDateFormat(context,piutang?.jatuh_tempo!!,
                "yyyy-MM-dd","dd MMMM yyyy")}")
        view.setList(list!!)
    }
}