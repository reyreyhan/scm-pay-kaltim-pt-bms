package com.bm.main.scm.feature.manage.product.main

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel
import com.bm.main.scm.utils.AppConstant

class AddProductMainPresenter(val context: Context, val view: AddProductMainContract.View) :
    BasePresenter<AddProductMainContract.View>(),
    AddProductMainContract.Presenter,
    AddProductMainContract.InteractorOutput{

    private var restModel = ProductRestModel(context)

    private var interactor =
        AddProductMainInteractor(this)

    override fun onViewCreated(intent: Intent) {
        val barcode = intent.getStringExtra(AppConstant.DATA)
        if (!barcode.isNullOrEmpty()){
            view.openAddProductFragmentFromScan(barcode)
        }else{
            view.openScanFragment()
        }
    }

    override fun searchByBarcode(search: String) {
        interactor.callSearchByBarcodeAPI(context, restModel, search)
    }

    override fun onSuccessByBarcode(list: List<Product>){
        if (list.isNotEmpty()) {
            val data =
                list.firstOrNull { it.nama_barang.isNotEmpty() && it.gbr.isNotEmpty() && it.hargajual.isNotEmpty() && it.hargabeli.isNotEmpty() }
                    ?: list.first()
//            Log.d("addProductPresenter ",list[0].toString())
//            Log.d("addProductPresenter ",data.)
            view.onResult(data)
        }
    }
    override fun onDestroy() {
        interactor.onDestroy()
    }
}