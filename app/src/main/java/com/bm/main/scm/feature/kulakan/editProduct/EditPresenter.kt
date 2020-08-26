package com.bm.main.scm.feature.kulakan.editProduct

import android.content.Context
import android.content.Intent
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.cart.CartRestModel
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.utils.AppConstant

class EditPresenter(val context: Context, val view: EditContract.View) : BasePresenter<EditContract.View>(),
    EditContract.Presenter, EditContract.InteractorOutput {

    private var interactor = EditInteractor(this)
    private var cartRestModel = CartRestModel(context)
    private var data:Product ?= null

    override fun onViewCreated(intent: Intent) {
        data = intent.getSerializableExtra(AppConstant.DATA) as Product
        if(data == null){
            view.onClose()
        }
        data?.let {
            view.setName(it.nama_barang!!)
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onCheck(buy: String, sell: String, stok: String) {
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

        interactor.callEditAPI(context,cartRestModel,data?.id_barang!!,data?.nama_barang!!,data?.kodebarang!!,buy,sell,stok)
    }

    override fun onSuccessEdit(data: List<Product>) {
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