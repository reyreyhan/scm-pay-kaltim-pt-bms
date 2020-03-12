package com.bm.main.pos.feature.sell.chooseCustomer

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel


interface ChooseCustomerContract {

    interface View : BaseViewImpl {
        fun setData(list:List<Customer>)
        fun showErrorMessage(code: Int, msg: String)
        fun showSuccessMessage(msg: String?)
        fun reloadData()
        fun onSelected(data:Customer)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadData()
        fun search(search:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetDataAPI(context: Context, restModel:CustomerRestModel)
        fun callSearchAPI(context: Context, restModel:CustomerRestModel, search:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetData(list:List<Customer>)
        fun onFailedAPI(code:Int,msg:String)
    }


}