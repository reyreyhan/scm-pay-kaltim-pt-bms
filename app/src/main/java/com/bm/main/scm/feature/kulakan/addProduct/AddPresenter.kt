package com.bm.main.scm.feature.kulakan.addProduct

import android.content.Context
import android.content.Intent
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.cart.CartRestModel
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.utils.AppConstant

class AddPresenter(val context: Context, val view: AddContract.View) : BasePresenter<AddContract.View>(),
    AddContract.Presenter, AddContract.InteractorOutput {

    private var interactor = AddInteractor(this)
    private var cartRestModel = CartRestModel(context)
    private var barcode:String ?= null

    override fun onViewCreated(intent: Intent) {
        barcode = intent.getStringExtra(AppConstant.DATA)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onCheck(name: String, buy: String, sell: String, stok: String) {
        if(name.isBlank() || name.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_product_name))
            return
        }

        if(buy.isBlank() || buy.isEmpty() || "0" == buy){
            view.showMessage(999,context.getString(R.string.err_empty_buy))
            return
        }

        if(sell.isBlank() || sell.isEmpty() || "0" == sell){
            view.showMessage(999,context.getString(R.string.err_empty_sell))
            return
        }

        if(stok.isBlank() || stok.isEmpty() || "0" == stok){
            view.showMessage(999,context.getString(R.string.err_empty_stock))
            return
        }

        if(barcode.isNullOrEmpty() || barcode.isNullOrBlank()){
            interactor.callAddAPI(context,cartRestModel,name,buy,sell,stok)
        }
        else{
            interactor.callAddWithBarodeAPI(context,cartRestModel,name,barcode!!,buy,sell,stok)
        }
    }

    override fun onSuccessAdd(data: List<Product>) {
        if(data.isEmpty()){
            onFailedAPI(999,"Terjadi kesalahan")
            return
        }
        view.onSuccess(data[0])
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code,msg)
    }
}