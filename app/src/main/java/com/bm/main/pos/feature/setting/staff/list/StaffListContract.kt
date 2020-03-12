package com.bm.main.pos.feature.setting.staff.list

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.staff.Staff
import com.bm.main.pos.models.staff.StaffRestModel


interface StaffListContract {

    interface View : BaseViewImpl {
        fun setData(list:List<Staff>)
        fun showErrorMessage(code: Int, msg: String)
        fun showSuccessMessage(msg: String?)
        fun reloadData()
        fun openAddPage()
        fun openEditPage(data:Staff)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadData()
        fun delete(id:String)
        fun search(search:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetDataAPI(context: Context, restModel:StaffRestModel)
        fun callDeleteAPI(context: Context, restModel:StaffRestModel, id:String)
        fun callSearchAPI(context: Context, restModel:StaffRestModel, search:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetData(list:List<Staff>)
        fun onSuccessDelete(msg: String?)
        fun onFailedAPI(code:Int,msg:String)
    }


}