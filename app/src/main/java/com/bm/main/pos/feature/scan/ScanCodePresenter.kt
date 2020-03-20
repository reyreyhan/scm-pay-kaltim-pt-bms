package com.bm.main.pos.feature.scan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.utils.AppConstant

class ScanCodePresenter(val context: Context, val view: ScanCodeContract.View) : BasePresenter<ScanCodeContract.View>(),
    ScanCodeContract.Presenter, ScanCodeContract.InteractorOutput {

    private var restModel = ProductRestModel(context)
    private val interactor = ScanCodeInteractor(this)

    override fun onViewFragmentCreated(bundle: Bundle){
        val type = bundle.getInt(AppConstant.SCAN.TYPE,-1)
    }

    override fun onViewCreated(intent: Intent) {
        val type = intent.getIntExtra(AppConstant.SCAN.TYPE,-1)
        if(type == AppConstant.SCAN.SELL){
            view.hideShowSearchHeader(View.VISIBLE)
        }
        else{
            view.hideShowSearchHeader(View.GONE)
        }
    }

    override fun onDestroy() {
        interactor.destroy()
    }

    override fun searchByBarcode(search: String) {
        interactor.callSearchByBarcodeAPI(context, restModel, search)
    }

    override fun onSuccessByBarcode(list: List<Product>) {
        view.hideLoadingDialog()
        if (list.isNotEmpty()) {
            val data =
                list.firstOrNull { it.nama_barang.isNotEmpty() && it.gbr.isNotEmpty() && it.hargajual.isNotEmpty() && it.hargabeli.isNotEmpty() }
                    ?: list.first()
//            Log.d("addProductPresenter ",list[0].toString())
//            Log.d("addProductPresenter ",data.)

            //view.openEditPage(data)
        }
    }

    override fun onFailedByBarcode(code: Int, msg: String) {
        view.hideLoadingDialog()
    }
}