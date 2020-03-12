package com.bm.main.pos.feature.manage.hutangpiutang.detailHutang

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.hutangpiutang.DetailHutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper

class DetailHutangPresenter(val context: Context, val view: DetailHutangContract.View) : BasePresenter<DetailHutangContract.View>(),
    DetailHutangContract.Presenter, DetailHutangContract.InteractorOutput {

    private var interactor = DetailHutangInteractor(this)
    private val supplierRestModel = SupplierRestModel(context)
    private val hutangRestModel = HutangPiutangRestModel(context)
    private var supplier:Supplier ?= null

    override fun onViewCreated(intent: Intent) {
        supplier = intent.getSerializableExtra(AppConstant.DATA) as Supplier
        checkSupplier()
        loadDetailSupplier()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun getTitleName(): String {
        return if(supplier == null){
            ""
        } else{
            supplier?.nama_supplier!!
        }

    }

    private fun checkSupplier(){
        if(supplier == null){
            view.onClose(Activity.RESULT_CANCELED)
            return
        }
        view.setSupplier(supplier?.nama_supplier,supplier?.email,supplier?.telpon,supplier?.alamat,supplier?.gbr)
    }

    override fun loadDetailSupplier() {
        interactor.callGetDetailSupplier(context,supplierRestModel,supplier?.id_supplier!!)
    }

    override fun onSuccessGetDetailSupplier(data: Supplier) {
        if(data == null){
            onFailedAPI(999,"Data tidak ditemukan")
            return
        }
        supplier = data
        checkSupplier()
        loadHutang()
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code,msg)
    }

    override fun loadHutang() {
        interactor.callGetHutang(context,hutangRestModel,supplier?.id_supplier!!)
    }

    override fun onSuccessGetHutang(data: DetailHutang) {
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