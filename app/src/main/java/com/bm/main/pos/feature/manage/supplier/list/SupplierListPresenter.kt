package com.bm.main.pos.feature.manage.supplier.list

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.discount.Discount
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel
import kotlin.collections.ArrayList

class SupplierListPresenter(val context: Context, val view: SupplierListContract.View) : BasePresenter<SupplierListContract.View>(),
    SupplierListContract.Presenter, SupplierListContract.InteractorOutput {

    private var interactor: SupplierListInteractor = SupplierListInteractor(this)
    private var restModel = SupplierRestModel(context)


    override fun onViewCreated() {
        loadSuppliers()
    }

    override fun loadSuppliers() {
        interactor.callGetSuppliersAPI(context,restModel)
    }

    override fun deleteSupplier(id: String) {
        interactor.callDeleteSupplierAPI(context,restModel,id)
    }

    override fun searchSupplier(search: String) {
        interactor.onRestartDisposable()
        if(search.isNullOrEmpty() || search.isNullOrBlank()){
            interactor.callGetSuppliersAPI(context,restModel)
        }
        else{
            interactor.callSearchSupplierAPI(context,restModel,search)
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetSuppliers(list: List<Supplier>) {
        view.setData(list)
    }

    override fun onSuccessDeleteSupplier(msg: String?) {
        view.showSuccessMessage(msg)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }


}