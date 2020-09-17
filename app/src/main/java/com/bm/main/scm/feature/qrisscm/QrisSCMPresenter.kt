package com.bm.main.scm.feature.qrisscm

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel

class QrisSCMPresenter(val context: Context, val view: QrisSCMContract.View) : BasePresenter<QrisSCMContract.View>(),
    QrisSCMContract.Presenter, QrisSCMContract.InteractorOutput {

    private var interactor: QrisSCMInteractor = QrisSCMInteractor(this)
    private var restModel = ProductRestModel(context)


    override fun onViewCreated() {

    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun loadProducts() {
        interactor.callGetProductsAPI(context,restModel)
    }

    override fun searchProduct(search: String) {
        interactor.onRestartDisposable()
        if(search.isNullOrEmpty() || search.isNullOrBlank()){
            loadProducts()
        }
        else{
            interactor.callSearchProductAPI(context, restModel, search)
        }
    }

    override fun onSuccessGetProducts(list: List<Product>) {
        view.setProducts(list)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }

}