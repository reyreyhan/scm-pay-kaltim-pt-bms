package com.bm.main.pos.feature.setting.staff.list

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.discount.Discount
import com.bm.main.pos.models.staff.Staff
import com.bm.main.pos.models.staff.StaffRestModel
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel
import kotlin.collections.ArrayList

class StaffListPresenter(val context: Context, val view: StaffListContract.View) : BasePresenter<StaffListContract.View>(),
    StaffListContract.Presenter, StaffListContract.InteractorOutput {

    private var interactor = StaffListInteractor(this)
    private var restModel = StaffRestModel(context)


    override fun onViewCreated() {
        loadData()
    }

    override fun loadData() {
        interactor.callGetDataAPI(context,restModel)
    }

    override fun delete(id: String) {
        interactor.callDeleteAPI(context,restModel,id)
    }

    override fun search(search: String) {
        interactor.onRestartDisposable()
        if(search.isEmpty() || search.isBlank()){
            interactor.callGetDataAPI(context,restModel)
        }
        else{
            interactor.callSearchAPI(context,restModel,search)
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetData(list: List<Staff>) {
        view.setData(list)
    }

    override fun onSuccessDelete(msg: String?) {
        view.showSuccessMessage(msg)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }


}